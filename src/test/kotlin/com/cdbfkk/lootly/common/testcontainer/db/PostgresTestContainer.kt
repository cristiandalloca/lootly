package com.cdbfkk.lootly.common.testcontainer.db

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

object PostgresTestContainer {

    private const val POSTGRES_IMAGE_VERSION = "postgres:16"
    private const val DATABASE_NAME = "test_db"
    private const val USERNAME = "test"
    private const val PASSWORD = "test"

    lateinit var container: PostgreSQLContainer<*>

    fun start() {
        if (!::container.isInitialized) {
            container = createPostgresContainer()
        }
        container.start()
    }

    fun stop() {
        if (::container.isInitialized && container.isRunning) {
            container.stop()
        }
    }

    private fun createPostgresContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer<Nothing>(POSTGRES_IMAGE_VERSION).apply {
            withDatabaseName(DATABASE_NAME)
            withUsername(USERNAME)
            withPassword(PASSWORD)
            withReuse(true)
        }
    }

}