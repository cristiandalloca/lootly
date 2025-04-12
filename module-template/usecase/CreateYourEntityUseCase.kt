package com.cdbfkk.lootly.modules.template.usecase

class CreateYourEntityUseCase(
    private val repository: YourEntityRepository
) {
    fun execute(request: CreateYourEntityRequest) {
        val entity = YourEntity(name = request.name, description = request.description)
        repository.save(entity)
    }
}