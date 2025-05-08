package com.cdbfkk.lootly.modules.logs.listener

import com.cdbfkk.lootly.LootlyApplication
import com.cdbfkk.lootly.common.AbstractIntegrationTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.core.ApplicationModules

@SpringBootTest
@Tag("integration")
class ModuleStructureTest : AbstractIntegrationTest() {

    @Test
    fun `should verify gamodule structure`() {
        val modules = ApplicationModules.of(LootlyApplication::class.java)
        modules.verify()
    }

}