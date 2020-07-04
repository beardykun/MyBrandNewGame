package com.iamagamedev.mybrandnewgame.gameObjects

import android.graphics.PointF
import com.iamagamedev.mybrandnewgame.LocationXYZ
import kotlin.random.Random

abstract class EnemyObject : GameObject() {

    var waypointX2: Float? = null
    var waypointX1: Float? = null
    val currentWayPoint: PointF = PointF()
    var lastWayPointSetTime: Long = 0
    var enemySkill: Int = 0
    var enemyTired: Int = 0
    var enemyFear: Int = 0
    var startLocationX: Int = 0
    var startLocationY: Int = 0
    var shieldIsActive: Boolean = false
    var isDead: Boolean = false
    var headingWayPoint = 1
    var isAggressive = false

    fun successfulDefenceOrAttack(): Boolean {
        val random = Random.nextInt(enemySkill, 100)
        return random > Random.nextInt(100)
    }

    fun attackEnemy(spellType: Char) {

    }

    fun setWayPoint(heroLocation: LocationXYZ) {
        if (System.currentTimeMillis() > lastWayPointSetTime + 2000) {
            lastWayPointSetTime = System.currentTimeMillis()
            currentWayPoint.x = heroLocation.x + 5
            currentWayPoint.y = heroLocation.y
        }
    }

     fun setWaypoints(x1: Float, x2: Float) {
        waypointX1 = x1
        waypointX2 = x2
    }

    override fun update(fps: Long) {
        if (this.isDead) {
            setyVelocity(-2f)
        } else if (this.isAggressive) {
            when {
                currentWayPoint.x >= worldLocation!!.x -> {
                    setxVelocity(maxVelocity)
                }
                currentWayPoint.x <= worldLocation!!.x -> {
                    setxVelocity(-maxVelocity)
                }
                else -> {
                    setxVelocity(0f)
                }
            }
            when {
                currentWayPoint.y >= worldLocation!!.y -> {
                    setyVelocity(maxVelocity)
                }
                currentWayPoint.y <= worldLocation!!.y -> {
                    setyVelocity(-maxVelocity)
                }
                else -> {
                    setyVelocity(0f)
                }
            }
        } else if (!this.isAggressive) {
            lastWayPointSetTime = System.currentTimeMillis()
            if (headingWayPoint == 1) {// Heading left
                this.setxVelocity(-maxVelocity);
                if (this.worldLocation!!.x <= waypointX1!!) {
                    // Arrived at waypoint 1
                    headingWayPoint = 2
                    this.setxVelocity(maxVelocity);
                    facing = RIGHT
                }
            }
            if (headingWayPoint == 2) {
                if (this.worldLocation!!.x >= waypointX2!!) {
                    // Arrived at waypoint 2
                    headingWayPoint = 1
                    this.setxVelocity(-maxVelocity);
                    facing = LEFT
                }
            }
        }
        move(fps)
        setRectHitBox()
        //fireSpell()
    }

}