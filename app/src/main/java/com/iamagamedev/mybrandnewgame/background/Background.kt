package com.iamagamedev.mybrandnewgame.background

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix

/**
 * Created by Михан on 31.05.2017.
 */
class Background(context: Context, yPixelsPerMetre: Int,
                 screenWidth: Int, data: BackgroundData) {
    var bitmap: Bitmap
    var bitmapReversed: Bitmap
    var width: Int
    var height: Int
    var reversedFirst: Boolean
    var xClip: Int
    var y: Float
    var endY: Float
    var z: Int
    var speed: Float
    var isParallax: Boolean

    init {
        val resID = context.resources.getIdentifier(data.bitmapName,
                "drawable", context.packageName)
        bitmap = BitmapFactory.decodeResource(context.resources, resID)
        reversedFirst = false
        xClip = 0
        y = data.startY
        endY = data.endY
        z = data.layer
        isParallax = data.isParallax
        speed = data.speed
        bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth,
                data.height * yPixelsPerMetre, true)
        width = bitmap.width
        height = bitmap.height
        val matrix = Matrix()
        matrix.setScale(-1f, 1f)
        bitmapReversed = Bitmap.createBitmap(bitmap, 0, 0,
                width, height, matrix, true)
    }
}