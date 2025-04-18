package com.cdbfkk.lootly.games.api

import com.cdbfkk.lootly.games.infrastructure.exception.GameNotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/games")
class GameController {

    @GetMapping
    fun getAllGames(@RequestParam filter: String) : String {
        throw GameNotFoundException(
            messageKey = "game.not.found",
            args = arrayOf(filter)
        )
        return "Hello World: $filter"
    }

}