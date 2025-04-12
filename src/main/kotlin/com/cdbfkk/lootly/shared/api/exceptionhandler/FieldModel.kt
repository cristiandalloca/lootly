package com.cdbfkk.lootly.shared.api.exceptionhandler

import io.swagger.v3.oas.annotations.media.Schema

data class FieldModel(

    @Schema(example = "name", description = "Nome do campo")
    val name: String,

    @Schema(example = "Nome é obrigatório", description = "Mensagem de erro")
    val message: String
)
