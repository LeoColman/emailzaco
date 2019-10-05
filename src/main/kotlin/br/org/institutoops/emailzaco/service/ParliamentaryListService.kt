package br.org.institutoops.emailzaco.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class ParliamentaryListService(
    private val objectMapper: ObjectMapper
) {
    
    @Value("classpath:parliamentary_list.json")
    private lateinit var parliamentaryList: Resource
    
    
    fun getParliamentarians() = loadParliamentaryJsonFromFile()
    
    private fun loadParliamentaryJsonFromFile(): List<Parliamentary> {
        return objectMapper.readValue(parliamentaryList.inputStream)
    }
    
}

data class Parliamentary(
    val name: String,
    val mail: String,
    val mailBodyPlaceholder: String
)

@Configuration
class ObjectMapperConfig {
    
    @Bean fun objectMapper() = ObjectMapper().registerKotlinModule()
}
