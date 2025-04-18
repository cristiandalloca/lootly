package com.cdbfkk.lootly.games.infrastructure.exception

import com.cdbfkk.lootly.shared.api.exception.NotFoundException

class GameNotFoundException(
    messageKey: String,
    args: Array<String> = emptyArray()
) : NotFoundException(messageKey, args)