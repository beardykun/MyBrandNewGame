package com.iamagamedev.mybrandnewgame.gameObjects.spells

import com.iamagamedev.mybrandnewgame.gameObjects.GameObject

class ShieldSpellObject() : GameObject() {

    private constructor(startWorldX: Float, startWorldY: Float, type: Char, pixelsPerMetre: Int) : this() {
        width = 2f
        height = 2f
        damage = 0f
        isVisible = true
        isMoves = false
        isActive = true

        this.type = type
        bitmapName = "shield"
        badBitmapName = "shield"
        setWorldLocation(startWorldX, startWorldY, 0)
        val ANIMATION_FPS = 16
        val ANIMATION_FRAME_COUNT = 4

        setAnimFps(ANIMATION_FPS)
        setAnimFrameCount(ANIMATION_FRAME_COUNT)
        setAnimated(pixelsPerMetre, true)
        setRectHitBox()
    }

    override fun update(fps: Long) {
        move(fps)
        setRectHitBox()
    }

    companion object {
        private var shieldSpellObject: ShieldSpellObject? = null
        fun getInstance(startWorldX: Float, startWorldY: Float, type: Char,
                        pixelsPerMetre: Int): ShieldSpellObject? {

        if (shieldSpellObject == null) {
            shieldSpellObject = ShieldSpellObject(startWorldX, startWorldY, type, pixelsPerMetre)
        }
        return shieldSpellObject
    }
}
}