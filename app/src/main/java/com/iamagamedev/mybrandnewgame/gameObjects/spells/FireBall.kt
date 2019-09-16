package com.iamagamedev.mybrandnewgame.gameObjects.spells

import com.iamagamedev.mybrandnewgame.Constants.SpellNames
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject

class FireBall() : GameObject() {

    constructor(heroSkill: Int) : this (){
        width = 1f
        height = 1f
        damage = 150f
        isVisible = true
        isMoves = true
        isActive = true

        type = type
        bitmapName = SpellNames.FIREBALL
        badBitmapName = SpellNames.FIREBALL_BAD

        val ANIMATION_FPS = 16

        setAnimFps(ANIMATION_FPS)
    }

    override fun update(fps: Long) {
    }
}