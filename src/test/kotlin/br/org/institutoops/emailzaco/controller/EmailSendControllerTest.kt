package br.org.institutoops.emailzaco.controller

import br.org.institutoops.emailzaco.service.SendMailService
import com.ninjasquad.springmockk.MockkBean
import io.kotlintest.IsolationMode
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import io.kotlintest.spring.SpringListener
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EmailSendControllerTest : FunSpec() {
    
    @LocalServerPort
    private var port: Int = 0
    
    @Autowired
    private lateinit var restTemplate: TestRestTemplate
    
    @MockkBean(relaxed = true)
    private lateinit var sendMailService: SendMailService
    
    init {
        test("Should receive a post of a user, parliamentary and message list") {
            executePostRequest().statusCode shouldBe HttpStatus.ACCEPTED
        }
        
        test("Should delegate to the mail send service for each parliamentarian") {
            executePostRequest()
            
            verify(exactly = 1) { sendMailToJose() }
            verify(exactly = 1) { sendMailToFaria() }
        }
    }
    
    private fun executePostRequest(): ResponseEntity<String> {
        val request = HttpEntity(requestJson, applicationJsonHeaders)
        return restTemplate.postForEntity("http://localhost:$port/email", request)
    }
    
    private val requestJson = """
        {
            "user": {
                "name": "Pedro Pedroso",
                "email": "pedro_pedroso@gmail.com"
            },
            
            "parliamentarian_mails": [
                {
                    "name": "José da Silva",
                    "mail_body": "Ei seu José, não rouba nóis"
                },
                {
                    "name": "Faria Faremos",
                    "mail_body": "Ei, você também não rouba nóis"
                }
            ]
        }
    """.trimIndent()
    
    private fun sendMailToJose() {
        sendMailService.sendMail("Pedro Pedroso", "pedro_pedroso@gmail.com", "José da Silva", "Ei seu José, não rouba nóis")
    }
    
    private fun sendMailToFaria() {
        sendMailService.sendMail("Pedro Pedroso", "pedro_pedroso@gmail.com", "Faria Faremos", "Ei, você também não rouba nóis")
    }
    
    private val applicationJsonHeaders = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
    
    override fun listeners() = listOf(SpringListener)
    
    override fun isolationMode() = IsolationMode.InstancePerTest
}