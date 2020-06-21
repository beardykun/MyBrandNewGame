package com.iamagamedev.mybrandnewgame.gameObjects.spells

import com.iamagamedev.mybrandnewgame.Constants.SpellNames
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject

class EnemyShieldObject(startWorldX: Float, startWorldY: Float, type: Char, pixelsPerMetre: Int) : GameObject() {

    init {
        width = 2f
        height = 2f
        damage = 0
        isVisible = true
        isMoves = false
        isActive = true
        health = 100000

        this.type = type
        bitmapName = SpellNames.SHIELD
        badBitmapName = SpellNames.SHIELD
        setWorldLocation(startWorldX, startWorldY, 0)
        val ANIMATION_FPS = 16
        val ANIMATION_FRAME_COUNT = 4

        setAnimFps(ANIMATION_FPS)
        setAnimFrameCount(ANIMATION_FRAME_COUNT)
        setAnimated(pixelsPerMetre, true)
        setRectHitBox()
    }

    override fun update(fps: Long) {
        setRectHitBox()
    }
}
