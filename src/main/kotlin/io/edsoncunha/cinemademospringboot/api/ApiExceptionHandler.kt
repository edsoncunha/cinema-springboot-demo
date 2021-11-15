package io.edsoncunha.cinemademospringboot.api


import io.edsoncunha.cinemademospringboot.domain.exceptions.NotFoundException
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.servlet.http.HttpServletRequest
import javax.validation.ValidationException


@ControllerAdvice
class ApiExceptionHandler {
    private val logger = LogManager.getLogger()

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(
        request: HttpServletRequest,
        ex: NotFoundException
    ): ResponseEntity<ApiCallError<String>> {
        logger.error("NotFoundException {}\n", request.requestURI, ex)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiCallError("Not found exception", listOf(ex.message)))
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(
        request: HttpServletRequest,
        ex: ValidationException
    ): ResponseEntity<ApiCallError<String>> {
        logger.error("ValidationException {}\n", request.requestURI, ex)
        return ResponseEntity
            .badRequest()
            .body(ApiCallError("Validation exception", listOf(ex.message)))
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(
        request: HttpServletRequest,
        ex: MissingServletRequestParameterException
    ): ResponseEntity<ApiCallError<String>> {
        logger.error("handleMissingServletRequestParameterException {}\n", request.requestURI, ex)
        return ResponseEntity
            .badRequest()
            .body(ApiCallError("Missing request parameter", listOf(ex.message)))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        request: HttpServletRequest,
        ex: MethodArgumentTypeMismatchException
    ): ResponseEntity<ApiCallError<Map<String, Any?>>> {
        logger.error("handleMethodArgumentTypeMismatchException {}\n", request.requestURI, ex)
        val details = mapOf(
            "paramName" to ex.name,
            "paramValue" to ex.value,
            "errorMessage" to ex.message
        )
        return ResponseEntity
            .badRequest()
            .body(ApiCallError("Method argument type mismatch", listOf(details)))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        request: HttpServletRequest,
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ApiCallError<Map<String, String?>>> {
        logger.error("handleMethodArgumentNotValidException {}\n", request.requestURI, ex)
        val details = ex.bindingResult
            .fieldErrors
            .map {
                mapOf(
                    "objectName" to it.objectName,
                    "field" to it.field,
                    "rejectedValue" to it.rejectedValue.toString(),
                    "errorMessage" to it.defaultMessage
                )
            }

        return ResponseEntity
            .badRequest()
            .body(ApiCallError("Method argument validation failed", details))
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(
        request: HttpServletRequest,
        ex: AccessDeniedException
    ): ResponseEntity<ApiCallError<String>> {
        logger.error("handleAccessDeniedException {}\n", request.requestURI, ex)
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ApiCallError("Access denied!", listOf(ex.message)))
    }

    @ExceptionHandler(Exception::class)
    fun handleInternalServerError(request: HttpServletRequest, ex: Exception): ResponseEntity<ApiCallError<String>> {
        logger.error("handleInternalServerError {}\n", request.requestURI, ex)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiCallError("Internal server error", listOf(ex.message)))
    }


    data class ApiCallError<T>(
        val message: String,
        val details: List<T?> = listOf()
    )
}
