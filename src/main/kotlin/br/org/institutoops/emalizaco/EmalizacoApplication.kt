package br.org.institutoops.emalizaco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EmalizacoApplication

fun main(args: Array<String>) {
	runApplication<EmalizacoApplication>(*args)
}
