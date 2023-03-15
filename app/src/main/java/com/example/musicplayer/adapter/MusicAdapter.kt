package com.example.musicplayer.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.data.ItemsViewModel
import com.example.musicplayer.`interface`.MusicCallBack


/**
 * Created by Tirth Patel.
 */
class MusicAdapter(
    private val mList: List<ItemsViewModel>,
    val musicCallBack: MusicCallBack
) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_song, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_song)
        val songTitle: TextView = itemView.findViewById(R.id.txt_song_title)
        val songArtist: TextView = itemView.findViewById(R.id.txt_artist)
        val cvSongList: CardView = itemView.findViewById(R.id.cv_songList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.songTitle.text = mList[position].songFile.toString()
        Log.e("TAG", "onCreateViewHolder: ${mList[position].songFile}")

        holder.cvSongList.setOnClickListener {
            musicCallBack.onClickHandel(position)
        }
    }


}