package com.codingtroops.foodies.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import com.codingtroops.foodies.R
import com.codingtroops.foodies.ui.MainActivity

// Notification ID.
private const val NOTIFICATION_REQUEST_ID = 0

fun NotificationManager.sendNotification(
    id: Int? = null,
    title: String? = null,
    messageBody: String?,
    applicationContext: Context
) {

    val flags = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
        else -> FLAG_UPDATE_CURRENT
    }

    val contentIntent = Intent(applicationContext, MainActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }

    val pendingContentIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_REQUEST_ID,
        contentIntent,
        flags
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )

    if (title.isNullOrEmpty()) {
        builder.setContentTitle(applicationContext.getString(R.string.notification_title))
    } else builder.setContentTitle(title)

    builder
        .setColor(
            ResourcesCompat.getColor(
                applicationContext.resources,
                R.color.notifyIconBackground,
                null
            )
        )
        .setContentText(messageBody)
        .setContentIntent(pendingContentIntent)
        .setAutoCancel(true)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(messageBody)
        )

    notify(id ?: System.currentTimeMillis().toInt(), builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}

fun Context.createChannel(channelId: String, channelName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}