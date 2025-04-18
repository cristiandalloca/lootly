package com.cdbfkk.lootly.modules.logs.listener

import com.cdbfkk.lootly.common.AbstractUnitTest
import com.cdbfkk.lootly.logs.domain.model.HttpLog
import com.cdbfkk.lootly.logs.domain.repository.HttpLogRepository
import com.cdbfkk.lootly.logs.listener.HttpLogRequestEventListener
import com.cdbfkk.lootly.shared.api.events.HttpLogRequestEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import java.util.stream.Stream

class HttpLogRequestEventListenerTest : AbstractUnitTest() {

    @Mock
    lateinit var repository: HttpLogRepository

    @InjectMocks
    lateinit var listener: HttpLogRequestEventListener

    @ParameterizedTest
    @ArgumentsSource(HttpLogArgumentsProvider::class)
    fun `should save HttpLog with success`(requestBody: String?, responseBody: String?, headers: String) {
        val event = HttpLogRequestEvent(
            method = "POST",
            path = "/games",
            statusCode = 201,
            responseTimeMs = 120,
            requestBody = requestBody,
            responseBody = responseBody,
            queryParams = "foo=bar",
            ip = "127.0.0.1",
            headers = headers
        )

        val captor = ArgumentCaptor.forClass(HttpLog::class.java)

        listener.onLogRequestEvent(event)

        verify(repository).save(captor.capture())

        val saved = captor.value
        assertThat(saved.method).isEqualTo(event.method)
        assertThat(saved.path).isEqualTo(event.path)
        assertThat(saved.status).isEqualTo(event.statusCode)
        assertThat(saved.responseTimeMs).isEqualTo(event.responseTimeMs)
        assertThat(saved.queryParameters).isEqualTo(event.queryParams)
        assertThat(saved.ip).isEqualTo(event.ip)
        assertThat(saved.headers).isEqualTo(headers.take(5000))
        assertThat(saved.requestBody).isEqualTo(requestBody?.take(5000))
        assertThat(saved.responseBody).isEqualTo(responseBody?.take(5000))

        assertThat(saved.requestBody?.length ?: 0).isLessThanOrEqualTo(5000)
        assertThat(saved.responseBody?.length ?: 0).isLessThanOrEqualTo(5000)
        assertThat(saved.headers.length).isLessThanOrEqualTo(5000)

    }

    private class HttpLogArgumentsProvider : ArgumentsProvider {

        override fun provideArguments(context: org.junit.jupiter.api.extension.ExtensionContext): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(
                    "x".repeat(6000), // Caso requestBody ultrapassa o limite
                    "y".repeat(4500), // Dentro do limite para responseBody
                    "some-header: value".repeat(300) // Headers excedendo o limite
                ),
                Arguments.of(
                    null, // requestBody nulo
                    "z".repeat(5000), // responseBody exatamente no limite
                    "header: value; header2: value2;" // Headers perfeitamente válidos
                ),
                Arguments.of(
                    "short body", // requestBody curto
                    "response", // responseBody curto
                    "" // Headers vazio
                )
            )
        }
    }

}