package com.spark.binders.exception

import org.springframework.graphql.execution.ErrorType

sealed class ServerException(
    val code: Int,
    override val message: String,
    open val errorType: ErrorType,
) : RuntimeException()

data class NotFoundException(
    override val message: String = "요청하신 정보가 존재하지 않습니다.",
    override val errorType: ErrorType = ErrorType.NOT_FOUND,
): ServerException(404, message, errorType)

data class UnAuthorizedException(
    override val message: String = "인증 정보가 잘못되었습니다.",
    override val errorType: ErrorType = ErrorType.UNAUTHORIZED,
): ServerException(401, message, errorType)