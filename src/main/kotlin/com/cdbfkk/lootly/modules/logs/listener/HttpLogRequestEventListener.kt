package com.cdbfkk.lootly.modules.logs.listener

import com.cdbfkk.lootly.modules.logs.domain.model.HttpLog
import com.cdbfkk.lootly.modules.logs.domain.repository.HttpLogRepository
import com.cdbfkk.lootly.shared.logging.HttpLogRequestEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class HttpLogRequestEventListener(
    private val repository: HttpLogRepository
) {

    private companion object {
        private const val MAX_BODY_LENGTH = 5000
    }

    @Async
    @EventListener
    fun onLogRequestEvent(event: HttpLogRequestEvent) {
        val httpLog = HttpLog(
            method = event.method,
            path = event.path,
            status = event.statusCode,
            responseTimeMs = event.responseTimeMs,
            requestBody = event.requestBody?.take(MAX_BODY_LENGTH),
            responseBody = event.responseBody?.take(MAX_BODY_LENGTH),
            queryParameters = event.queryParams,
            ip = event.ip,
            headers = event.headers.take(MAX_BODY_LENGTH)
        )
        repository.save(httpLog)
    }
}