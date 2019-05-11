package com.iamagamedev.mybrandnewgame.gameObjects;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;

import com.iamagamedev.mybrandnewgame.LocationXYZ;
import com.iamagamedev.mybrandnewgame.RectHitBox;

/**
 * Created by Михан on 17.05.2017.
 */

public class Hero extends GameObject {

    private RectHitBox rectHitBoxBottom;
    private RectHitBox rectHitBoxLeft;
    private RectHitBox rectHitBoxTop;
    private RectHitBox rectHitBoxRight;

    private final float MAX_VELOCITY = 2;
    private Context context;
    private int pixelPerMetre;

    private boolean pressingRight, pressingLeft, pressingUp, pressingDown;

    public Hero(Context context, float worldStartX, float worldStartY,
                int pixelsPerMetre) {

        this.context = context;
        this.pixelPerMetre = pixelsPerMetre;

        final float HEALTH = 50;
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);
        setHealth(HEALTH);

        setxVelocity(0);
        setyVelocity(0);
        setFacing(LEFT);

        setMoves(true);
        setActive(true);
        setVisible(true);
        setType('p');

        setBitmapName("mage");
        setBadBitmapName("magedead");

        final int ANIMATION_FPS = 16;
        final int ANIMATION_FRAME_COUNT = 4;

        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setAnimated(pixelsPerMetre, true);

        setWorldLocation(worldStartX, worldStartY, 1);
        rectHitBoxBottom = new RectHitBox();
        rectHitBoxTop = new RectHitBox();
        rectHitBoxLeft = new RectHitBox();
        rectHitBoxRight = new RectHitBox();
    }


    @Override
    public void update(long fps) {
        if (pressingRight) {
            this.setxVelocity(MAX_VELOCITY);
        } else if (pressingLeft) {
            this.setxVelocity(-MAX_VELOCITY);
        } else {
            this.setxVelocity(0);
        }
        if (pressingUp) {
            this.setyVelocity(-MAX_VELOCITY);
        } else if (pressingDown) {
            this.setyVelocity(MAX_VELOCITY);
        } else {
            this.setyVelocity(0);
        }

        if (this.getxVelocity() > 0) {
            setFacing(RIGHT);
        } else if (this.getxVelocity() < 0) {
            setFacing(LEFT);
        } else if (this.getyVelocity() > 0) {
            setFacing(DOWN);
        } else if (this.getyVelocity() < 0) {
            setFacing(UP);
        }

        this.move(fps);

        LocationXYZ locationXYZ = getWorldLocation();
        float hiroX = locationXYZ.x;
        float hiroY = locationXYZ.y;
        //update the player feet hitbox
        rectHitBoxBottom.top = hiroY + getHeight() * .95f;
        rectHitBoxBottom.left = hiroX + getWidth() * .3f;
        rectHitBoxBottom.bottom = hiroY + getHeight() * .98f;
        rectHitBoxBottom.right = hiroX + getWidth() * .7f;
// Update player head hitbox
        rectHitBoxTop.top = hiroY;
        rectHitBoxTop.left = hiroX + getWidth() * .4f;
        rectHitBoxTop.bottom = hiroY + getHeight() * .2f;
        rectHitBoxTop.right = hiroX + getWidth() * .6f;
// Update player left hitbox
        rectHitBoxLeft.top = hiroY + getHeight() * .2f;
        rectHitBoxLeft.left = hiroX + getWidth() * .2f;
        rectHitBoxLeft.bottom = hiroY + getHeight() * .8f;
        rectHitBoxLeft.right = hiroX + getWidth() * .3f;
// Update player right hitbox
        rectHitBoxRight.top = hiroY + getHeight() * .2f;
        rectHitBoxRight.left = hiroX + getWidth() * .71f;
        rectHitBoxRight.bottom = hiroY + getHeight() * .8f;
        rectHitBoxRight.right = hiroX + getWidth() * .8f;
    }

    public int checkCollisions(RectHitBox rectHitBox) {
        int collided = 0;

        if (this.rectHitBoxLeft.intersects(rectHitBox)) {
            this.setWorldLocationX(rectHitBox.right);
            collided = 1;
        }
        if (this.rectHitBoxRight.intersects(rectHitBox)) {
            this.setWorldLocationX(rectHitBox.left - getWidth());
            collided = 1;
        }
        if (this.rectHitBoxBottom.intersects(rectHitBox)) {
            this.setWorldLocationY(rectHitBox.top - getHeight());
            collided = 2;
        }
        if (this.rectHitBoxTop.intersects(rectHitBox)) {
            this.setWorldLocationY(rectHitBox.bottom);
            collided = 3;
        }
        return collided;
    }


    public void setPressingDown(boolean pressingDown) {
        this.pressingDown = pressingDown;
    }

    public void setPressingUp(boolean pressingUp) {
        this.pressingUp = pressingUp;
    }

    public void setPressingLeft(boolean pressingLeft) {
        this.pressingLeft = pressingLeft;
    }

    public void setPressingRight(boolean pressingRight) {
        this.pressingRight = pressingRight;
    }

    public RectHitBox getRectHitBoxLeft() {
        return rectHitBoxLeft;
    }

    public RectHitBox getRectHitBoxBottom() {
        return rectHitBoxBottom;
    }

    public RectHitBox getRectHitBoxRight() {
        return rectHitBoxRight;
    }

    public RectHitBox getRectHitBoxTop() {
        return rectHitBoxTop;
    }

    public void moveHero(int x, int y){
          if (x > getWorldLocation().x) {
            setxVelocity(MAX_VELOCITY);
        } else if (x < getWorldLocation().x) {
            setxVelocity(-MAX_VELOCITY);
        } else {
            setxVelocity(0);
        }
        if (y >= getWorldLocation().y) {
            setyVelocity(MAX_VELOCITY);
        } else if (y < getWorldLocation().y) {
            setyVelocity(-MAX_VELOCITY);
        } else {
            setyVelocity(0);
        }
        Log.i("x,y", x +" " + y + " " + getWorldLocation().x +  " " + getWorldLocation().y);
    }
}
