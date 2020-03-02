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
    restTemplateBuilder: RestTemplateBuilder
) {
    private val mailgunApi by lazy { System.getenv("MAILGUN_API_KEY") }
    private val mailgunKey = "api:$mailgunApi"
    
    private val restTemplate = restTemplateBuilder.build()

    fun sendMail(
        userName: String,
        userEmail: String,
        targetEmail: String,
        mailBody: String
    ) {
        val headers = createAuthHeaders()
        val value = createValue(userName, userEmail, targetEmail, mailBody)

        restTemplate.exchange(
            "https://api.mailgun.net/v3/mg.institutoops.org.br/messages",
            HttpMethod.POST,
            HttpEntity(value, headers),
            String::class.java
        )
    }
    
    private fun createAuthHeaders() = HttpHeaders().apply {
        val userBase64 = Base64Utils.encodeToUrlSafeString(mailgunKey.toByteArray())
        add("Authorization", "Basic $userBase64")
        contentType = MediaType.APPLICATION_FORM_URLENCODED
    }
    
    
    private fun createValue(
        userName: String,
        userEmail: String,
        targetEmail: String,
        mailBody: String
    ): LinkedMultiValueMap<String, String> {
        return LinkedMultiValueMap(mapOf(
            "from" to listOf("$userName <no-reply-$randomStr@institutoops.org.br>"),
            "to" to listOf(targetEmail),
            "html" to listOf(mailBody),
            "subject" to listOf("Projeto de Lei 15/20"),
            "h:Reply-To" to listOf(userEmail)
        ))
    }
    
    private val randomStr: String get() = (1..5).map { (('a'..'z')).random() }.joinToString(separator = "")

}
