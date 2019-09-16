package com.iamagamedev.mybrandnewgame.gameObjects.worldObjects;

import com.iamagamedev.mybrandnewgame.Constants.ObjectNames;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;

/**
 * Created by Михан on 08.06.2017.
 */

public class Mountain extends GameObject {

    public Mountain(float worldStartX, float worldStartY, char type){
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setBitmapName(ObjectNames.MOUNT);
        setBadBitmapName(ObjectNames.MOUNT);

        setWorldLocation(worldStartX, worldStartY, -1);
        setRectHitBox();
    }

    @Override
    public void update(long fps) {

    }
}
