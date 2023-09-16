package io.violabs.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpringAngularApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringAngularApplication>(*args)
}