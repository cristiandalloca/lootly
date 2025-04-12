package com.cdbfkk.lootly.modules.games.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/games")
class GameController {

    @GetMapping
    fun getAllGames(@RequestParam filter: String) : String {
        return "Hello World: $filter"
    }

}