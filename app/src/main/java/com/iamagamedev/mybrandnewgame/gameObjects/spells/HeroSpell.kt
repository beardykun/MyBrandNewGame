package com.iamagamedev.mybrandnewgame.gameObjects.spells

import com.iamagamedev.mybrandnewgame.Constants.SpellNames

class HeroSpell() : SpellObject() {

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

    companion object {

        private var heroSpell: HeroSpell? = null

        fun getInstance(startWorldX: Float, startWorldY: Float, type: Char, pixelsPerMetre: Int): HeroSpell {
            if (heroSpell == null) {
                heroSpell = HeroSpell(startWorldX, startWorldY, type, pixelsPerMetre)
            }
            return heroSpell!!
        }
    }

    private constructor(startWorldX: Float, startWorldY: Float, type: Char, pixelsPerMetre: Int) : this() {
        width = 1f
        height = 1f
        damage = 150f
        isVisible = true
        isMoves = true
        isActive = true

        setType(type)
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

    override fun setSpellType(spellType: String?, spellObject: SpellObject) {
        super.setSpellType(spellType, heroSpell)
    }
}