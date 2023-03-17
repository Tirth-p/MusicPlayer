package com.example.musicplayer.utils

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder

/**
 * Created by Tirth Patel.
 */

class MyService() : Service() {

    lateinit var handler: Handler
    private val myRunnable = object : Runnable {
        override fun run() {
            // do some work here
            handler?.postDelayed(this, 1000)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        handler = Handler()
        handler?.post(myRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(myRunnable)
    }
}
