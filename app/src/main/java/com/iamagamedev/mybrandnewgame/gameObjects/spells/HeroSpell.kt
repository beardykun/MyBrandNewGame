package com.iamagamedev.mybrandnewgame.gameObjects.spells

import com.iamagamedev.mybrandnewgame.Constants.SpellNames

class HeroSpell(startWorldX: Float, startWorldY: Float, type: Char, pixelsPerMetre: Int) : SpellObject() {

    override fun update(fps: Long) {
        when (this.facing) {
            LEFT -> {
                setxVelocity(-speed)
                setyVelocity(0f)
            }
            RIGHT -> {
                setxVelocity(speed)
                setyVelocity(0f)
            }
            UP -> {
                setyVelocity(-speed)
                setxVelocity(0f)
            }
            DOWN -> {
                setyVelocity(speed)
                setxVelocity(0f)
            }
            else -> {
                setxVelocity(0f)
                setyVelocity(0f)
            }
        }
        move(fps)
        setRectHitBox()
    }

    init {
        width = 1f
        height = 1f
        damage = 150
        isVisible = true
        isMoves = true
        isActive = true

        this.type = type
        bitmapName = SpellNames.FIREBALL
        badBitmapName = SpellNames.FIREBALL_BAD
        setWorldLocation(startWorldX, startWorldY, 1)
        val ANIMATION_FPS = 16
        val ANIMATION_FRAME_COUNT = 4

        setAnimFps(ANIMATION_FPS)
        setAnimFrameCount(ANIMATION_FRAME_COUNT)
        setAnimated(pixelsPerMetre, true)
        setRectHitBox()
    }
}