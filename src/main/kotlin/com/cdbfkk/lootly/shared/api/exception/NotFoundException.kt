package com.cdbfkk.lootly.shared.api.exception

open class NotFoundException(
    messageKey: String,
    args: Array<String> = emptyArray()
) : BaseException(messageKey, args)