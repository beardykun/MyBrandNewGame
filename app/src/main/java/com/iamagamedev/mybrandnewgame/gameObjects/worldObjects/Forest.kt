package com.iamagamedev.mybrandnewgame.gameObjects.worldObjects

import com.iamagamedev.mybrandnewgame.constants.ObjectNames
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject

/**
 * Created by Михан on 08.06.2017.
 */
class Forest(worldStartX: Float, worldStartY: Float, type: Char) : GameObject() {
    override fun update(fps: Long) {}

    init {
        val HEIGHT = 1f
        val WIDTH = 1f
        height = HEIGHT
        width = WIDTH
        this.type = type
        bitmapName = ObjectNames.WOOD
        badBitmapName = ObjectNames.WOOD
        setWorldLocation(worldStartX, worldStartY, -1)
        setRectHitBox()
    }
}