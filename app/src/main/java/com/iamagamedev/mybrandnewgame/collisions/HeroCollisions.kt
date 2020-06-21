package com.iamagamedev.mybrandnewgame.collisions

import com.iamagamedev.mybrandnewgame.Constants.CharConstants
import com.iamagamedev.mybrandnewgame.GameView
import com.iamagamedev.mybrandnewgame.LevelManager
import com.iamagamedev.mybrandnewgame.SoundManager
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Home

object HeroCollisions {
    var collidedObject: GameObject? = null
        private set
    private var dialogLocationX = 0
    private var dialogLocationY = 0
    fun checkForCollisions(go: GameObject, lm: LevelManager, soundManager: SoundManager) {
        if (go.type != CharConstants.SHIELD) {
            val hit = lm.hero!!.checkCollisions(go.rectHitBoxRight, go.rectHitBoxLeft,
                    go.rectHitBoxTop, go.rectHitBoxBottom)
            if (hit > 0 && go.isCanTalk) {
                collidedObject = go
                GameView.showTalkButton = true
                dialogLocationX = lm.hero!!.worldLocation!!.x.toInt()
                dialogLocationY = lm.hero!!.worldLocation!!.y.toInt()
            }
            if (lm.hero!!.worldLocation!!.x.toInt() != dialogLocationX || lm.hero!!.worldLocation!!.y.toInt() != dialogLocationY) {
                collidedObject = null
                GameView.showTalkButton = false
            }
            if (hit > 0) {
                lm.hero!!.health = lm.hero!!.health - go.damage
                when (go.type) {
                    CharConstants.HOME -> {
                        val home = go as Home
                        val t = home.target
                    }
                    CharConstants.ENEMY -> if (lm.hero!!.health <= 0) {
                        lm.hero!!.isActive = false
                        lm.switchPlayingStatus()
                    } else {
                        //lm.hero.setWorldLocationX(lm.hero.getWorldLocation().x - 1);
                        if (hit == 1) {
                            lm.hero!!.setxVelocity(0f)
                        }
                        if (hit == 2) {
                            lm.hero!!.setyVelocity(0f)
                        }
                    }
                    CharConstants.TOWN -> soundManager.playSound("PutThatCookieDown")
                    else -> {
                        if (hit == 1) {
                            lm.hero!!.setxVelocity(0f)
                        }
                        if (hit == 2) {
                            lm.hero!!.setyVelocity(0f)
                        }
                    }
                }
            }
        }
    }

}