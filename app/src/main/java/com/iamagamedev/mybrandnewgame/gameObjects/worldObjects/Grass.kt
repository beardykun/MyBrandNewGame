package com.iamagamedev.mybrandnewgame.gameObjects.worldObjects

import com.iamagamedev.mybrandnewgame.Constants.ObjectNames
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject

/**
 * Created by Михан on 24.05.2017.
 */
class Grass(worldStartX: Float, worldStartY: Float, type: Char) : GameObject() {
    override fun update(fps: Long) {}

    init {
        var type = type
        val HEIGHT = 1f
        val WIDTH = 1f
        height = HEIGHT
        width = WIDTH
        this.type = type
        bitmapName = ObjectNames.GRASS
        badBitmapName = ObjectNames.GRASS
        setWorldLocation(worldStartX, worldStartY, -1)
        setRectHitBox()
    }
}