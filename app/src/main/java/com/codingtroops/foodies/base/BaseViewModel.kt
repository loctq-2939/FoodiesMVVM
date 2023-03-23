package com.codingtroops.foodies.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingtroops.foodies.model.ErrorType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : LifecycleObserver, ViewModel() {

    val loading: LiveData<Boolean>
        get() = _loading

    val isError = MutableLiveData<Boolean>()

    var error = Channel<ErrorContract>(Channel.UNLIMITED)
        private set

    // optional flags

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()

    protected fun setLoading(isLoading: Boolean) = _loading.postValue(isLoading)

    open fun onError(throwable: Throwable) {
        viewModelScope.launch {
            isError.value = true
            throwable.toBaseException().apply {
                when (this.errorType) {
                    ErrorType.NETWORK -> {
                        handleNetworkError(this)
                    }
                    ErrorType.HTTP -> {
                        handleHttpError(this)
                    }
                    ErrorType.SERVER -> {
                        this.serverErrorResponse?.let {
                            it.message?.let { message ->
                                error.send(
                                    ErrorContract.ServerError(
                                        message
                                    )
                                )
                            }
                        }
                    }
                    else -> {
                        when (throwable.cause) {
                            is UnknownHostException -> {
                                error.send(ErrorContract.NoInternet)
                            }
                            is ConnectException -> {
                                error.send(ErrorContract.NoInternet)
                            }
                            is SocketTimeoutException -> {
                                error.send(ErrorContract.ConnectTimeout)
                            }
                            else -> {
                                error.send(ErrorContract.UnknownError(httpCode))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleNetworkError(baseException: BaseException) {
        viewModelScope.launch {
            when (baseException.cause) {
                is UnknownHostException -> {
                    error.send(ErrorContract.NoInternet)
                }
                is ConnectException -> {
                    error.send(ErrorContract.NoInternet)
                }
                is SocketTimeoutException -> {
                    error.send(ErrorContract.ConnectTimeout)
                }
            }
        }
    }

    private fun handleHttpError(baseException: BaseException) {
        viewModelScope.launch {
            when (baseException.httpCode) {
                HttpURLConnection.HTTP_BAD_REQUEST -> {
                    error.send(ErrorContract.UnknownError(baseException.httpCode))
                }
                HttpURLConnection.HTTP_UNAVAILABLE -> {
                    error.send(ErrorContract.ServerMaintain)
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    error.send(ErrorContract.UnAuthorize)
                }
                HttpURLConnection.HTTP_FORBIDDEN -> {
                    error.send(ErrorContract.Forbidden)
                }
                HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> {
                    error.send(ErrorContract.BadGateway)
                }
                HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                    error.send(ErrorContract.UnknownError(baseException.httpCode))
                }
            }
        }
    }
}