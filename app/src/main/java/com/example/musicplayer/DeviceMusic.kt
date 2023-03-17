package com.example.musicplayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.adapter.AudioFileAdapter
import com.example.musicplayer.data.AudioFile
import com.example.musicplayer.databinding.ActivityDeviceMusicBinding

open class DeviceMusic : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceMusicBinding
    val songsList = mutableListOf<String>()
    var currentIndex: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_device_audio)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val audioFiles = getAudioFilesFromDevice()
        val adapter = AudioFileAdapter(this,audioFiles)
        recyclerView.adapter = adapter
    }

    @SuppressLint("Range")
    fun getAudioFilesFromDevice(): List<AudioFile> {
        val audioFile = mutableListOf<AudioFile>()
        val uri = Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(Media._ID, Media.DISPLAY_NAME,Media.DATA,Media.ARTIST,Media.DURATION)
        val selection = Media.IS_MUSIC + "!=0"
        val sortOrder = "${Media.DISPLAY_NAME} ASC"

        val cursor = contentResolver.query(uri, projection, selection, null, sortOrder)

        if (cursor != null){
            while (cursor.moveToNext()){
                val filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                 songsList.add(filePath)


                val name = cursor.getString(cursor.getColumnIndex(Media.DISPLAY_NAME))
                val path = cursor.getString(cursor.getColumnIndex(Media.DATA))
                val artist = cursor.getString(cursor.getColumnIndex(Media.ARTIST))
                val duration = cursor.getString(cursor.getColumnIndex(Media.DURATION))
                if (duration != null) {
                    audioFile.add(AudioFile(name, path, artist, duration))
                }else{
                    audioFile.add(AudioFile(name, path, artist,"00:00"))
                }
            }
            cursor.close()
        }
        return audioFile
    }
}