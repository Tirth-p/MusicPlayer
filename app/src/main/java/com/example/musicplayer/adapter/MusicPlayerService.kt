package com.example.musicplayer.adapter

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationManagerCompat

/**
 * Created by Tirth Patel.
 */
class MusicPlayerService : Service() {

    private lateinit var notificationManager: NotificationManagerCompat
    private var isPlaying = false

    override fun onCreate() {
        super.onCreate()
        notificationManager = NotificationManagerCompat.from(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start playing music
        isPlaying = true
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop playing music
        isPlaying = false
        stopForeground(true)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

}
