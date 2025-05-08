package com.cdbfkk.lootly.shared.api.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SwaggerRedirectConfig : WebMvcConfigurer {

    companion object {
        private const val SWAGGER_UI_PATH = "/swagger-ui/index.html"
    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/", SWAGGER_UI_PATH)
        registry.addRedirectViewController("/swagger", SWAGGER_UI_PATH)
        registry.addRedirectViewController("/swagger-ui", SWAGGER_UI_PATH)
    }
}