package com.codingtroops.foodies.utils

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.codingtroops.foodies.R

@Composable
fun ShowDialog(
    title: String?,
    message: String? = null,
    textPositive: String? = null,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    AlertDialog(
        title = {
            title?.let { Text(it) }
        },
        text = {
            message?.let { Text(text = it) }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(textPositive ?: context.getString(R.string.ok))
            }
        },
        dismissButton = {}
    )
}

/*
@Composable
fun ShowDialog(
    title: String?,
    message: String? = null,
    textPositive: String?,
    onDismiss: (() -> Unit)? = null,
    onPositive: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss?.invoke()
        },
        title = {
            title?.let { Text(text = it) }
        },
        text = {
            message?.let { Text(it) }
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onPositive.invoke()
                    }
                ) {
                    textPositive?.let { Text(it) }
                }
            }
        }
    )
}*/
