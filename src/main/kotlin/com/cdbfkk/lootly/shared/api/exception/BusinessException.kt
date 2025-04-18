package com.cdbfkk.lootly.shared.api.exception

open class BusinessException(
    messageKey: String,
    args: Array<String> = emptyArray()
) : BaseException(messageKey, args)