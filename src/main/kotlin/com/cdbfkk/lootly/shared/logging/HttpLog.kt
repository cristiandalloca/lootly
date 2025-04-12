package com.cdbfkk.lootly.shared.logging

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "http_log")
data class HttpLog(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    val id: UUID? = null,

    @Column(name = "method", nullable = false, length = 10)
    val method: String,

    @Column(name = "path", nullable = false)
    val path: String,

    @Column(name = "ip", nullable = false)
    val ip: String,

    @Column(name = "status", nullable = false)
    val status: Int,

    @Column(name = "timestamp", nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @Column(name = "response_time_ms", nullable = false)
    val responseTimeMs: Long,

    @Column(name = "headers", nullable = false, columnDefinition = "TEXT")
    val headers: String,

    @Column(name = "query_parameters")
    val queryParameters: String? = null,

    @Column(name = "request_body", columnDefinition = "TEXT")
    val requestBody: String? = null,

    @Column(name = "response_body", columnDefinition = "TEXT")
    val responseBody: String? = null
)

