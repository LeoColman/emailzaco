package br.org.institutoops.emailzaco.controller

import br.org.institutoops.emailzaco.service.ParliamentaryListService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ParliamentaryListController(
    private val parliamentaryListService: ParliamentaryListService
) {

    @GetMapping
    fun parliamentaryList() = parliamentaryListService.getParliamentarians()
}