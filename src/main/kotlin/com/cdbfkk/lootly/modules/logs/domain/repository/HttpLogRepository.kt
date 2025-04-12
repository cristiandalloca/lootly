package com.cdbfkk.lootly.modules.logs.domain.repository

import com.cdbfkk.lootly.modules.logs.domain.model.HttpLog
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface HttpLogRepository : CrudRepository<HttpLog, UUID> {
}