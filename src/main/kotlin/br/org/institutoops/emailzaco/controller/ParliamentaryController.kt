package br.org.institutoops.emailzaco.controller

import br.org.institutoops.emailzaco.service.ParliamentaryListService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/parliamentary")
class ParliamentaryController(
    private val parliamentaryListService: ParliamentaryListService
) {

    @GetMapping("/list")
    fun parliamentaryList() = parliamentaryListService.getParliamentarians()
}