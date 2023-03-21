package com.example.musicplayer

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.adapter.AudioFileAdapter
import com.example.musicplayer.data.AudioFile
import com.example.musicplayer.databinding.ActivityDeviceMusicBinding
import org.checkerframework.checker.units.qual.m

open class DeviceMusic : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceMusicBinding
    val songsList = mutableListOf<String>()
    var currentIndex: Int = 0
    private var mScrollPosition = 0
    lateinit var recyclerView: RecyclerView
    private val READ_REQUEST_CODE = 101



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        askForPermission()
        init()


        if (savedInstanceState != null) {
            mScrollPosition = savedInstanceState.getInt("scroll_position")
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                mScrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
            }
        })
    }

    private fun init() {
        recyclerView = findViewById<RecyclerView>(R.id.rv_device_audio)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val audioFiles = getAudioFilesFromDevice()
        val adapter = AudioFileAdapter(this, audioFiles)
        recyclerView.adapter = adapter
    }

    private fun askForPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.READ_EXTERNAL_STORAGE),READ_REQUEST_CODE)
        }
    }

    @SuppressLint("Range")
    fun getAudioFilesFromDevice(): List<AudioFile> {
        val audioFile = mutableListOf<AudioFile>()
        val uri = Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(Media._ID, Media.DISPLAY_NAME, Media.DATA, Media.ARTIST, Media.DURATION)
        val selection = Media.IS_MUSIC + "!=0"
        val sortOrder = "${Media.DISPLAY_NAME} ASC"

        val cursor = contentResolver.query(uri, projection, selection, null, sortOrder)
        var i = 0
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                songsList.add(filePath)

                val name = cursor.getString(cursor.getColumnIndex(Media.DISPLAY_NAME))
                val path = cursor.getString(cursor.getColumnIndex(Media.DATA))
                val artist = cursor.getString(cursor.getColumnIndex(Media.ARTIST))
                val duration = cursor.getString(cursor.getColumnIndex(Media.DURATION))
                if (duration != null) {
                    audioFile.add(AudioFile(name, path, artist, duration))
                } else {
                    audioFile.add(AudioFile(name, path, artist, "00:00"))
                }
            }

            cursor.close()
        }
        return audioFile
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mScrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
        outState.putInt("scroll_position", mScrollPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mScrollPosition = savedInstanceState.getInt("scroll_position")
    }

    override fun onBackPressed() {
        (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
            mScrollPosition,
            0
        )
        super.onBackPressed()
    }
}