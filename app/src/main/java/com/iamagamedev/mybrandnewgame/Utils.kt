package com.iamagamedev.mybrandnewgame

import android.os.Handler
import android.os.Looper
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject

object Utils {
    private val handler = Handler(Looper.getMainLooper())
    @JvmStatic
    fun fireSpell(spellObject: SpellObject, gameObject: GameObject, spellType: String?) {
        SoundManager.instance?.playPTCD()
        gameObject.isMoves = false
        spellObject.isActive = false
        spellObject.isMoves = false
        spellObject.isVisible = false
        spellObject.facing = gameObject.facing
        spellObject.setSpellType(spellType, spellObject)
        handler.postDelayed({
            gameObject.isMoves = true
            spellObject.updatePosition(gameObject.worldLocation!!.x, gameObject.worldLocation!!.y)
            spellObject.setAnimated(LevelManager.pixelsPerMetre, true)
            spellObject.isVisible = true
            spellObject.isActive = true
            spellObject.isMoves = true
        }, spellObject.waitingTime.toLong())
    }
}