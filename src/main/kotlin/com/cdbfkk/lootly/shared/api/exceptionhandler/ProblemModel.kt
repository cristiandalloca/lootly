package com.cdbfkk.lootly.shared.api.exceptionhandler

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProblemModel(

    @Schema(example = "404", description = "Código do status HTTP")
    val status: Int,

    @Schema(example = "internal.error", description = "Chave da mensagem de erro")
    val key: String,

    @Schema(example = "Erro inesperado. Por favor, tente novamente.", description = "Mensagem de erro")
    val message: String,

    @Schema(example = "2024-07-15T11:21:50.902245498Z", description = "Data e hora no formato UTC que ocorreu o problema")
    val timestamp: OffsetDateTime = OffsetDateTime.now(),

    val details: List<FieldModel>? = null
)
