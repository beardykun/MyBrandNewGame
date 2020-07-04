package com.iamagamedev.mybrandnewgame.gameObjects.enemys

import com.iamagamedev.mybrandnewgame.LevelManager
import com.iamagamedev.mybrandnewgame.Utils.fireSpell
import com.iamagamedev.mybrandnewgame.constants.ObjectNames
import com.iamagamedev.mybrandnewgame.constants.SpellNames
import com.iamagamedev.mybrandnewgame.gameObjects.EnemyObject
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject

class TankuNeko(worldStartX: Float, worldStartY: Float,
                type: Char, pixelPerMetre: Int) : EnemyObject() {
    val MAX_VELOCITY = 1.8f

    private fun fireSpell() {
        fireSpell(LevelManager.enemySpellObject!!, this, SpellNames.FIREBALL)
    }

    fun isUnderAttack(spellObject: SpellObject) {
        if (spellObject.worldLocation!!.x - worldLocation!!.x < 3) {
            if (successfulDefenceOrAttack()) {
            }
        }
    }

    override fun update(fps: Long) {
        super.update(fps)
    }

    init {
        val HEIGHT = 1f
        val WIDTH = 1f
        val HEALTH = 150
        val DAMAGE = 15
        maxVelocity = MAX_VELOCITY
        width = WIDTH
        height = HEIGHT
        health = HEALTH
        damage = DAMAGE
        enemySkill = 80
        enemyTired = 0
        enemyFear = 0
        this.type = type
        isMoves = true
        isActive = true
        isVisible = true
        isAggressive = false
        facing = LEFT
        bitmapName = ObjectNames.ENEMY_TANK
        badBitmapName = ObjectNames.ENEMY_BAD
        setWorldLocation(worldStartX, worldStartY, 1)
        setRectHitBox()
    }
}