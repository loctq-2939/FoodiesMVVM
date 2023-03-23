package com.codingtroops.foodies.firebase

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleObserver
import com.codingtroops.foodies.utils.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirebaseCloudMessagingService : FirebaseMessagingService(), LifecycleObserver {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // save token
    }

    private fun sendNotification(
        title: String?,
        content: String?,
        applicationContext: Context
    ) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(
            title = title,
            messageBody = content,
            applicationContext = applicationContext
        )
    }

}