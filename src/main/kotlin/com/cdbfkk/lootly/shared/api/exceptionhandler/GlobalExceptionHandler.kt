package com.cdbfkk.lootly.shared.api.exceptionhandler

import com.cdbfkk.lootly.shared.exception.BaseException
import com.cdbfkk.lootly.shared.exception.BusinessException
import com.cdbfkk.lootly.shared.exception.NotFoundException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler(
    private val messageSource: MessageSource
) : ResponseEntityExceptionHandler() {

    private companion object {
        private const val DEFAULT_MESSAGE_KEY = "internal.error"
    }

    @ExceptionHandler(BaseException::class)
    fun handleBusinessException(ex: BaseException): ResponseEntity<ProblemModel> {
        val status = when (ex) {
            is BusinessException -> HttpStatus.BAD_REQUEST
            is NotFoundException -> HttpStatus.NOT_FOUND
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        return createResponseEntity(status, ex.messageKey, ex.args)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ProblemModel> {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_MESSAGE_KEY)
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val key = ex::class.simpleName ?: DEFAULT_MESSAGE_KEY
        return createResponseEntity(HttpStatus.valueOf(statusCode.value()), key) as? ResponseEntity<Any>
    }

    private fun createResponseEntity(httpStatus: HttpStatus, key: String, args: Array<String>? = null):
            ResponseEntity<ProblemModel> {
        return ResponseEntity
            .status(httpStatus)
            .body(
                ProblemModel(
                    status = httpStatus.value(),
                    key = key,
                    message = getMessageForKey(key, args)
                )
            )
    }

    private fun getMessageForKey(key: String, args: Array<String>? = null): String {
        val locale = LocaleContextHolder.getLocale()
        return messageSource.getMessage(key, args ?: emptyArray(), "Chave: $key não encontrada", locale).orEmpty()
    }
}