package com.iamagamedev.mybrandnewgame.gameObjects.spells

import com.iamagamedev.mybrandnewgame.Constants.SpellNames

class SpellUtils {

    companion object {
        fun setFireball(spellObject: SpellObject) {
            spellObject.width = 1f
            spellObject.height = 1f
            spellObject.damage = 150f
            spellObject.speed = 10f

            spellObject.bitmapName = SpellNames.FIREBALL
            spellObject.badBitmapName = SpellNames.FIREBALL_BAD
            val animationFrameCount = 4

            spellObject.waitingTime = 3000
            spellObject.setAnimFrameCount(animationFrameCount)
        }
    }
}