package com.example.musicplayer

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import androidx.core.net.toUri
import com.example.musicplayer.databinding.ActivityMusicPlayerBinding
import com.example.musicplayer.utils.musicNotification
import java.util.concurrent.TimeUnit


class MusicPlayerActivity : DeviceMusic() {
    lateinit var binding: ActivityMusicPlayerBinding
    var isPlaying = false
    lateinit var playSong: MediaPlayer
    lateinit var runnable: Runnable
    private var handler = Handler()
    var songDuration: Int = 0
    var songName: String? = null
    var songArtist: String? = null
    var songPath: String? = null
    var minutes: Long? = null
    var second: Long? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        musicNotification(
            this,
            songName,
            songArtist,
            songPath,
            minutes,
            second,
            playSong,
            handler,
            binding.clMain
        )

    }

    fun initView() {
        // Set Values with SharedPref
        songName = MyPreferences.getStringPref(this, "SONG_NAME")
        songArtist = MyPreferences.getStringPref(this, "SONG_ARTIST")
        songPath = MyPreferences.getStringPref(this, "SONG_PATH")
        songDuration = MyPreferences.getStringPref(this, "DURATION")!!.toInt()

        binding.txtName.isSelected = true
        binding.txtArtistName.isSelected = true
        binding.txtName.text = songName
        binding.txtArtistName.text = songArtist

        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(songPath)
        val data = mmr.embeddedPicture
        if (data != null) {
            val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)

            binding.imgSong.setImageBitmap(bitmap)
            applyBlueGradientBackground(binding.clMain)

            binding.swTheam.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val drawable = BitmapDrawable(resources, bitmap)
                    binding.clMain.background = drawable
                } else {
                    applyBlurBackground(binding.clMain, bitmap)
                }
            }

        } else {
            binding.imgSong.setImageResource(R.drawable.ic_play)
        }
        minutes = TimeUnit.MILLISECONDS.toMinutes(songDuration.toLong())
        second = (TimeUnit.MILLISECONDS.toSeconds(songDuration.toLong()) % 60)

        binding.txtTotalTime.text = ("$minutes : $second")

        playSong = MediaPlayer.create(this, songPath?.toUri())
        binding.imgPlayPause.setColorFilter(resources.getColor(com.google.android.material.R.color.material_dynamic_neutral20))
        binding.imgPlayPause.setBackgroundResource(R.drawable.ic_pause)

        playMusic()

        val bundle = intent.getBundleExtra("my_bundle")
        val myValue = bundle?.getInt("BG_IMAGE")
        if (myValue != null) {
            binding.clMain.setBackgroundResource(myValue.toInt())

        }

        //Button Setup
        binding.imgSelect.setOnClickListener {
            val intent = Intent(this, BackgroundSelectActivity::class.java)
            startActivity(intent)
        }
        val position: Int = MyPreferences.getIntegerPref(this, "SONG_POSITION")
        currentIndex = position
        Log.e("TAG","PoSITOION : -> ${position}  currentIndex : -> ${currentIndex}--> ")
        binding.imgNext.setOnClickListener {
            currentIndex++
            if (currentIndex >= songsList.size) {
                currentIndex = 0
            }
            playSong.stop()
            playSong.reset()
            playSong.setDataSource(songsList[currentIndex])
            playSong.prepare()
            playSong.start()

            binding.txtName.text = songName
            binding.txtArtistName.text = songArtist
        }

        binding.imgPrevious.setOnClickListener {
            playSong.stop()
            currentIndex--
            if (currentIndex < 0) {
                currentIndex = songsList.size - 1
            }
            playSong.reset()
            playSong.setDataSource(songsList[currentIndex])
            playSong.prepare()
            playSong.start()

            binding.txtName.text = songName
            binding.txtArtistName.text = songArtist
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, DeviceMusic::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        playSong.release()
        super.onDestroy()
    }

    private fun playMusic() {
        playSong.start()
        binding.seekbar.progress = 0
        binding.seekbar.max = songDuration
        binding.imgPlayPause.setOnClickListener {
            if (isPlaying) {
                isPlaying = false
                binding.imgPlayPause.setBackgroundResource(R.drawable.ic_play)
                playSong.pause()
            } else {
                if (!playSong.isPlaying) {
                    playSong.start()
                }
                isPlaying = true
                binding.imgPlayPause.setBackgroundResource(R.drawable.ic_pause)
            }
        }
        binding.seekbar.max = songDuration
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    playSong.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }
        })

        runnable = Runnable {
            val rMinutes = TimeUnit.MILLISECONDS.toMinutes(playSong.currentPosition.toLong())
            val rSeconds = (TimeUnit.MILLISECONDS.toSeconds(playSong.currentPosition.toLong()) % 60)

            binding.txtRunningTime.text = ("$rMinutes:$rSeconds")
            binding.seekbar.progress = playSong.currentPosition
            handler.postDelayed(runnable, 0)
        }
        handler.postDelayed(runnable, 0)

        playSong.setOnCompletionListener {
            binding.seekbar.progress = 0
        }
    }


}
