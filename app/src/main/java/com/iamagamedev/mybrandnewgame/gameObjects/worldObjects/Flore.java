package com.iamagamedev.mybrandnewgame.gameObjects.worldObjects;

import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;

/**
 * Created by Михан on 03.06.2017.
 */

public class Flore extends GameObject {

    public Flore(float worldStartX, float worldStartY, char type){
        final float HEIGHT = 1f;
        final float WIDTH = 1f;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setBitmapName("flore");
        setBadBitmapName("flore");

        setWorldLocation(worldStartX, worldStartY, -1);
    }
    @Override
    public void update(long fps) {

    }
}
