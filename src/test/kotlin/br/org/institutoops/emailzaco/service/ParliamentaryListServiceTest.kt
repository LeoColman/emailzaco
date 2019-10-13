package br.org.institutoops.emailzaco.service

import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.specs.FunSpec
import io.kotlintest.spring.SpringListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ParliamentaryListServiceTest : FunSpec() {
    
    @Autowired
    private lateinit var target: ParliamentaryListService
    
    init {
        test("Should load parliamentary list from file") {
            target.getParliamentarians() shouldContainExactlyInAnyOrder expectedParliamentaryListFromFile
        }
    }
    
    private val expectedParliamentaryListFromFile = listOf(
        Parliamentary("José da Silva", listOf("jose_da_silva@example.com"), "Olá, José! Este é um placeholder"),
        Parliamentary("Banana Banonoso", listOf("banana_banonoso@example.com", "banana_bone@example.com"), "Olá, banana bananoso! Este é outro placeholder")
    )
    
    
    override fun listeners() = listOf(SpringListener)
}