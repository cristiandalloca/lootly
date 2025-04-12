package com.cdbfkk.lootly.modules.template.infrastructure.persistence

import com.cdbfkk.lootly.modules.template.domain.model.YourEntity
import com.cdbfkk.lootly.modules.template.domain.repository.YourEntityRepository
import org.springframework.stereotype.Repository

@Repository
class YourEntityJpaRepository(
    private val springDataRepository: SpringDataYourEntityRepository
) : YourEntityRepository {

    override fun save(entity: YourEntity) {
        springDataRepository.save(entity.toJpaEntity())
    }

    override fun findAll(): List<YourEntity> {
        return springDataRepository.findAll().map { it.toDomain() }
    }
}