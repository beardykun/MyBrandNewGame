package com.iamagamedev.mybrandnewgame.gameObjects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.util.Log
import com.iamagamedev.mybrandnewgame.Animation
import com.iamagamedev.mybrandnewgame.LocationXYZ
import com.iamagamedev.mybrandnewgame.RectHitBox
import java.util.*

/**
 * Created by Михан on 24.05.2017.
 */
abstract class GameObject {
    @JvmField
    val LEFT = -1
    @JvmField
    val RIGHT = 1
    @JvmField
    val UP = -2
    @JvmField
    val DOWN = 2
    var worldLocation: LocationXYZ? = null
        private set
    val rectHitBoxBottom = RectHitBox()
    val rectHitBoxLeft = RectHitBox()
    val rectHitBoxTop = RectHitBox()
    val rectHitBoxRight = RectHitBox()
    private var animation: Animation? = null
    var isAnimated = false
        private set
    private var animFps = 1
    open var isMoves = false
    var facing = 0
    var width = 0f
    var height = 0f
    var health = 0
    var damage = 0
    private var xVelocity = 0f
    private var yVelocity = 0f
    var isActive = true
    var isVisible = true
    private var animFrameCount = 1
    var type = 0.toChar()
    var bitmapName: String? = null
    var badBitmapName: String? = null
    var speed = 0f
    var isTalking = false
    var dialogs: ArrayList<String>? = null
    var isCanTalk = false
    abstract fun update(fps: Long)
    fun prepareBitmap(context: Context,
                      bitmapName: String?, pixelsPerMetre: Int): Bitmap {
        val resID = context.resources
                .getIdentifier(bitmapName,
                        "drawable", context.packageName)
        var bitmap = BitmapFactory.decodeResource(context.resources, resID)
        bitmap = Bitmap.createScaledBitmap(bitmap, (width * animFrameCount * pixelsPerMetre).toInt(),
                (height * pixelsPerMetre).toInt(), false)
        return bitmap
    }

    fun setWorldLocation(x: Float, y: Float, z: Int) {
        worldLocation = LocationXYZ()
        worldLocation!!.x = x
        worldLocation!!.y = y
        worldLocation!!.z = z
    }

    open fun updatePosition(x: Float, y: Float) {
        setWorldLocation(x, y, worldLocation!!.z)
    }

    fun setRectHitBox() {
        val locationXYZ = worldLocation
        val goX = locationXYZ!!.x
        val goY = locationXYZ.y
        //update the obj feet hitbox
        rectHitBoxBottom.top = goY + height * .95f
        rectHitBoxBottom.left = goX + width * .1f
        rectHitBoxBottom.bottom = goY + height * .98f
        rectHitBoxBottom.right = goX + width * .9f
        // Update obj head hitbox
        rectHitBoxTop.top = goY
        rectHitBoxTop.left = goX + width * .1f
        rectHitBoxTop.bottom = goY + height * .2f
        rectHitBoxTop.right = goX + width * .9f
        // Update obj left hitbox
        rectHitBoxLeft.top = goY + height * .2f
        rectHitBoxLeft.left = goX + width * .2f
        rectHitBoxLeft.bottom = goY + height * .8f
        rectHitBoxLeft.right = goX + width * .3f
        // Update obj right hitbox
        rectHitBoxRight.top = goY + height * .2f
        rectHitBoxRight.left = goX + width * .71f
        rectHitBoxRight.bottom = goY + height * .8f
        rectHitBoxRight.right = goX + width * .8f
    }

    fun setAnimated(pixelsPerMetre: Int, animated: Boolean) {
        isAnimated = animated
        animation = Animation(bitmapName,
                height, width, animFps, animFrameCount, pixelsPerMetre)
    }

    fun getRectToDraw(deltaTime: Long): Rect {
        return animation!!.getCurrentFrame(deltaTime, xVelocity, yVelocity, isMoves)
    }

    fun move(fps: Long) {
        if (xVelocity != 0f) {
            worldLocation!!.x += xVelocity / fps
        }
        if (yVelocity != 0f) {
            worldLocation!!.y += yVelocity / fps
        }
    }

    fun checkCollisions(rectHitBoxRight: RectHitBox, rectHitBoxLeft: RectHitBox,
                        rectHitBoxTop: RectHitBox, rectHitBoxBottom: RectHitBox): Int {
        var collided = 0
        if (this.rectHitBoxLeft.intersects(rectHitBoxRight)) {
            setWorldLocationX(rectHitBoxRight.right)
            collided = 1
        }
        if (this.rectHitBoxRight.intersects(rectHitBoxLeft)) {
            setWorldLocationX(rectHitBoxLeft.left - width)
            collided = 1
        }
        if (this.rectHitBoxBottom.intersects(rectHitBoxTop)) {
            setWorldLocationY(rectHitBoxTop.top - height)
            collided = 2
        }
        if (this.rectHitBoxTop.intersects(rectHitBoxBottom)) {
            setWorldLocationY(rectHitBoxBottom.bottom)
            collided = 2
        }
        return collided
    }

    fun setWorldLocationY(y: Float) {
        worldLocation!!.y = y
    }

    fun setWorldLocationX(x: Float) {
        worldLocation!!.x = x
    }

    fun setAnimFps(animFps: Int) {
        this.animFps = animFps
    }

    fun setAnimFrameCount(animFrameCount: Int) {
        this.animFrameCount = animFrameCount
    }

    fun getxVelocity(): Float {
        return xVelocity
    }

    fun getyVelocity(): Float {
        return yVelocity
    }

    fun setxVelocity(xVelocity: Float) {
        if (isMoves) this.xVelocity = xVelocity
    }

    fun setyVelocity(yVelocity: Float) {
        if (isMoves) this.yVelocity = yVelocity
    }
}