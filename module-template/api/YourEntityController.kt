package com.cdbfkk.lootly.modules.template.api

import com.cdbfkk.lootly.modules.template.usecase.CreateYourEntityUseCase
import com.cdbfkk.lootly.modules.template.usecase.FindYourEntityUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/your-entity")
class YourEntityController(
    private val createUseCase: CreateYourEntityUseCase,
    private val findUseCase: FindYourEntityUseCase
) {

    @PostMapping
    fun create(@RequestBody request: CreateYourEntityRequest): ResponseEntity<Unit> {
        createUseCase.execute(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<YourEntityResponse>> {
        val items = findUseCase.execute()
        return ResponseEntity.ok(items)
    }
}