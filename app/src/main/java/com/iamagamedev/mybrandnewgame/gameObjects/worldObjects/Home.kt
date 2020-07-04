package com.iamagamedev.mybrandnewgame.gameObjects.worldObjects

import com.iamagamedev.mybrandnewgame.constants.ObjectNames
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject
import com.iamagamedev.mybrandnewgame.levels.Location

/**
 * Created by Михан on 01.06.2017.
 */
class Home(worldStartX: Float, worldStartY: Float, type: Char, target: Location) : GameObject() {
    val target: Location
    override fun update(fps: Long) {}

    init {
        var type = type
        val HEIGHT = 1f
        val WIDTH = 1f
        val HEALTH = 1000
        width = WIDTH
        height = HEIGHT
        health = HEALTH
        this.type = type
        bitmapName = ObjectNames.HOME
        badBitmapName = ObjectNames.HOME
        this.target = Location(target.level, target.x, target.y)
        setWorldLocation(worldStartX, worldStartY, 0)
        setRectHitBox()
    }
}