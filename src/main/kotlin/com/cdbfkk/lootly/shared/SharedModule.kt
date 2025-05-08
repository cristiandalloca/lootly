@file:ApplicationModule
package com.cdbfkk.lootly.shared

import com.cdbfkk.lootly.shared.api.configuration.HttpLoggingProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.modulith.ApplicationModule

@Configuration
@EnableConfigurationProperties(HttpLoggingProperties::class)
class SharedModule