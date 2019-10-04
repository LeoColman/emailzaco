package br.org.institutoops.emalizaco.controller

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import io.kotlintest.spring.SpringListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HelloWorldControllerTest : FunSpec() {

    @LocalServerPort var port: Int = 0
    
    @Autowired
    private lateinit var restTemplate: TestRestTemplate
    
    init {
        test("Returns hello world") {
            restTemplate.getForObject<String>("http://localhost:$port/") shouldBe "Hello World"
        }
    }
    
    
    override fun listeners() = listOf(SpringListener)
}