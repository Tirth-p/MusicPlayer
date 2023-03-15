package com.example.musicplayer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.musicplayer.R
import com.example.musicplayer.data.LocalImageData

/**
 * Created by Tirth Patel.
 */

class BackgroundImageSelectAdapter(
    private val imageDataList: List<LocalImageData>,
    val mContext: Context
) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView

    override fun getCount(): Int {
        return imageDataList.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView

        if (layoutInflater == null) {
            layoutInflater =
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        }

        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.rv_background_image, null)
        }

        imageView = convertView!!.findViewById(R.id.idIVCourse)
        imageView.setImageResource(imageDataList[position].imageResource)

        return convertView
    }
}


