package com.cdbfkk.lootly.shared.logging

data class HttpLogRequestEvent(
    val method: String,
    val path: String,
    val ip: String,
    val headers: String,
    val queryParams: String?,
    val requestBody: String?,
    val responseBody: String?,
    val statusCode: Int,
    val responseTimeMs: Long
)