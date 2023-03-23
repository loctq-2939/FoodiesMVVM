package com.codingtroops.foodies.permission

import android.Manifest
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(onPermissionsGranted:()->Unit) {
    val permissionState = rememberMultiplePermissionsState(
        permissions =
        listOf(
            Manifest.permission.CAMERA,
        )
    )

    if (!permissionState.permissionRequested && !permissionState.shouldShowRationale) {
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
    } else if (permissionState.shouldShowRationale) {
        /*ShowRationaleContent {
            permissionState.launchMultiplePermissionRequest()
        }*/

    } else {
        /*ShowOpenSettingsContent {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri: Uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }*/
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermission(
    permissionState: PermissionState,
    permissionNotGrantedContent: (() -> Unit)? = null,
    permissionNotAvailableContent: (() -> Unit)? = null,
) {
    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
            permissionNotGrantedContent?.invoke()
        },
        permissionNotAvailableContent = {
            permissionNotAvailableContent?.invoke()
        }
    ) {
        // Open Camera
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissions(
    multiplePermissionState: MultiplePermissionsState
) {
    PermissionsRequired(
        multiplePermissionsState = multiplePermissionState,
        permissionsNotGrantedContent = { /* ... */ },
        permissionsNotAvailableContent = { /* ... */ }
    ) {
        // Use location
    }
}