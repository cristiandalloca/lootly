package com.cdbfkk.lootly.shared.exception

abstract class BaseException(
    val messageKey: String,
    val args: Array<String> = emptyArray()
) : RuntimeException()