package com.iamagamedev.mybrandnewgame.gameObjects.enemys

import android.graphics.PointF
import com.iamagamedev.mybrandnewgame.Constants.ObjectNames
import com.iamagamedev.mybrandnewgame.Constants.SpellNames
import com.iamagamedev.mybrandnewgame.LevelManager
import com.iamagamedev.mybrandnewgame.LocationXYZ
import com.iamagamedev.mybrandnewgame.Utils.fireSpell
import com.iamagamedev.mybrandnewgame.gameObjects.EnemyObject
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject

class TankuNeko(worldStartX: Float, worldStartY: Float,
                type: Char, pixelPerMetre: Int) : EnemyObject() {
    private var lastWaypointSetTime: Long = 0
    private val currentWaypoint: PointF
    private val MAX_X_VELOCITY = 1.8f
    private val MAX_Y_VELOCITY = 1.8f
    override fun update(fps: Long) {
        if (!this.isDead) {
            when {
                currentWaypoint.x >= worldLocation!!.x -> {
                    setxVelocity(MAX_X_VELOCITY)
                }
                currentWaypoint.x <= worldLocation!!.x -> {
                    setxVelocity(-MAX_X_VELOCITY)
                }
                else -> {
                    setxVelocity(0f)
                }
            }
            when {
                currentWaypoint.y >= worldLocation!!.y -> {
                    setyVelocity(MAX_Y_VELOCITY)
                }
                currentWaypoint.y <= worldLocation!!.y -> {
                    setyVelocity(-MAX_Y_VELOCITY)
                }
                else -> {
                    setyVelocity(0f)
                }
            }
        } else {
            setyVelocity(-2f)
        }
        move(fps)
        setRectHitBox()
        //fireSpell()
    }

    fun setWaypoint(heroLocation: LocationXYZ) {
        if (System.currentTimeMillis() > lastWaypointSetTime + 2000) {
            lastWaypointSetTime = System.currentTimeMillis()
            currentWaypoint.x = heroLocation.x + 5
            currentWaypoint.y = heroLocation.y
        }
    }

    private fun fireSpell() {
        fireSpell(LevelManager.enemySpellObject!!, this, SpellNames.FIREBALL)
    }

    fun isUnderAttack(spellObject: SpellObject) {
        if (spellObject.worldLocation!!.x - worldLocation!!.x < 3) {
            if (successfulDefenceOrAttack()) {
            }
        }
    }

    init {
        var type = type
        val HEIGHT = 1f
        val WIDTH = 1f
        val HEALTH = 150
        val DAMAGE = 15
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
        facing = LEFT
        currentWaypoint = PointF()
        bitmapName = "tanku_neko"
        badBitmapName = ObjectNames.ENEMY_BAD
        setWorldLocation(worldStartX, worldStartY, 1)
        setRectHitBox()
    }
}