package com.iamagamedev.mybrandnewgame

import android.graphics.Bitmap
import android.graphics.Rect

/**
 * Created by Михан on 25.05.2017.
 */
class Animation(bitmapName: String, frameHeight: Float,
                frameWidth: Float, animFps: Int, private val frameCount: Int, pixelsPerMetre: Int) {
    var bitmapSheet: Bitmap? = null
    private val bitmapName: String
    private val soursRect: Rect
    private var currentFrame = 0
    private var frameTicker: Long
    private val framePeriod: Int
    private val frameWidth: Float = frameWidth * pixelsPerMetre
    private val frameHeight: Float = frameHeight * pixelsPerMetre
    private val pixelsPerMetre: Int
    fun getCurrentFrame(time: Long, xVelocity: Float,
                        yVelocity: Float, moves: Boolean): Rect {
        if (xVelocity != 0f || yVelocity != 0f || !moves) {
            if (time > frameTicker + framePeriod) {
                frameTicker = time
                currentFrame++
                if (currentFrame >= frameCount) {
                    currentFrame = 0
                }
            }
        }
        soursRect.left = currentFrame * frameWidth.toInt()
        soursRect.right = soursRect.left + frameWidth.toInt()
        return soursRect
    }

    init {
        soursRect = Rect(0, 0, this.frameWidth.toInt(), this.frameHeight.toInt())
        framePeriod = 1000 / animFps
        frameTicker = 0L
        this.bitmapName = "" + bitmapName
        this.pixelsPerMetre = pixelsPerMetre
    }
}