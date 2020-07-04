package com.iamagamedev.mybrandnewgame.collisions

import com.iamagamedev.mybrandnewgame.LevelManager
import com.iamagamedev.mybrandnewgame.constants.CharConstants
import com.iamagamedev.mybrandnewgame.gameObjects.EnemyObject
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject

object EnemyCollisions {
    fun checkForEnemyCollisions(go: GameObject, lm: LevelManager) {
        for (i in lm.enemiesList.indices) {
            val enemy = lm.gameObjects[lm.enemiesList[i]] as EnemyObject
            if (go === enemy || !enemy.isActive) return
            var col = 0

            col = enemy.checkCollisions(go.rectHitBoxRight, go.rectHitBoxLeft,
                    go.rectHitBoxTop, go.rectHitBoxBottom)
            if (col > 0) {
                when (go.type) {
                    CharConstants.SHIELD, CharConstants.WALL, CharConstants.ENEMY, CharConstants.ENEMY_TANKU -> {
                    }
                    CharConstants.SPELL -> {
                    }
                    else -> {
                    }
                }
            }
        }
    }
}