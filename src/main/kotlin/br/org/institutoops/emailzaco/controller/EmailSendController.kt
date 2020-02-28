package br.org.institutoops.emailzaco.controller

import br.org.institutoops.emailzaco.service.SendMailService
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty


@RestController
@RequestMapping("/email")
class EmailSendController(
    private val sendMailService: SendMailService
) {
    
    @PostMapping
    fun sendEmail(
        @Valid @RequestBody emailSendRequest: EmailSendRequest
    ): ResponseEntity<String> {
        sendAllEmails(emailSendRequest)
        return ResponseEntity.accepted().build()
    }
    
    private fun sendAllEmails(emailSendRequest: EmailSendRequest) {
        parliamentaryEmails.forEach { 
            sendMailService.sendMail(
                emailSendRequest.user.name,
                emailSendRequest.user.email,
                it,
                messageParliamentary.replace("%USUARIO%", emailSendRequest.user.name)
            )
        }

        sendMailService.sendMail(
            emailSendRequest.user.name,
            emailSendRequest.user.email,
            ombudsmanEmail,
            messageOmbudsman.replace("%USUARIO%", emailSendRequest.user.name)
        )
    }
}

data class EmailSendRequest(
    @JsonProperty("user") val user: User
)

data class User(
    
    @NotEmpty
    @JsonProperty("name")
    val name: String,
    
    @Email
    @JsonProperty("email")
    val email: String
)

val parliamentaryEmails = listOf(
    "deputadoavallone@al.mt.gov.br",
    "deputadodelegadoclaudinei@al.mt.gov.br",
    "gabinetedilmardalbosco@hotmail.com",
    "assessoria@dreugenio.com.br",
    "deputadodrgimenez@al.mt.gov.br",
    "deputadodrjoao@al.mt.gov.br",
    "imprensa.eduardobotelho@gmail.com",
    "deputadoelizeunascimento@gmail.com",
    "deputadofaissal@al.mt.gov.br",
    "contato@janainarivamt.com.br",
    "deputadojoaobatista@al.mt.gov.br",
    "deputadoludiocabral@al.mt.gov.br",
    "deputadomaxrussi@gmail.com",
    "ascomdepnininho@gmail.com",
    "deputadopauloaraujo@al.mt.gov.br",
    "depromoaldojr@al.mt.gov.br",
    "sebastiao_rezende@hotmail.com",
    "deputadosilviofavero@al.mt.gov.br",
    "deputadothiagosilva@al.mt.gov.br",
    "deputadoulyssesmoraes@al.mt.gov.br",
    "depvaldirbarranco@al.mt.gov.br",
    "deputadovalmirmoretto@al.mt.gov.br",
    "dep.wilsonsantos@gmail.com",
    "deputadoxuxudalmolin@al.mt.gov.br",
    "profallanbenitez@gmail.com"
)

val ombudsmanEmail = "ouvidoria@tce.mt.gov.br"

