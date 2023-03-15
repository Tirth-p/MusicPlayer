package com.example.musicplayer

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.data.AudioFile

/**
 * Created by Tirth Patel.
 */
class AudioFileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(audioFile: AudioFile) {

        itemView.findViewById<TextView>(R.id.txt_song_title).text = audioFile.name
        itemView.findViewById<TextView>(R.id.txt_artist).text = audioFile.artist

        itemView.findViewById<CardView>(R.id.cv_songListDevice).setOnClickListener {
            val intent = Intent(itemView.context, MusicPlayerActivity::class.java)
            itemView.context.startActivity(intent)

            MyPreferences.setStringPref(itemView.context, "SONG_NAME", audioFile.name)
            MyPreferences.setStringPref(itemView.context, "SONG_ARTIST", audioFile.artist)
            MyPreferences.setStringPref(itemView.context, "SONG_PATH", audioFile.path)
            MyPreferences.setStringPref(itemView.context, "DURATION", audioFile.duration)

            MyPreferences.setIntPref(itemView.context, "SONG_POSITION", position)

            Log.e("TAG", "onBindViewHolder: $position")
            Log.e("TAG", "onBindViewHolder: $audioFile")

        }
        val dataImage = audioFile.path

        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(dataImage)
        val data = mmr.embeddedPicture
        if (data != null) {
            val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)

            itemView.findViewById<ImageView>(R.id.img_song).setImageBitmap(bitmap)
        } else {
            itemView.findViewById<ImageView>(R.id.img_song).setImageResource(R.drawable.ic_play)
        }

    }
}