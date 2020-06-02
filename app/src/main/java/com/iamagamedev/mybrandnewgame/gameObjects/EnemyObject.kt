package com.iamagamedev.mybrandnewgame.gameObjects

import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject
import kotlin.random.Random

abstract class EnemyObject : GameObject() {

    var spell: SpellObject? = null
    var enemySkill: Int = 0
    var enemyTired: Int = 0
    var enemyFear: Int = 0
    var startLocationX: Int = 0
    var startLocationY: Int = 0

    fun successfulDefenceOrAttack(): Boolean {
        val random = Random.nextInt(enemySkill, 100)
        return random > Random.nextInt(100)
    }

    fun attackEnemy(spellType: Char) {

    }
}