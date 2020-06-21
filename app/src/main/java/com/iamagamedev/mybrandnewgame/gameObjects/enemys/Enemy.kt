package com.iamagamedev.mybrandnewgame.gameObjects.enemys

import android.graphics.PointF
import android.os.Handler
import android.os.Looper
import com.iamagamedev.mybrandnewgame.Constants.ObjectNames
import com.iamagamedev.mybrandnewgame.Constants.SpellNames
import com.iamagamedev.mybrandnewgame.LevelManager
import com.iamagamedev.mybrandnewgame.LocationXYZ
import com.iamagamedev.mybrandnewgame.Utils
import com.iamagamedev.mybrandnewgame.gameObjects.EnemyObject
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject
import java.util.*

/**
 * Created by Михан on 17.05.2017.
 */
class Enemy(worldStartX: Float, worldStartY: Float,
            type: Char, private val pixelPerMetre: Int) : EnemyObject() {
    private var lastWayPointSetTime: Long = 0
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
        if (shieldIsActive)
            updateShieldLocation()
    }

    fun setWayPoint(x: Float, y: Float) {
        if (System.currentTimeMillis() > lastWayPointSetTime + 10000) {
            lastWayPointSetTime = System.currentTimeMillis()
            currentWaypoint.x = x
            currentWaypoint.y = y
        }
    }

    fun setWayPointHero(heroLocation: LocationXYZ) {
        if (System.currentTimeMillis() > lastWayPointSetTime + 2000) {
            lastWayPointSetTime = System.currentTimeMillis()
            currentWaypoint.x = heroLocation.x + 6
            currentWaypoint.y = heroLocation.y
        }
    }

    private fun fireSpell() {
        Utils.fireSpell(LevelManager.enemySpellObject!!, this, SpellNames.FIREBALL)
    }

    fun isUnderAttack(spellObject: SpellObject) {
        if (spellObject.isActive && spellObject.worldLocation!!.x - worldLocation!!.x < 3 && !shieldIsActive) {
            if (successfulDefenceOrAttack()) {
                LevelManager.enemyShieldObject?.also {
                    shieldIsActive = true
                    it.isActive = true
                    it.isVisible = true
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    LevelManager.enemyShieldObject?.also {
                        it.updatePosition((-100).toFloat(), (-100).toFloat())
                        shieldIsActive = false
                        it.isActive = false
                        it.isVisible = false
                    }
                }, 1000)
                updateShieldLocation()
            }
        }
    }

    private fun updateShieldLocation() {
        LevelManager.enemyShieldObject?.setWorldLocation(worldLocation!!.x - 0.5f, worldLocation!!.y - 0.5f, 0)
    }

    private val dialogsList: ArrayList<String>
        get() {
            return arrayListOf("Hello who are you?", "にゃんにゃんニャンコだぜ！",
                    "What the f*ck are you talking shithead?", "It is にゃんこ大戦争! 死ね！")
        }

    init {
        val HEIGHT = 1f
        val WIDTH = 1f
        val HEALTH = 150
        val DAMAGE = 15
        startLocationX = worldStartX.toInt()
        startLocationY = worldStartY.toInt()
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
        isCanTalk = true
        dialogs = dialogsList
        currentWaypoint = PointF()
        bitmapName = ObjectNames.ENEMY
        badBitmapName = ObjectNames.ENEMY_BAD
        setWorldLocation(worldStartX, worldStartY, 1)
        setRectHitBox()
    }
}