package com.iamagamedev.mybrandnewgame.gameObjects.spells

import com.iamagamedev.mybrandnewgame.constants.SpellNames
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellUtils.Companion.setFireball

abstract class SpellObject : GameObject() {
    var waitingTime = 3000
    private var x = 0f
    private var y = 0f

    override fun update(fps: Long) {
        when (facing) {
            LEFT -> x--
            RIGHT -> x++
            UP -> y--
            DOWN -> y++
        }
        super.move(fps)
    }

    open fun setSpellType(spellType: String?, spellObject: SpellObject?) {
        when (spellType) {
            SpellNames.FIREBALL -> setFireball(spellObject!!)
        }
    }
}