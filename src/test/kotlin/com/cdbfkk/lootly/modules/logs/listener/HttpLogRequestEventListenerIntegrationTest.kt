package com.cdbfkk.lootly.modules.logs.listener

import com.cdbfkk.lootly.common.AbstractIntegrationTest
import com.cdbfkk.lootly.logs.domain.repository.HttpLogRepository
import com.cdbfkk.lootly.shared.api.events.HttpLogRequestEvent
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import java.time.Duration
import java.util.concurrent.TimeUnit

class HttpLogRequestEventListenerIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    lateinit var publisher: ApplicationEventPublisher

    @Autowired
    lateinit var repository: HttpLogRepository

    @Test
    fun `should persit HttpLog when event is published`() {
        val event = HttpLogRequestEvent(
            method = "POST",
            path = "/games",
            statusCode = 201,
            responseTimeMs = 120,
            requestBody = "request body",
            responseBody = "response body",
            queryParams = "foo=bar",
            ip = "127.0.0.1",
            headers = "some-header: value"
        )

        publisher.publishEvent(event)

        await()
            .atMost(5, TimeUnit.SECONDS)
            .pollInterval(Duration.ofMillis(250))
            .untilAsserted {
                val logs = repository.findAll()
                assertThat(logs).hasSize(1)
                val firstHttpLog = logs.first()
                assertThat(firstHttpLog.method).isEqualTo(event.method)
                assertThat(firstHttpLog.path).isEqualTo(event.path)
                assertThat(firstHttpLog.status).isEqualTo(event.statusCode)
                assertThat(firstHttpLog.responseTimeMs).isEqualTo(event.responseTimeMs)
                assertThat(firstHttpLog.queryParameters).isEqualTo(event.queryParams)
                assertThat(firstHttpLog.ip).isEqualTo(event.ip)
                assertThat(firstHttpLog.headers).isEqualTo(event.headers)
                assertThat(firstHttpLog.requestBody).isEqualTo(event.requestBody)
                assertThat(firstHttpLog.responseBody).isEqualTo(event.responseBody)
            }

    }
}