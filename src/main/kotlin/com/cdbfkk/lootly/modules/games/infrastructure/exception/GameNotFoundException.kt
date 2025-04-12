package com.cdbfkk.lootly.modules.games.infrastructure.exception

import com.cdbfkk.lootly.shared.exception.NotFoundException

class GameNotFoundException(
    messageKey: String,
    args: Array<String> = emptyArray()
) : NotFoundException(messageKey, args)