package com.example.musicplayer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.AudioFileViewHolder
import com.example.musicplayer.R
import com.example.musicplayer.data.AudioFile

/**
 * Created by Tirth Patel.
 */

class AudioFileAdapter(private val context : Context, private val audioFile: List<AudioFile>) :
    RecyclerView.Adapter<AudioFileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioFileViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_audio_file, parent, false)
        return AudioFileViewHolder(itemView)
    }

    override fun getItemCount()= audioFile.size



    override fun onBindViewHolder(holder: AudioFileViewHolder, position: Int) {
        holder.bind(audioFile[position])
    }

}