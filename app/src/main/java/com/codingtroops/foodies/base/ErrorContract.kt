package com.codingtroops.foodies.base

sealed class ErrorContract {
    object NoInternet : ErrorContract()
    object ConnectTimeout : ErrorContract()
    object ForceUpdateApp : ErrorContract()
    object ServerMaintain : ErrorContract()
    object Forbidden : ErrorContract()
    object HttpNotFound : ErrorContract()
    object BadGateway : ErrorContract()
    object UnAuthorize : ErrorContract()
    data class UnknownError(val code: Int) : ErrorContract()
    data class ServerError(val message: String) : ErrorContract()
}