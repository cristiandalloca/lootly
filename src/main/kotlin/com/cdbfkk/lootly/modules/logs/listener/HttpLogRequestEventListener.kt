package com.cdbfkk.lootly.modules.logs.listener

import com.cdbfkk.lootly.modules.logs.domain.model.HttpLog
import com.cdbfkk.lootly.modules.logs.domain.repository.HttpLogRepository
import com.cdbfkk.lootly.shared.api.events.HttpLogRequestEvent
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
            requestBody = takeIfNecessary(event.requestBody),
            responseBody = takeIfNecessary(event.responseBody),
            queryParameters = event.queryParams,
            ip = event.ip,
            headers = takeIfNecessary(event.headers).toString()
        )
        repository.save(httpLog)
    }

    private fun takeIfNecessary(value: String?) : String? {
        return value?.take(MAX_BODY_LENGTH)
    }
}