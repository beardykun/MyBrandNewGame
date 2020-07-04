package com.iamagamedev.mybrandnewgame.gameObjects

import android.content.Context
import com.iamagamedev.mybrandnewgame.constants.CharConstants
import com.iamagamedev.mybrandnewgame.constants.ObjectNames
import com.iamagamedev.mybrandnewgame.constants.SpellNames
import com.iamagamedev.mybrandnewgame.LevelManager
import com.iamagamedev.mybrandnewgame.Utils.fireSpell
import com.iamagamedev.mybrandnewgame.gameObjects.spells.HeroSpell
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject
import java.util.*

/**
 * Created by Михан on 17.05.2017.
 */
class Hero(context: Context?, worldStartX: Float, worldStartY: Float,
           private val pixelPerMetre: Int) : GameObject() {
    private val spellObjects: ArrayList<String> = ArrayList()
    private val MAX_VELOCITY = 2f
    private var pressingRight = false
    private var pressingLeft = false
    private var pressingUp = false
    private var pressingDown = false
    override fun update(fps: Long) {
        when {
            pressingRight -> {
                setxVelocity(MAX_VELOCITY)
            }
            pressingLeft -> {
                setxVelocity(-MAX_VELOCITY)
            }
            else -> {
                setxVelocity(0f)
            }
        }
        when {
            pressingUp -> {
                setyVelocity(-MAX_VELOCITY)
            }
            pressingDown -> {
                setyVelocity(MAX_VELOCITY)
            }
            else -> {
                setyVelocity(0f)
            }
        }
        when {
            getxVelocity() > 0 -> {
                facing = RIGHT
            }
            getxVelocity() < 0 -> {
                facing = LEFT
            }
            getyVelocity() > 0 -> {
                facing = DOWN
            }
            getyVelocity() < 0 -> {
                facing = UP
            }
        }
        move(fps)
        setRectHitBox()
        //updateShieldLocation();
    }

    fun setPressingDown(pressingDown: Boolean) {
        this.pressingDown = pressingDown
    }

    fun setPressingUp(pressingUp: Boolean) {
        this.pressingUp = pressingUp
    }

    fun setPressingLeft(pressingLeft: Boolean) {
        this.pressingLeft = pressingLeft
    }

    fun setPressingRight(pressingRight: Boolean) {
        this.pressingRight = pressingRight
    }

    fun fireSpell() {
        if (LevelManager.spellObject === null) {
            LevelManager.spellObject = HeroSpell(this.worldLocation!!.x, this.worldLocation!!.y, CharConstants.SPELL, pixelPerMetre)
        }
        LevelManager.spellObject?.let { fireSpell(it, this@Hero, SpellNames.FIREBALL) }
    }

    private fun updateShieldLocation() {
        LevelManager.shieldObject!!.setWorldLocation(worldLocation!!.x - 0.5f, worldLocation!!.y - 0.5f, 0)
    }

    init {
        spellObjects.add(SpellNames.FIREBALL)
        val HEALTH = 50
        val HEIGHT = 1f
        val WIDTH = 1f
        height = HEIGHT
        width = WIDTH
        health = HEALTH
        setxVelocity(0f)
        setyVelocity(0f)
        facing = LEFT
        isMoves = true
        isActive = true
        isVisible = true
        type = CharConstants.PLAYER
        bitmapName = ObjectNames.MAGE
        badBitmapName = ObjectNames.MAGE_DEAD
        val ANIMATION_FPS = 16
        val ANIMATION_FRAME_COUNT = 4
        setAnimFps(ANIMATION_FPS)
        setAnimFrameCount(ANIMATION_FRAME_COUNT)
        setAnimated(pixelPerMetre, true)
        setWorldLocation(worldStartX, worldStartY, 1)
    }
}