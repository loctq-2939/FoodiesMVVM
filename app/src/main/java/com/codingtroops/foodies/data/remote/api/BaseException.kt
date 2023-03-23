package com.codingtroops.foodies.data.remote.api

import com.codingtroops.foodies.model.ErrorState
import com.codingtroops.foodies.model.ErrorType
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

const val UNKNOWN_API_ERROR = "Unknown API error"

class BaseException(
    val errorType: ErrorType,
    val serverErrorResponse: ErrorState? = null,
    val response: Response<*>? = null,
    val httpCode: Int = 0,
    cause: Throwable? = null
) : RuntimeException(cause?.message, cause) {

    override val message: String?
        get() = when (errorType) {
            ErrorType.HTTP -> response?.message()

            ErrorType.NETWORK, ErrorType.TIME_OUT -> cause?.message

            ErrorType.SERVER -> serverErrorResponse?.message

            ErrorType.UNEXPECTED -> cause?.message
        }

    companion object {
        fun toHttpError(response: Response<*>?, httpCode: Int) =
            BaseException(
                errorType = ErrorType.HTTP,
                response = response,
                httpCode = httpCode
            )

        fun toNetworkError(cause: Throwable) =
            BaseException(
                errorType = ErrorType.NETWORK,
                cause = cause
            )

        fun toServerError(
            serverErrorResponse: ErrorState,
            response: Response<*>?,
            httpCode: Int
        ) = BaseException(
            errorType = ErrorType.SERVER,
            serverErrorResponse = serverErrorResponse,
            response = response,
            httpCode = httpCode
        )

        fun toUnexpectedError(cause: Throwable) =
            BaseException(
                errorType = ErrorType.UNEXPECTED,
                cause = cause
            )
    }
}

fun Throwable.convertToBaseException(): BaseException =
    when (this) {
        is IOException -> BaseException.toNetworkError(this)

        is HttpException -> {
            val response = response()
            val httpCode = code()

            if (response?.errorBody() == null) {
                BaseException.toHttpError(
                    httpCode = httpCode,
                    response = response
                )
            }

            val serverErrorResponse =
                try {
                    Gson().fromJson(response?.errorBody()?.string(), ErrorState::class.java)
                } catch (e: Exception) {
                    ErrorState(message = UNKNOWN_API_ERROR)
                }

            if (serverErrorResponse != null) {
                BaseException.toServerError(
                    serverErrorResponse = serverErrorResponse,
                    httpCode = httpCode,
                    response = response
                )
            } else {
                BaseException.toHttpError(
                    response = response,
                    httpCode = httpCode
                )
            }
        }

        else -> BaseException.toUnexpectedError(this)
    }