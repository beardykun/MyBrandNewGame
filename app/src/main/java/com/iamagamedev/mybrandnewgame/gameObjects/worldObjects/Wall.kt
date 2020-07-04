package com.iamagamedev.mybrandnewgame.gameObjects.worldObjects

import com.iamagamedev.mybrandnewgame.constants.ObjectNames
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject

/**
 * Created by Михан on 25.05.2017.
 */
class Wall(worldStartX: Float, worldStartY: Float, type: Char) : GameObject() {
    var x: Float
    var y: Float
    override fun update(fps: Long) {
        if (!isActive) setWorldLocation(x, y, -1)
    }

    init {
        var type = type
        val HEIGHT = 1f
        val WIDTH = 1f
        val HEALTH = 100
        x = worldStartX
        y = worldStartY
        width = WIDTH
        height = HEIGHT
        health = HEALTH
        this.type = type
        bitmapName = ObjectNames.WALL
        badBitmapName = ObjectNames.WALL_BAD
        setWorldLocation(worldStartX, worldStartY, -1)
        setRectHitBox()
    }
}