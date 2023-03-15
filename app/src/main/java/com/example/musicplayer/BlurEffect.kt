package com.example.musicplayer

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

/**
 * Created by Tirth Patel.
 */

fun applyBlueGradientBackground(layout: ConstraintLayout) {
    val drawable = ContextCompat.getDrawable(layout.context, R.drawable.blue_gradient)
    drawable?.let {
        it.colorFilter = PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY)
        layout.background = it
    }
}

fun applyBlurBackground(layout: ConstraintLayout, bitmap: Bitmap) {
    // create a RenderScript context
    val rsContext = RenderScript.create(layout.context)

    // create a blurred bitmap using RenderScript
    val blurredBitmap = bitmap.copy(bitmap.config, true)
    val input = Allocation.createFromBitmap(rsContext, blurredBitmap)
    val output = Allocation.createTyped(rsContext, input.type)
    val script = ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext))
    script.setRadius(20f)
    script.setInput(input)
    script.forEach(output)
    output.copyTo(blurredBitmap)

    // create a drawable from the blurred bitmap and set it as the background of the layout
    val drawable = BitmapDrawable(layout.resources, blurredBitmap)
    layout.background = drawable

    // destroy the RenderScript context to free up memory
    rsContext.finish()
    rsContext.destroy()
}

