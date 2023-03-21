package com.example.musicplayer

import android.graphics.*
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

fun giveDarkFadelEffect(originalBitmap : Bitmap): Bitmap{

// Create a new Bitmap with the same dimensions as the original Bitmap
    val bitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, Bitmap.Config.ARGB_8888)

// Create a Canvas and draw the original Bitmap onto it
    val canvas = Canvas(bitmap)
    canvas.drawBitmap(originalBitmap, 0f, 0f, null)

// Create a Paint object with a black color and an alpha of 80 (out of 255)
    val paint = Paint()
    paint.color = Color.argb(150, 0, 0, 0)

// Draw a rectangle over the entire Bitmap using the Paint object
    canvas.drawRect(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat(), paint)

return bitmap

}