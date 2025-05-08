package com.cdbfkk.lootly.shared.api.logging

import com.cdbfkk.lootly.shared.api.configuration.HttpLoggingProperties
import com.cdbfkk.lootly.shared.api.events.HttpLogRequestEvent
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper

@Component
class HttpLogFilter(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val httpLoggingProperties: HttpLoggingProperties
) : OncePerRequestFilter() {

    private companion object {
        private const val X_FORWARDED_FOR_HEADER = "X-Forwarded-For"
        private val pathMatcher = AntPathMatcher()
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return httpLoggingProperties.excludedPaths.any {
            pattern -> pathMatcher.match(pattern, request.requestURI)
        }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val cachedRequest = CachedBodyHttpServletRequest(request)
        val cachedResponse = ContentCachingResponseWrapper(response)

        val startTimeMillis = System.currentTimeMillis()

        filterChain.doFilter(cachedRequest, cachedResponse)
        val responseTime = System.currentTimeMillis() - startTimeMillis

        logRequest(cachedRequest, cachedResponse, responseTime)
        cachedResponse.copyBodyToResponse()
    }

    private fun logRequest(
        cachedRequest: CachedBodyHttpServletRequest,
        cachedResponse: ContentCachingResponseWrapper,
        responseTime: Long
    ) {
        val logRequestEvent = buildLogRequestEvent(cachedRequest, cachedResponse, responseTime)
        applicationEventPublisher.publishEvent(logRequestEvent)
    }

    private fun buildLogRequestEvent(
        request: CachedBodyHttpServletRequest,
        response: ContentCachingResponseWrapper,
        responseTime: Long
    ): HttpLogRequestEvent {
        val headers = extractHeaders(request).toString()

        return HttpLogRequestEvent(
            method = request.method,
            path = request.requestURI,
            statusCode = response.status,
            responseTimeMs = responseTime,
            requestBody = request.getCachedBody(),
            responseBody = String(response.contentAsByteArray),
            queryParams = request.queryString,
            ip = request.getHeader(X_FORWARDED_FOR_HEADER) ?: request.remoteAddr,
            headers = headers
        )
    }

    private fun extractHeaders(request: HttpServletRequest): Map<String, String> {
        return request.headerNames.asSequence()
            .associateWith { request.getHeader(it) }
    }

}