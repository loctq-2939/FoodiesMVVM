package com.codingtroops.foodies

import android.app.Application
import com.codingtroops.foodies.utils.createChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FoodiesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )
    }
}