package com.cdbfkk.lootly.shared.api.exception

abstract class BaseException(
    val messageKey: String,
    val args: Array<String> = emptyArray()
) : RuntimeException()