package com.cdbfkk.lootly.shared.exception

open class BusinessException(
    messageKey: String,
    args: Array<Any> = emptyArray()
) : BaseException(messageKey, args)