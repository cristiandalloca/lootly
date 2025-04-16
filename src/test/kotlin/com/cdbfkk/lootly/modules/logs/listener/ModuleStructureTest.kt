package com.cdbfkk.lootly.modules.logs.listener

import com.cdbfkk.lootly.LootlyApplication
import com.cdbfkk.lootly.modules.games.GameModule
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.core.ApplicationModules

@SpringBootTest
@Tag("integration")
class ModuleStructureTest {

    @Test
    fun `should verify gamodule structure`() {
        val modules = ApplicationModules.of(LootlyApplication::class.java)
        modules.verify()
    }

}