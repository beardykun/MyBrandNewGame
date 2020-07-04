package com.iamagamedev.mybrandnewgame.collisions

import android.os.Handler
import android.os.Looper
import com.iamagamedev.mybrandnewgame.constants.CharConstants
import com.iamagamedev.mybrandnewgame.LevelManager
import com.iamagamedev.mybrandnewgame.SoundManager
import com.iamagamedev.mybrandnewgame.gameObjects.EnemyObject
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject

object SpellCollisions {
    private val handler = Handler(Looper.getMainLooper())
    fun checkForSpellCollisions(go: GameObject) {
        if (LevelManager.spellObject == null || go === LevelManager.spellObject || !LevelManager.spellObject!!.isActive) return
        var col = 0
        col = LevelManager.spellObject!!.checkCollisions(go.rectHitBoxRight, go.rectHitBoxLeft,
                go.rectHitBoxTop, go.rectHitBoxBottom)
        if (col > 0) {
            when (go.type) {
                CharConstants.ENEMY, CharConstants.ENEMY_TANKU -> {
                    spellIsUsed()
                    if (!(go as EnemyObject).shieldIsActive) {
                        go.health = go.health - LevelManager.spellObject!!.damage
                        if (go.health <= 0) {
                            go.isActive = false
                            go.isDead = true
                        }
                    }
                }
                CharConstants.WALL -> {
                    go.isActive = false
                    spellIsUsed()
                }
                CharConstants.ENEMY_SHIELD -> {
                    spellIsUsed()
                }
                else -> {
                }
            }
        }
    }

    private fun spellIsUsed() {
        if (!LevelManager.spellObject!!.isActive) return
        SoundManager.instance?.playSound("explode")
        LevelManager.spellObject?.isActive = false
        LevelManager.spellObject?.isMoves = false
        handler.postDelayed({
            LevelManager.spellObject?.updatePosition((-100).toFloat(), (-100).toFloat())
            LevelManager.spellObject?.isVisible = false
        }, 200)
    }
}