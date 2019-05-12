package com.iamagamedev.mybrandnewgame.gameObjects.enemys;

import android.graphics.PointF;

import com.iamagamedev.mybrandnewgame.LocationXYZ;
import com.iamagamedev.mybrandnewgame.RectHitBox;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;

/**
 * Created by Михан on 17.05.2017.
 */

public class Enemy extends GameObject {

    private long lastWaypointSetTime;
    private PointF currentWaypoint;
    private final float MAX_X_VELOCITY = 1.8f;
    private final float MAX_Y_VELOCITY = 1.8f;

    public Enemy(float worldStartX, float worldStartY,
                 char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;
        final float HEALTH = 150;
        final float DAMAGE = 15;

        setWidth(WIDTH);
        setHeight(HEIGHT);
        setHealth(HEALTH);
        setDamage(DAMAGE);

        setType(type);
        setMoves(true);
        setActive(true);
        setVisible(true);

        currentWaypoint = new PointF();

        setBitmapName("enemy");
        setBadBitmapName("enemyblow");

        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitBox();
    }

    @Override
    public void update(long fps) {
        if (currentWaypoint.x > getWorldLocation().x) {
            setxVelocity(MAX_X_VELOCITY);
        } else if (currentWaypoint.x < getWorldLocation().x) {
            setxVelocity(-MAX_X_VELOCITY);
        } else {
            setxVelocity(0);
        }
        if (currentWaypoint.y >= getWorldLocation().y) {
            setyVelocity(MAX_Y_VELOCITY);
        } else if (currentWaypoint.y < getWorldLocation().y) {
            setyVelocity(-MAX_Y_VELOCITY);
        } else {
            setyVelocity(0);
        }
        move(fps);
        setRectHitBox();
    }

    public void setWaypoint(LocationXYZ hiroLocation) {
        if (System.currentTimeMillis() > lastWaypointSetTime + 2000) {
            lastWaypointSetTime = System.currentTimeMillis();
            currentWaypoint.x = hiroLocation.x;
            currentWaypoint.y = hiroLocation.y;
        }
    }

    public int checkForEnemyCollisions(RectHitBox rectHitBox) {
        int collided = 0;

        if (this.getRectHitBox().intersects(rectHitBox)) {
            if (this.getRectHitBox().top < rectHitBox.bottom) {
                //this.setWorldLocationY(rectHitBox.bottom);
                collided = 1;
            } else if (this.getRectHitBox().bottom > rectHitBox.top) {
                //this.setWorldLocationY(rectHitBox.top);
                collided = 1;
            } else if (this.getRectHitBox().left < rectHitBox.right) {
                //this.setWorldLocationX(rectHitBox.right);
                collided = 2;
            } else if (this.getRectHitBox().right > rectHitBox.left) {
                //this.setWorldLocationX(rectHitBox.left);
                collided = 2;
            }
        }
        return collided;
    }

}
