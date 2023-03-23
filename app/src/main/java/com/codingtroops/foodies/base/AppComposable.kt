package com.codingtroops.foodies.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codingtroops.foodies.R
import com.codingtroops.foodies.ui.widgets.LoadingBar
import com.codingtroops.foodies.utils.ShowDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun AppComposable(
    viewModel: BaseViewModel,
    content: @Composable () -> Unit
) {

    val isLoading by viewModel.loading.observeAsState(initial = false)

    val error: Flow<ErrorContract?> = viewModel.error.receiveAsFlow()

    val showDialog: MutableState<ErrorContract?> = remember { mutableStateOf(null) }

    // Listen for side effects from the VM
    LaunchedEffect(error) {
        error.onEach { error ->
            showDialog.value = error
        }.collect()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        content()
    }

    if (isLoading) {
        LoadingBar()
    }

    when (showDialog.value) {
        ErrorContract.BadGateway -> {
            stringResource(id = R.string.no_internet_connection)
        }
        ErrorContract.ConnectTimeout -> {
            stringResource(id = R.string.connect_timeout)
        }
        ErrorContract.Forbidden -> {
            stringResource(id = R.string.forbidden)
        }
        ErrorContract.ForceUpdateApp -> {
            stringResource(id = R.string.force_update_app)
        }
        ErrorContract.HttpNotFound -> {
            stringResource(id = R.string.unknown_error)
        }
        ErrorContract.NoInternet -> {
            stringResource(id = R.string.no_internet_connection)
        }
        ErrorContract.ServerMaintain -> {
            stringResource(id = R.string.server_maintain_message)
        }
        ErrorContract.UnAuthorize -> {
            stringResource(id = R.string.unknown_error)
        }
        is ErrorContract.UnknownError -> {
            stringResource(id = R.string.unknown_error)
        }
        is ErrorContract.ServerError -> {
            (showDialog.value as ErrorContract.ServerError).message
        }
        else -> {
            null
        }
    }?.apply {
        ShowDialog(title = this) {
            showDialog.value = null
        }
    }
}
