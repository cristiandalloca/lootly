package com.cdbfkk.lootly.modules.template.usecase

class FindYourEntityUseCase(
    private val repository: YourEntityRepository
) {
    fun execute(): List<YourEntityResponse> {
        return repository.findAll().map { YourEntityResponse.from(it) }
    }
}