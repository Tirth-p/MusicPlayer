package com.example.musicplayer

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import androidx.core.net.toUri
import com.example.musicplayer.data.AudioFile
import com.example.musicplayer.databinding.ActivityMusicPlayerBinding
import com.example.musicplayer.utils.MusicPlayerService
import com.example.musicplayer.utils.musicNotification
import java.util.concurrent.TimeUnit


class MusicPlayerActivity : DeviceMusic() {
    lateinit var binding: ActivityMusicPlayerBinding
    private var isPlaying = false
    lateinit var playSong: MediaPlayer
    private var songDuration: Int = 0
    private var songName: String? = null
    private var songArtist: String? = null
    private var songPath: String? = null
    private var minutes: Long? = null
    private var second: Long? = null
    private lateinit var audioFiles: List<AudioFile>

    // Declare updateUiRunnable as a class-level property
    private lateinit var updateSeekBarRunnable: Runnable
    private val handler = Handler(Looper.getMainLooper())


    private val MAX_PROGRESS_DELAY: Long = 100 // Delay in milliseconds

    private val handlerSeekbar = Handler()
    private val updateProgressRunnable: Runnable = object : Runnable {
        override fun run() {
            val currentProgress: Int = binding.seekbar.progress
            val maxProgress: Int = binding.seekbar.max

            Log.e("TAG", "run() --> called maxProgress $currentProgress :: $maxProgress")
            if (currentProgress >= maxProgress) {
                Log.e("TAG", "run() --> called maxProgress")
                // Play the next music
                playNextSong()
            } else {
                // Continue updating the progress
                handlerSeekbar.postDelayed(this, MAX_PROGRESS_DELAY)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekbar.max = songDuration
        startUpdatingProgress()

        initView()
        musicNotification(
            this, songName, songArtist, songPath, minutes, second, playSong, handler, binding.clMain
        )

    }

    private fun startUpdatingProgress() {
        handlerSeekbar.postDelayed(updateProgressRunnable, MAX_PROGRESS_DELAY)
    }

    private fun stopUpdatingProgress() {
        handlerSeekbar.removeCallbacks(updateProgressRunnable)
    }

    private fun initView() {
        // Set Values with SharedPref
        songName = MyPreferences.getStringPref(this, "SONG_NAME")
        songArtist = MyPreferences.getStringPref(this, "SONG_ARTIST")
        songPath = MyPreferences.getStringPref(this, "SONG_PATH")
        songDuration = MyPreferences.getStringPref(this, "DURATION")!!.toInt()

        binding.txtName.isSelected = true
        binding.txtArtistName.isSelected = true
        binding.txtName.text = songName
        binding.txtArtistName.text = songArtist

        songPath?.let { setSongDetails(it, songDuration) }

        playSong = MediaPlayer.create(this, songPath?.toUri())
        binding.imgPlayPause.setColorFilter(resources.getColor(com.google.android.material.R.color.material_dynamic_neutral20))
        binding.imgPlayPause.setBackgroundResource(R.drawable.ic_pause)

        playMusic()

        val bundle = intent.getBundleExtra("my_bundle")
        val myValue = bundle?.getInt("BG_IMAGE")
        if (myValue != null) {
            binding.clMain.setBackgroundResource(myValue.toInt())

        }

        songsList.clear()
        audioFiles = getAudioFilesFromDevice()

        //Button Setup
        binding.imgSelect.setOnClickListener {
            val intent = Intent(this, BackgroundSelectActivity::class.java)
            startActivity(intent)
            finish()
        }
        val position: Int = MyPreferences.getIntegerPref(this, "SONG_POSITION")
        currentIndex = position
        binding.imgNext.setOnClickListener {
            playNextSong()
        }

        binding.imgPrevious.setOnClickListener {
            playPreviousSong()
        }
    }

    private fun playPreviousSong() {
        currentIndex--
        if (currentIndex < 0) {
            currentIndex = songsList.size - 1
        }

        // Stop the current song
        playSong.stop()
        playSong.reset()

        // Set the data source to the new song
        playSong.setDataSource(songsList[currentIndex])
        playSong.prepare()

        // Start playing the new song
        playSong.start()

        // Update the seek bar with the new song's duration and position
        binding.seekbar.max = playSong.duration
        binding.seekbar.progress = playSong.currentPosition

        //Set Previous Music Details

        val previousSongPath = audioFiles[currentIndex].path
        val previousDuration = audioFiles[currentIndex].duration

        binding.txtName.text = audioFiles[currentIndex].name
        binding.txtArtistName.text = audioFiles[currentIndex].artist
        setSongDetails(previousSongPath, previousDuration.toInt())
    }

    private fun setSeekBarMaxValue() {
        binding.seekbar.max = songDuration
    }

    private fun playNextSong() {
        currentIndex++
        if (currentIndex >= songsList.size) {
            currentIndex = 0
        }

        // Stop the current song
        playSong.stop()
        playSong.reset()

        // Set the data source to the new song
        playSong.setDataSource(songsList[currentIndex])
        playSong.prepare()

        // Start playing the new song
        playSong.start()

        // Seek to the desired position (e.g., 0 milliseconds)
        playSong.seekTo(0)

        // Reset SeekBar progress and max value
        binding.seekbar.progress = 0
        setSeekBarMaxValue()

        // Update the seek bar with the new song's duration and position
        binding.seekbar.max = playSong.duration
        binding.seekbar.progress = playSong.currentPosition

        //Set Next Music Details

        val nextSongPath = audioFiles[currentIndex].path
        val nextDuration = audioFiles[currentIndex].duration

        binding.txtName.text = audioFiles[currentIndex].name
        binding.txtArtistName.text = audioFiles[currentIndex].artist
        setSongDetails(nextSongPath, nextDuration.toInt())
    }

    private fun setSongDetails(setSongPath: String, setSongDuration: Int) {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(setSongPath)
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
        minutes = TimeUnit.MILLISECONDS.toMinutes(setSongDuration.toLong())
        second = (TimeUnit.MILLISECONDS.toSeconds(setSongDuration.toLong()) % 60)

        binding.txtTotalTime.text = String.format("%02d:%02d", minutes, second)
    }

    override fun onBackPressed() {
        handler.removeCallbacks(updateProgressRunnable)
       /* val intent = Intent(this, DeviceMusic::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)*/
        finish()
        super.onBackPressed()
    }

    override fun onDestroy() {
        playSong.release()
        // Remove the callbacks when the activity is destroyed
        handler.removeCallbacksAndMessages(null)

        stopUpdatingProgress()
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
                // Handle release event
                /* if (seekBar.progress >= seekBar.max)
                     playNextSong()*/
                val currentProgress = seekBar.progress
                val maxProgress = seekBar.max

                if (currentProgress >= maxProgress) {
                    // Play the next song
                    playNextSong()
                } else {
                    // Handle other stop tracking touch events
                    // For example, update the playback position of the current song
                    playSong.seekTo(currentProgress)
                }
            }
        })
        // Define a Runnable to update the UI
        val updateUiRunnable = Runnable { updateRunningTimeAndSeekBar() }

        // Start the UI update process
        handler.post(updateUiRunnable)

        playSong.setOnCompletionListener {
            binding.seekbar.progress = 0
        }
    }

    // Define a function to update the running time and seek bar
    private fun updateRunningTimeAndSeekBar() {
        val currentPosition = playSong.currentPosition

        val rMinutes = TimeUnit.MILLISECONDS.toMinutes(currentPosition.toLong())
        val rSeconds = TimeUnit.MILLISECONDS.toSeconds(currentPosition.toLong()) % 60

        binding.txtRunningTime.text = String.format("%02d:%02d", rMinutes, rSeconds)
        binding.seekbar.progress = currentPosition

        // Seek completion listener to handle seek completion
        val seekCompletionListener = MediaPlayer.OnSeekCompleteListener {
            updateRunningTimeAndSeekBar()
        }

        // Add the seek completion listener before seeking to the desired position
        playSong.setOnSeekCompleteListener(seekCompletionListener)

        // Schedule the next update after a certain delay
        handler.postDelayed(::updateRunningTimeAndSeekBar, 1000) // Update every second
    }
}
