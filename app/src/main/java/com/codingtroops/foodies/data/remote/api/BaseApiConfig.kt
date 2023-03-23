package com.codingtroops.foodies.data.remote.api

import com.codingtroops.foodies.model.ErrorState
import com.codingtroops.foodies.model.ErrorType
import com.codingtroops.foodies.model.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseApiConfig {

    protected suspend fun <T : Any> safeApiCallFlow(
        call: suspend () -> T
    ): Flow<ResultWrapper<T>> {
        return try {
            flowOf(ResultWrapper.success(call.invoke()))
        } catch (throwable: Throwable) {
            // return specific error
            flowOf(ResultWrapper.error(throwable))
        }
    }

}