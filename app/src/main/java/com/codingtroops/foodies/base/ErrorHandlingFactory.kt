package com.codingtroops.foodies.base

import com.codingtroops.foodies.data.remote.api.UNKNOWN_API_ERROR
import com.codingtroops.foodies.model.ErrorList
import com.codingtroops.foodies.model.ErrorState
import com.codingtroops.foodies.model.ErrorType
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


fun Throwable.toBaseException(): BaseException {
    return when (val throwable = this) {
        is BaseException -> throwable

        is IOException -> BaseException.toNetworkError(throwable)

        is HttpException -> {
            val response = throwable.response()
            val httpCode = throwable.code()

            if (response?.errorBody() == null) {
                return BaseException.toHttpError(
                    httpCode = httpCode,
                    response = response
                )
            }

            val serverErrorResponseBody = try {
                response.errorBody()
                    ?.string() ?: ""
            } catch (e: Exception) {
                ""
            }

            val serverErrorResponse =
                try {
                    Gson().fromJson(serverErrorResponseBody, ErrorState::class.java)
                } catch (e: Exception) {
                    try {
                        Gson().fromJson(serverErrorResponseBody, ErrorList::class.java)
                    } catch (e: Exception) {
                        ErrorState(message = UNKNOWN_API_ERROR)
                    }
                }

            if (serverErrorResponse != null) {
                when (serverErrorResponse) {
                    is ErrorState -> {
                        BaseException.toServerError(
                            serverErrorResponse = serverErrorResponse.apply {
                                errorType = ErrorType.SERVER
                                code = httpCode
                            },
                            response = response,
                            httpCode = httpCode
                        )
                    }
                    is ErrorList -> {
                        BaseException.toServerError(
                            serverErrorResponse = ErrorState(
                                serverErrorResponse.success,
                                serverErrorResponse.message?.get(0)?.message,
                                code = httpCode,
                                ErrorType.SERVER,
                            ),
                            response = response,
                            httpCode = httpCode
                        )
                    }
                    else -> BaseException.toHttpError(
                        response = response,
                        httpCode = httpCode
                    )
                }
            } else {
                BaseException.toHttpError(
                    response = response,
                    httpCode = httpCode
                )
            }
        }

        else -> BaseException.toUnexpectedError(throwable)
    }
}

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
