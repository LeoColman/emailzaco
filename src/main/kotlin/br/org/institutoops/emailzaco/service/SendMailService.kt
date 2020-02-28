package br.org.institutoops.emailzaco.service

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service

@Service
class SendMailService(
    restTemplateBuilder: RestTemplateBuilder
) {
    private val pepiPostKey by lazy { System.getenv("PEPIPOST_API_KEY") }
    
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
            "https://api.pepipost.com/v2/sendEmail",
            HttpMethod.POST,
            HttpEntity(value, headers),
            String::class.java
        )
    }
    
    private fun createAuthHeaders() = HttpHeaders().apply {
        add("api_key", pepiPostKey)
        contentType = MediaType.APPLICATION_JSON
    }
    
    
    private fun createValue(
        userName: String,
        userEmail: String,
        targetEmail: String,
        mailBody: String
    ): Map<Any, Any> {
        return mapOf(
            "personalizations" to listOf(
                mapOf("recipient" to targetEmail)
            ),
            "from" to mapOf(
                "fromEmail" to "no-reply-$randomStr@institutoops.org.br",
                "fromName" to userName
            ),
            "replyToId" to userEmail,
            "subject" to "Projeto de Lei 15/20",
            "content" to mailBody,
            "settings" to mapOf(
                "unsubscribe" to 0
            )
        )
    }
    
    private val randomStr: String get() = (1..5).map { (('a'..'z') + ('0'..'9')).random() }.joinToString(separator = "")

}
