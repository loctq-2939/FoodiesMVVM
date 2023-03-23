package com.codingtroops.foodies.model

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onCompletion
import java.io.IOException

data class ResultWrapper<out T : Any>(val status: Status, val data: T?,
                                      val throwable: Throwable? = null) {
    companion object {
        fun <T : Any> success(data: T): ResultWrapper<T> = ResultWrapper(status = Status.SUCCESS,
            data = data)

        fun <T : Any> error(errorState: Throwable? = null): ResultWrapper<T> =
            ResultWrapper(status = Status.ERROR, null, errorState)
    }
}

enum class Status {
    SUCCESS,
    ERROR
}

suspend fun <T : Any> Flow<ResultWrapper<T>>.handleResponse(
    onCompletion: (() -> Unit)? = null,
    onSuccess: ((T) -> Unit)? = null,
    onError: ((Throwable) -> Unit)? = null,
) {
    onCompletion {
        onCompletion?.invoke()
    }.distinctUntilChanged()
        .collect {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> onSuccess?.invoke(data) }
                }
                Status.ERROR -> {
                    it.throwable?.let { throwable ->
                        onError?.invoke(throwable)
                    }
                }
            }
        }
}

data class ErrorState(
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("code")
    var code: Int? = null,
    var errorType: ErrorType? = ErrorType.UNEXPECTED,
)

data class ErrorList(
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("message")
    val message: List<Message>? = null,
    @SerializedName("code")
    val code: Int? = null,
    var errorType: ErrorType? = ErrorType.SERVER
)

data class Message(
    var message: String? = null
)

/**
 * Identifies the error type which triggered a [BaseException]
 */
enum class ErrorType {
    /**
     * An [IOException] occurred while communicating to the server.
     */
    NETWORK,

    /**
     * A non-2xx HTTP status code was received from the server.
     */
    HTTP,

    /**
     * Time-out request
     */
    TIME_OUT,

    /**
     * A error server with code & message
     */
    SERVER,

    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED,

}