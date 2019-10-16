package br.org.institutoops.emailzaco.service

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import org.springframework.util.LinkedMultiValueMap

@Service
class SendMailService(
    private val restTemplateBuilder: RestTemplateBuilder,
    private val parliamentaryListService: ParliamentaryListService
) {
    private val mailgunApi by lazy { System.getenv("MAILGUN_API_KEY") }
    private val mailgunKey = "api:$mailgunApi"
    
    private val restTemplate = restTemplateBuilder.build()

    fun sendMail(
        userName: String,
        userEmail: String,
        parliamentaryName: String,
        mailBody: String
    ) {
        val headers = createAuthHeaders()
        getParliamentaryEmails(parliamentaryName).forEach {
            val value = createValue(userName, userEmail, it, mailBody)
            
            restTemplate.exchange(
                "https://api.mailgun.net/v3/mg.institutoops.org.br/messages",
                HttpMethod.POST,
                HttpEntity(value, headers),
                String::class.java
            )
        }
    }
    
    private fun createAuthHeaders() = HttpHeaders().apply {
        val userBase64 = Base64Utils.encodeToUrlSafeString(mailgunKey.toByteArray())
        add("Authorization", "Basic $userBase64")
        contentType = MediaType.APPLICATION_FORM_URLENCODED
    }
    
    private fun getParliamentaryEmails(parliamentaryName: String): List<String> {
        return parliamentaryListService.getParliamentarians().first { it.name == parliamentaryName }.emails
    }
    
    private fun createValue(
        userName: String,
        userEmail: String,
        parliamentaryEmail: String,
        mailBody: String
    ): LinkedMultiValueMap<String, String> {
        return LinkedMultiValueMap(mapOf(
            "from" to listOf("$userName <no-reply@institutoops.org.br>"),
            "to" to listOf(parliamentaryEmail),
            "bcc" to listOf("emalizaco@institutoops.org.br"),
            "text" to listOf(mailBody),
            "subject" to listOf("Solicitação com base na Lei de Acesso à Informação"),
            "h:Reply-To" to listOf(userEmail)
        ))
    }

}
