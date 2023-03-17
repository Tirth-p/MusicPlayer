package com.example.musicplayer.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.view.View
import com.example.musicplayer.R
import androidx.core.app.NotificationCompat

class MyBroadcastReceiver : BroadcastReceiver() {
    val CHANNEL_ID = "CHANNEL_ID"
    val NOTIFICATION_ID = 102

/*    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "my_action") {
            // User touched the notification layout or the expand/collapse button
            if (isNotificationExpanded(context)) {
                collapseNotification(context)
            } else {
                expandNotification(context)
            }
        } else if (intent.action == "android.intent.action.NOTIFICATION_DELETED") {
            val notificationId = intent.getIntExtra("android.intent.extra.NOTIFICATION_ID", -1)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notificationId)
        }

    }*/

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "android.intent.action.NOTIFICATION_DELETED") {
                val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val activeNotifications = notificationManager.activeNotifications

                for (notification in activeNotifications) {
                    // process each active notification here
                }
            }
        }

/*

    private fun isNotificationExpanded(context: Context): Boolean {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val activeNotifications = notificationManager.activeNotifications
        for (notification in activeNotifications) {
            if (notification.id == NOTIFICATION_ID) {
                return notification.isExpanded
            }
        }
        return false
    }

    private fun expandNotification(context: Context) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification = notificationManager.getActiveNotification(CHANNEL_ID, NOTIFICATION_ID)
        val expandedView = notification.bigContentView ?: return
        notification.expandedView.setViewVisibility(R.id.cv_songList_notification_large, View.GONE)
        notification.expandedView.setViewVisibility(R.id.cv_songList_notification, View.VISIBLE)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun collapseNotification(context: Context) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification = notificationManager.getActiveNotification(CHANNEL_ID, NOTIFICATION_ID)
        val collapsedView = notification.contentView ?: return
        notification.collapsedView.setViewVisibility(
            R.id.cv_songList_notification_large, View.VISIBLE
        )
        notification.collapsedView.setViewVisibility(R.id.cv_songList_notification, View.GONE)
        notificationManager.notify(NOTIFICATION_ID, notification)

    }*/
}
