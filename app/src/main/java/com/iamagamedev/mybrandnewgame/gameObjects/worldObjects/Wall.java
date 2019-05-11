package com.iamagamedev.mybrandnewgame.gameObjects.worldObjects;

import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;

/**
 * Created by Михан on 25.05.2017.
 */

public class Wall extends GameObject {
    float x;
    float y;

    public Wall(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;
        final int HEALTH = 100;

        x = worldStartX;
        y = worldStartY;
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setHealth(HEALTH);

        setType(type);

        setBitmapName("wall");
        setBadBitmapName("breaking");

        setWorldLocation(worldStartX, worldStartY, -1);
        setRectHitBox();
    }

    @Override
    public void update(long fps) {
        if (!isActive())
            setWorldLocation(x, y, -1);
    }
}
