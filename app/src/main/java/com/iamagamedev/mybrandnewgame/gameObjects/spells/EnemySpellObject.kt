package com.iamagamedev.mybrandnewgame.gameObjects.spells

import com.iamagamedev.mybrandnewgame.Constants.SpellNames

class EnemySpellObject private constructor(startWorldX: Float, startWorldY: Float, type: Char, pixelsPerMetre: Int) : SpellObject() {

    init {
        width = 1f
        height = 1f
        damage = 150f
        isVisible = true
        isMoves = true
        isActive = true

        setType(type)
        bitmapName = SpellNames.FIREBALL
        badBitmapName = SpellNames.FIREBALL_BAD
        setWorldLocation(startWorldX, startWorldY, 0)
        val ANIMATION_FPS = 16
        val ANIMATION_FRAME_COUNT = 4

        setAnimFps(ANIMATION_FPS)
        setAnimFrameCount(ANIMATION_FRAME_COUNT)
        setAnimated(pixelsPerMetre, true)
        setRectHitBox()
    }

    override fun update(fps: Long) {
        when (this.facing) {
            LEFT -> {
                setxVelocity(-getSpeed())
                setyVelocity(0f)
            }
            RIGHT -> {
                setxVelocity(getSpeed())
                setyVelocity(0f)
            }
            UP -> {
                setyVelocity(-getSpeed())
                setxVelocity(0f)
            }
            DOWN -> {
                setyVelocity(getSpeed())
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

    override fun updatePosition(x: Float, y: Float) {
        var x = x
        var y = y
        when (this.facing) {
            LEFT -> x--
            RIGHT -> x++
            UP -> y--
            DOWN -> y++
        }
        this.setWorldLocation(x, y, worldLocation.z)
    }

    override fun setSpellType(spellType: String, spellObject: SpellObject) {
        super.setSpellType(spellType, this)
    }

    companion object {

        private var spellObject: EnemySpellObject? = null

        fun getInstance(x: Float, y: Float, c: Char, pix: Int): EnemySpellObject {
            if (spellObject == null) {
                spellObject = EnemySpellObject(x, y, c, pix)
            }
            return spellObject!!
        }
    }
}
