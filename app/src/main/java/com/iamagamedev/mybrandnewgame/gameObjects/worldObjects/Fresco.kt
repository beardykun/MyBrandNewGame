package com.iamagamedev.mybrandnewgame.gameObjects.worldObjects

import com.iamagamedev.mybrandnewgame.constants.ObjectNames
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject

class Fresco (worldStartX: Float, worldStartY: Float, type: Char) : GameObject(){

    override fun update(fps: Long) {}

    init {
        val HEIGHT = 13f
        val WIDTH = 26f
        height = HEIGHT
        width = WIDTH
        this.type = type
        bitmapName = ObjectNames.FRESCO
        badBitmapName = ObjectNames.FRESCO
        setWorldLocation(worldStartX, worldStartY, -1)
    }
}