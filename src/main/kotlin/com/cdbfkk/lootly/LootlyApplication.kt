package com.cdbfkk.lootly

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.modulith.Modulith

@Modulith(
    sharedModules = ["shared"]
)
@SpringBootApplication
class LootlyApplication

fun main(args: Array<String>) {
    System.setProperty("user.timezone", "UTC")
    runApplication<LootlyApplication>(*args)
}


