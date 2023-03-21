package com.example.musicplayer.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.widget.RemoteViews
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.musicplayer.R
import com.example.musicplayer.giveDarkFadelEffect

/**
 * Created by Tirth Patel.
 */


fun musicNotification(
    mContext: Context,
    mName: String?,
    mArtist: String?,
    songPath: String?,
    minutes: Long?,
    second: Long?,
    playSong: MediaPlayer,
    handler: Handler,
    clMain: ConstraintLayout
) {

    val CHANNEL_ID = "CHANNEL_ID"
    val NOTIFICATION_ID = 102
    val bitmap: Bitmap?
    lateinit var runnable: Runnable
    val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(mContext)


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val chanel = NotificationChannel(
            CHANNEL_ID,
            "Music Player",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(chanel)
    }


    val packageName = mContext.applicationContext.packageName

    //Set Small Notification
    val notificationLayoutSmall = RemoteViews(packageName, R.layout.music_notification_small)

    notificationLayoutSmall.setTextViewText(R.id.txt_title_notification, mName)
    notificationLayoutSmall.setTextViewText(R.id.txt_artist_notification, mArtist)

    notificationLayoutSmall.setImageViewResource(R.id.img_previous_notification, R.drawable.ic_skip_previous)
    notificationLayoutSmall.setImageViewResource(R.id.img_next_notification, R.drawable.ic_pause_arrow)
    notificationLayoutSmall.setImageViewResource(R.id.img_play_pause_notification, R.drawable.ic_skip_next)
//    notificationLayout.setImageViewResource(R.id.img_close_notification, R.drawable.ic_close)

    //Set Big Notification
    val notificationLayoutBig = RemoteViews(packageName, R.layout.music_notification_large)


    notificationLayoutBig.setTextViewText(R.id.txt_title_notification_large, mName)
    notificationLayoutBig.setTextViewText(R.id.txt_artist_notification_large, mArtist)
    val mmr = MediaMetadataRetriever()
    mmr.setDataSource(songPath)
    val data = mmr.embeddedPicture
    if (data != null) {
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
        val setFadelImage = giveDarkFadelEffect(bitmap)
        notificationLayoutBig.setImageViewBitmap(R.id.song_img_notification, setFadelImage)
    }

    notificationLayoutBig.setTextViewText(
        R.id.txt_totalTime_notification,
        "${minutes.toString()}:${second.toString()}"
    )

    notificationLayoutBig.setImageViewResource(R.id.img_previous_notification_large, R.drawable.ic_skip_previous)
    notificationLayoutBig.setImageViewResource(R.id.img_play_pause_notification_large, R.drawable.ic_pause_arrow)
    notificationLayoutBig.setImageViewResource(R.id.img_next_notification_large, R.drawable.ic_skip_next)
    notificationLayoutBig.setImageViewResource(R.id.img_close_notification_large, R.drawable.ic_close)

/*
    runnable = Runnable {
        val rMinutes = TimeUnit.MILLISECONDS.toMinutes(playSong.currentPosition.toLong())
        val rSeconds = (TimeUnit.MILLISECONDS.toSeconds(playSong.currentPosition.toLong()) % 60)

        notificationLayoutBig.setTextViewText(
            R.id.txt_running_time_notification,
            "$rMinutes:$rSeconds"
        )

//        binding.seekbar.progress = playSong.currentPosition
        handler.postDelayed(runnable, 0)
    }
    handler.postDelayed(runnable, 0)
*/


    val notification = NotificationCompat.Builder(mContext, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_library_music)
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        .setCustomContentView(notificationLayoutSmall)
        .setCustomBigContentView(notificationLayoutBig)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .build()

    notificationManager.notify(NOTIFICATION_ID, notification)

/*
// Gestures android notification

// Create the notification layout for the collapsed state
    val collapsedView = RemoteViews(packageName, R.layout.music_notification_small)

// Create the notification layout for the expanded state
    val expandedView = RemoteViews(packageName, R.layout.music_notification_large)

// Set up the notification builder
    val builder = NotificationCompat.Builder(mContext, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_library_music)
        .setContentTitle("Notification Title")
        .setContentText("Notification Text")
        .setContent(collapsedView)
        .setCustomBigContentView(notificationLayoutBig)

// Set up the touch events for the notification layout
    val intent = Intent(mContext, MyBroadcastReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    notificationLayoutSmall.setOnClickPendingIntent(R.id.cv_songList_notification, pendingIntent)
    notificationLayoutSmall.setOnClickPendingIntent(R.id.cv_songList_notification, pendingIntent)
    notificationLayoutBig.setOnClickPendingIntent(R.id.cv_songList_notification_large, pendingIntent)
    notificationLayoutBig.setOnClickPendingIntent(R.id.cv_songList_notification_large, pendingIntent)

// Set up the intent that will be broadcasted when the user swipes down on the notification
    val swipeIntent = Intent(mContext, MyBroadcastReceiver::class.java)
    swipeIntent.putExtra("swiped", true)
    val swipePendingIntent = PendingIntent.getBroadcast(mContext, 0, swipeIntent, PendingIntent.FLAG_IMMUTABLE)
    builder.setDeleteIntent(swipePendingIntent)

// Create the notification and show it
    val notification = builder.build()
    val notificationManagerChanges = mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManagerChanges.notify(NOTIFICATION_ID, notification)
    */

}