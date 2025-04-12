package com.cdbfkk.lootly.modules.template.domain.repository

import com.cdbfkk.lootly.modules.template.domain.model.YourEntity

interface YourEntityRepository {
    fun save(entity: YourEntity)
    fun findAll(): List<YourEntity>
}