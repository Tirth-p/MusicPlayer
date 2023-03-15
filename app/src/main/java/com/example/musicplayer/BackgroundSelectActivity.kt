package com.example.musicplayer

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayer.adapter.BackgroundImageSelectAdapter
import com.example.musicplayer.data.LocalImageData
import com.example.musicplayer.databinding.ActivityBackgroundSelectBinding

class BackgroundSelectActivity : AppCompatActivity() {
    lateinit var binding: ActivityBackgroundSelectBinding
    lateinit var imageAdapter: BackgroundImageSelectAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBackgroundSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init() {


/*        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvImageSelect.layoutManager = llm*/

        val imageDataList = ArrayList<LocalImageData>()

        imageDataList.add(LocalImageData(R.drawable.music_img2))
        imageDataList.add(LocalImageData(R.drawable.music_img3))
        imageDataList.add(LocalImageData(R.drawable.music_img4))
        imageDataList.add(LocalImageData(R.drawable.music_img5))
        imageDataList.add(LocalImageData(R.drawable.music_img6))
        imageDataList.add(LocalImageData(R.drawable.music_img7))
        imageDataList.add(LocalImageData(R.drawable.music_img8))
        imageDataList.add(LocalImageData(R.drawable.music_img9))
        imageDataList.add(LocalImageData(R.drawable.music_img10))
        imageDataList.add(LocalImageData(R.drawable.music_img11))
        imageDataList.add(LocalImageData(R.drawable.music_img12))
        imageDataList.add(LocalImageData(R.drawable.music_img13))
        imageDataList.add(LocalImageData(R.drawable.music_img14))
        imageDataList.add(LocalImageData(R.drawable.music_img15))
        imageDataList.add(LocalImageData(R.drawable.music_img16))
        imageDataList.add(LocalImageData(R.drawable.music_img17))
        imageDataList.add(LocalImageData(R.drawable.music_img18))
        imageDataList.add(LocalImageData(R.drawable.music_img19))
        imageDataList.add(
            LocalImageData(R.drawable.music_img20)
        )

        val courseAdapter = BackgroundImageSelectAdapter(imageDataList, this)


        // on below line we are setting adapter to our grid view.
        binding.idGRV.adapter = courseAdapter

        // on below line we are adding on item
        // click listener for our grid view.
        binding.idGRV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // inside on click method we are simply displaying
            // a toast message with course name.


            val bundle = Bundle()
            bundle.putInt("BG_IMAGE", imageDataList[position].imageResource)

            val intent = Intent(this, MusicPlayerActivity::class.java)
            intent.putExtra("my_bundle", bundle)
            startActivity(intent)
            finish()
        }

            /*   val recyclerView = findViewById<RecyclerView>(R.id.rv_image_select)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager*/
            /*    val adapter = BackgroundImageSelectAdapter(imageDataList, this)
        recyclerView.adapter = adapter*/

            /*    imageAdapter = BackgroundImageSelectAdapter(imageDataList, this)
        binding.rvImageSelect.adapter = imageAdapter
        imageAdapter.notifyDataSetChanged()*/



/*    override fun onImageClick(localImageData: Int) {

        val bundle = Bundle()
        bundle.putInt("BG_IMAGE", localImageData)

        val intent = Intent(this, MusicPlayerActivity::class.java)
        intent.putExtra("my_bundle", bundle)
        startActivity(intent)

        MyPreferences.setImagePref(this, "BG_IMAGE", localImageData)

    }*/
    }
}