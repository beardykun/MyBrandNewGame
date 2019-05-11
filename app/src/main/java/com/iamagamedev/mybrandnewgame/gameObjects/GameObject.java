package com.iamagamedev.mybrandnewgame.gameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.iamagamedev.mybrandnewgame.Animation;
import com.iamagamedev.mybrandnewgame.LocationXYZ;
import com.iamagamedev.mybrandnewgame.RectHitBox;

/**
 * Created by Михан on 24.05.2017.
 */

public abstract class GameObject {

    public final int LEFT = -1;
    public final int RIGHT = 1;
    public final int UP = -2;
    public final int DOWN = 2;

    private LocationXYZ worldLocation;
    private RectHitBox rectHitBox = new RectHitBox();
    private Animation animation = null;
    private boolean animated;
    private int animFps = 1;
    private boolean moves = false;
    private int facing;

    private float width;
    private float height;

    private float health;
    private float damage;

    private float xVelocity;
    private float yVelocity;

    private boolean active = true;
    private boolean visible = true;
    private int animFrameCount = 1;
    private char type;

    private String bitmapName;
    private String badBitmapName;

    public abstract void update(long fps);

    public Bitmap prepareBitmap(Context context,
                                String bitmapName, int pixelsPerMetre) {
        int resID = context.getResources()
                .getIdentifier(bitmapName,
                        "drawable", context.getPackageName());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (width * animFrameCount * pixelsPerMetre),
                (int) (height * pixelsPerMetre), false);

        return bitmap;
    }

    public LocationXYZ getWorldLocation() {
        return worldLocation;
    }

    public void setWorldLocation(float x, float y, int z) {
        this.worldLocation = new LocationXYZ();
        this.worldLocation.x = x;
        this.worldLocation.y = y;
        this.worldLocation.z = z;
    }

    public void setRectHitBox() {
        rectHitBox.setTop(worldLocation.y);
        rectHitBox.setLeft(worldLocation.x);
        rectHitBox.setBottom(worldLocation.y + height);
        rectHitBox.setRight(worldLocation.x + width);
    }

    public void setAnimated(int pixelsPerMetre, boolean animated) {
        this.animated = animated;
        this.animation = new Animation(bitmapName,
                height, width, animFps, animFrameCount, pixelsPerMetre);
    }

    public Rect getRectToDraw(long deltaTime) {
        return animation.getCurrentFrame(deltaTime, xVelocity, yVelocity, isMoves());
    }

    public void move(long fps) {
        if (xVelocity != 0) {
            this.getWorldLocation().x += xVelocity / fps;
        }
        if (yVelocity != 0) {
            this.getWorldLocation().y += yVelocity / fps;
        }
    }

    public RectHitBox getRectHitBox() {
        return rectHitBox;
    }

    public void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }

    public String getBitmapName() {
        return bitmapName;
    }

    public void setBadBitmapName(String badBitmapName) {
        this.badBitmapName = badBitmapName;
    }

    public String getBadBitmapName() {
        return badBitmapName;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setWorldLocationY(float y) {
        this.worldLocation.y = y;
    }

    public void setWorldLocationX(float x) {
        this.worldLocation.x = x;
    }

    public void setAnimFps(int animFps) {
        this.animFps = animFps;
    }

    public void setAnimFrameCount(int animFrameCount) {
        this.animFrameCount = animFrameCount;
    }

    public boolean isAnimated() {
        return animated;
    }

    public float getxVelocity() {
        return xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setxVelocity(float xVelocity) {
        if (moves)
            this.xVelocity = xVelocity;
    }

    public void setyVelocity(float yVelocity) {
        if (moves)
            this.yVelocity = yVelocity;
    }

    public boolean isMoves() {
        return moves;
    }

    public void setMoves(boolean moves) {
        this.moves = moves;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

}
