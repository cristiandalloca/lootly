package com.cdbfkk.lootly.shared.api.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.parameters.Parameter
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.collections.forEach

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI =
        OpenAPI().info(Info().title("Lootly API").version("1.0.0"))

    @Bean
    fun addAcceptLanguageHeader(): OpenApiCustomizer {
        return OpenApiCustomizer { openApi ->
            openApi.paths?.values?.forEach { pathItem ->
                pathItem.readOperations().forEach { operation: Operation ->
                    operation.addParametersItem(
                        Parameter()
                            .name("Accept-Language")
                            .`in`("header")
                            .description("Idioma da resposta (ex: pt-BR, en-US, es-ES)")
                            .required(false)
                            .example("pt-BR")
                    )
                }
            }
        }
    }
}