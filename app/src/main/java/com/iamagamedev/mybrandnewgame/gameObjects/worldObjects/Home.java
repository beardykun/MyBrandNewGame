package com.iamagamedev.mybrandnewgame.gameObjects.worldObjects;

import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;
import com.iamagamedev.mybrandnewgame.levels.Location;

/**
 * Created by Михан on 01.06.2017.
 */

public class Home extends GameObject {

    Location target;

    public Home(float worldStartX, float worldStartY, char type, Location target) {
        final float HEIGHT = 1f;
        final float WIDTH = 1f;
        final float HEALTH = 1000;


        setWidth(WIDTH);
        setHeight(HEIGHT);
        setHealth(HEALTH);

        setType(type);

        setBitmapName("home");
        setBadBitmapName("home");

        this.target = new Location(target.level, target.x, target.y);


        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitBox();
    }

    @Override
    public void update(long fps) {
    }

    public Location getTarget() {
        return target;
    }
}
