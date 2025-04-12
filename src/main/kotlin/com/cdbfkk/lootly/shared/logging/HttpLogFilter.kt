package com.cdbfkk.lootly.shared.logging

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper

@Component
class HttpLogFilter(
    private val repository:HttpLogRepository
) : OncePerRequestFilter() {

    private companion object {
        const val MAX_BODY_LENGTH = 5000
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val cachedRequest = CachedBodyHttpServletRequest(request)
        val cachedResponse = ContentCachingResponseWrapper(response)

        val startTimestamp = System.currentTimeMillis()
        filterChain.doFilter(cachedRequest, cachedResponse)
        val responseTime = System.currentTimeMillis() - startTimestamp

        val log = buildHttpLog(cachedRequest, cachedResponse, responseTime)
        repository.save(log)

        cachedResponse.copyBodyToResponse()
    }

    private fun buildHttpLog(
        request: CachedBodyHttpServletRequest,
        response: ContentCachingResponseWrapper,
        responseTime: Long
    ): HttpLog {
        val requestBody = request.getCachedBody().take(MAX_BODY_LENGTH)
        val responseBody = String(response.contentAsByteArray).take(MAX_BODY_LENGTH)
        val headers = extractHeaders(request)

        return HttpLog(
            method = request.method,
            path = request.requestURI,
            status = response.status,
            responseTimeMs = responseTime,
            requestBody = requestBody,
            responseBody = responseBody,
            queryParameters = request.queryString ?: "",
            ip = request.getHeader("X-Forwarded-For") ?: request.remoteAddr,
            headers = headers.toString().take(MAX_BODY_LENGTH)
        )
    }

    private fun extractHeaders(request: HttpServletRequest): Map<String, String> {
        val headers = mutableMapOf<String, String>()
        val headerNames = request.headerNames
        while (headerNames.hasMoreElements()) {
            val name = headerNames.nextElement()
            headers[name] = request.getHeader(name)
        }
        return headers
    }

}