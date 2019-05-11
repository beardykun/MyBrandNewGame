package com.iamagamedev.mybrandnewgame.gameObjects.worldObjects;

import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;

/**
 * Created by Михан on 24.05.2017.
 */

public class Grass extends GameObject {

    public Grass(float worldStartX, float worldStartY, char type){
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setBitmapName("grass");
        setBadBitmapName("grass");

        setWorldLocation(worldStartX, worldStartY, -1);
        setRectHitBox();
    }
    @Override
    public void update(long fps) {

    }
}
