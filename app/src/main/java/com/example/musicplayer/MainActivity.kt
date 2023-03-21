package com.example.musicplayer


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.data.ItemsViewModel
import com.example.musicplayer.adapter.MusicAdapter
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.`interface`.MusicCallBack
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit


class MainActivity : MusicCallBack, AppCompatActivity() {
    lateinit var runnable: Runnable
    private var handler = Handler()
    private lateinit var binding: ActivityMainBinding
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var albumUri: Uri
    var duration: Int = 0
    var isPlaying = false
    lateinit var playSong: MediaPlayer
    private val READ_REQUEST_CODE = 101



    private val mediaMetadataRetriever = MediaMetadataRetriever()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       /* askForPermission()
        init()
        playMusic(1)
        getRawFilePath(1)*/
    }

/*
    private fun init() {

        binding.floatingActionButton.setOnClickListener {
            intent = Intent(this,DeviceMusic::class.java)
            startActivity(intent)
        }


        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvSongList.layoutManager = llm

        val data = ArrayList<ItemsViewModel>()


        data.add(ItemsViewModel(R.raw.music))
        data.add(ItemsViewModel(R.raw.music1))
        data.add(ItemsViewModel(R.raw.music2))
        data.add(ItemsViewModel(R.raw.music3))
        data.add(ItemsViewModel(R.raw.music4))
        data.add(ItemsViewModel(R.raw.music5))

        musicAdapter = MusicAdapter(data, this)
        binding.rvSongList.adapter = musicAdapter
        musicAdapter.notifyDataSetChanged()

        playSong = MediaPlayer.create(this, R.raw.music)
        duration = playSong.duration
        binding.imgPlayPause.setBackgroundResource(R.drawable.ic_play)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration.toLong())
        val seconds = (TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) % 60)

        binding.txtTotalTime.text = ("$minutes:$seconds")


        albumUri = Uri.parse("android.resource://$packageName/raw/music")

//        val uri = Uri.fromFile(mp3File) as Uri
        mediaMetadataRetriever.setDataSource(this@MainActivity, albumUri)
        val title =
            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        val artists =
            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)


        */
/* val mmr = MediaMetadataRetriever()
         mmr.setDataSource(albumUri.toString())
         val data = mmr.embeddedPicture
         val bitmap = BitmapFactory.decodeByteArray(data, 0, data!!.size)
         binding.imgMusic.setImageBitmap(bitmap)*//*

    }
*/

/*
    private fun askForPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.READ_EXTERNAL_STORAGE),READ_REQUEST_CODE)
        }
    }
*/

/*
    fun playMusic(possion: Int) {

        binding.seekbar.progress = 0
        binding.seekbar.max = duration
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
        binding.seekbar.max = duration
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
//            binding.imgPlayPause.setImageResource(R.drawable.ic_play)
            binding.seekbar.progress = 0
        }
    }
*/

/*
    fun getRawFilePath(possision:Int) {
        val `is`: InputStream = this.resources.openRawResource(R.raw.music5)
        val br = BufferedReader(InputStreamReader(`is`))
        var readLine: String? = null

        try {
            // While the BufferedReader readLine is not null
            while (br.readLine().also { readLine = it } != null) {
                readLine?.let { Log.d("TEXT", it) }
            }

            // Close the InputStream and BufferedReader
            `is`.close()
            br.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
*/


    override fun onClickHandel(position: Int) {
  /*      if (position == 0) {
            albumUri = Uri.parse("android.resource://$packageName/raw/music")
        } else {
            albumUri = Uri.parse("android.resource://$packageName/raw/music$position")
        }
        mediaMetadataRetriever.setDataSource(this@MainActivity, albumUri)
        val title =
            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        val artists =
            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)

        binding.txtMusicTitle.text = "$title \n $artists"
        binding.txtMusicTitle.text = "$title \n $artists"


        val intr = R.raw.music + position

        playSong = MediaPlayer.create(this, intr)
        duration = playSong.duration
        binding.imgPlayPause.setBackgroundResource(R.drawable.ic_play)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration.toLong())
        val seconds = (TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) % 60)

        binding.txtTotalTime.text = ("$minutes:$seconds")

        albumUri = Uri.parse("android.resource://$packageName/raw/music")

        playMusic(position)
        getRawFilePath(position)*/
    }
}