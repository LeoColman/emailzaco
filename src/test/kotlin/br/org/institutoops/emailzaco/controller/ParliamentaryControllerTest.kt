package br.org.institutoops.emailzaco.controller

import io.kotlintest.assertions.json.shouldMatchJson
import io.kotlintest.specs.FunSpec
import io.kotlintest.spring.SpringListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.io.Resource

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ParliamentaryControllerTest : FunSpec() {

    @LocalServerPort var port: Int = 0
    
    @Autowired
    private lateinit var restTemplate: TestRestTemplate
    
    init {
        test("Returns the parliamentary list") {
            val parliamentariansJson = getParliamentariansJson()
            
            restTemplate.getForObject<String>("http://localhost:$port/parliamentary/list")!!.shouldMatchJson(parliamentariansJson)
        }
    }
    
    
    @Value("classpath:parliamentary_list.json")
    private lateinit var parliamentaryListResource: Resource
    
    private fun getParliamentariansJson() = parliamentaryListResource.inputStream.bufferedReader().readText()
    
    
    override fun listeners() = listOf(SpringListener)
}