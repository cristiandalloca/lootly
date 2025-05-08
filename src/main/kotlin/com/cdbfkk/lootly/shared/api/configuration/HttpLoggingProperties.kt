package com.cdbfkk.lootly.shared.api.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "http.logging")
data class HttpLoggingProperties(
    val excludedPaths: List<String>
)
