package br.org.institutoops.emailzaco.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class FormController {

    private val emailApiUrl by lazy { System.getenv("EMAIL_API_URL") }

    @GetMapping("/")
    fun form(model: Model): String {
        model.addAttribute("EMAIL_API_URL", emailApiUrl)
        return "disabled"
    }
}