val messageParliamentary = """
    Senhor parlamentar,

    Me chamo %USUARIO% e sou colaborador do Instituto OPS, entidade que se dedica a fiscalizar gastos públicos e o uso correto desse recurso.
    A população acompanha (incrédula e revoltada) a notícia de que o TCMT enviou para a ALMT o PL 15/20, que pretende criar Vantagem Indenizatória para membros desse Tribunal no valor de um subsídio, além do já recebido mensalmente; fora verba indenizatória para Presidente, que pode chegar à metade do subsídio, que se somariam a outros dois recebidos, anualmente, a título de auxilio-livro. São valores que chegariam a R${'$'}95 ou R${'$'}75 mil reais ao mês.

    Trata-se de remuneração desproporcional, quase 70 vezes superior ao salário mínimo, superando o que recebe a esmagadora maioria da população que o senhor representa, da qual 20% vivem em estado de pobreza ou abaixo dela. 

    ADVERTIMOS, portanto, que ESSES VALORES CUSTARÃO O SUOR DO POVO DE MATO GROSSO.

    Não fosse o fato de que referido projeto de lei fere o interesse público, extrapola, ainda, o teto constitucional de ministros do STF, tratando-se, na prática, de majorar subsídio por meio da criação de vantagens ditas indenizatórias, mantendo-as a salvo do corte.  

    Além disso, o PL fere a simetria constitucional entre o TCMT e a Magistratura desse Estado, como se logrou comprovar após expediente enviado ao CNJ, no qual se respondeu que jamais os membros do TJMT receberam VI, como essa, que, por sua natureza, vem sendo percebida pelo Parlamento. 

    Dessa forma, CIENTIFICAMOS o senhor de que, vantagens como essas, “não são apenas ilegais, como também descaradamente inconstitucionais. Sob essa ótica, a percepção de verbas manifestamente inconstitucionais EQUIVALE A RECEBÊ-LAS DE MÁ FÉ, uma vez que esta é ínsita à própria inconstitucionalidade”, escreveu o STF na Ação Originária 506, em 2018.

    Por esse modo, queremos INFORMÁ-LO das irregularidades do PL 15/20, para que seja imediatamente ARQUIVADO. 

    Caso se persista nesse ato de  verdadeiro desrespeito ao povo, não espere passividade: O Instituto OPS convocará a população e irá lutar contra a aprovação desse PL, e, se isso ocorrer, irá denunciar o fato em todas as instâncias, para que não apenas derrubem a medida inconstitucional, mas, ainda, como forma de desmascarar os fatos e desvendar à sociedade brasileira o elevado grau de Insensibilidade daqueles que deveriam defender o erário e o cumprimento da Constituição Federal, em Mato Grosso.
""".trimIndent().replace("\n", "<br />")

val messageOmbudsman ="""
    Senhor Ouvidor
    
    Me chamo %USUARIO% e sou colaborador do Instituto OPS, entidade que se dedica a fiscalizar gastos públicos e o uso correto desse recurso.
    A população acompanha (incrédula e revoltada) a notícia de que este TCMT enviou para a ALMT o PL 15/20, que pretende criar Vantagem Indenizatória para membros desse Tribunal no valor de um subsídio, além do já recebido mensalmente; fora verba indenizatória para Presidente, que pode chegar à metade do subsídio, o que se somariam a outros dois, anualmente, a título de auxilio-livro. São valores que chegariam a R${'$'} 95 mil ou R${'$'} 75 mil. 
    
    Trata-se de um valor desproporcional, quase 70 vezes superior ao salário mínimo, superando o que recebe a esmagadora maioria da população de MT, da qual 20% vivem em estado de pobreza, ou abaixo dela. 
    ADVERTIMOS, portanto, os senhores de que ESSES VALORES CUSTARÃO O SUOR DO POVO DE MATO GROSSO.
    Não fosse o fato de que referido projeto de lei fere o interesse público, extrapola, ainda, o teto constitucional de ministros do STF, tratando-se, na prática, de majorar subsídio por meio da criação de vantagens ditas indenizatórias, mantendo-as a salvo do corte.  
    
    Dessa forma, CIENTIFICAMOS os senhores de que, vantagens como essas, “não são apenas ilegais, como também descaradamente inconstitucionais. Sob essa ótica, a percepção de verbas manifestamente inconstitucionais EQUIVALE A RECEBÊ-LAS DE MÁ FÉ, uma vez que esta é ínsita à própria inconstitucionalidade”, assentou o STF na AO 506.
    
    Por esse modo, queremos INFORMAR os senhores das irregularidades do PL 15/20, para que seja imediatamente retirado do Parlamento. 
    
    Caso se persista nesse ato de  verdadeiro desrespeito ao povo, não esperem passividade: O Instituto OPS convocará a população para lutar contra a aprovação desse projeto de lei, e, se isso ocorrer, irá denunciar o fato em todas as instâncias, para que não apenas derrubem a medida inconstitucional, mas, ainda, como forma de desmascarar os fatos e desvendar à sociedade brasileira o elevado grau de insensibilidade daqueles que deveriam defender o erário e o cumprimento da Constituição Federal, em Mato Grosso.
""".trimIndent().replace("\n", "<br />")