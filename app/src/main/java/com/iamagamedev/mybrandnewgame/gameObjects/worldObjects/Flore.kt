package com.iamagamedev.mybrandnewgame.gameObjects.worldObjects

import com.iamagamedev.mybrandnewgame.Constants.ObjectNames
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject

/**
 * Created by Михан on 03.06.2017.
 */
class Flore(worldStartX: Float, worldStartY: Float, type: Char) : GameObject() {
    override fun update(fps: Long) {}

    init {
        var type = type
        val HEIGHT = 1f
        val WIDTH = 1f
        height = HEIGHT
        width = WIDTH
        this.type = type
        bitmapName = ObjectNames.FLORE
        badBitmapName = ObjectNames.FLORE
        setWorldLocation(worldStartX, worldStartY, -1)
    }
}