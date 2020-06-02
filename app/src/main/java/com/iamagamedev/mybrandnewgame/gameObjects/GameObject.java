package com.iamagamedev.mybrandnewgame.gameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.iamagamedev.mybrandnewgame.Animation;
import com.iamagamedev.mybrandnewgame.LocationXYZ;
import com.iamagamedev.mybrandnewgame.RectHitBox;

import java.util.ArrayList;

/**
 * Created by Михан on 24.05.2017.
 */

public abstract class GameObject {

    public final int LEFT = -1;
    public final int RIGHT = 1;
    public final int UP = -2;
    public final int DOWN = 2;

    private LocationXYZ worldLocation;
    private RectHitBox rectHitBoxBottom = new RectHitBox();
    private RectHitBox rectHitBoxLeft = new RectHitBox();
    private RectHitBox rectHitBoxTop = new RectHitBox();
    private RectHitBox rectHitBoxRight = new RectHitBox();
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
    public float speed;
    private boolean isTalking;
    private ArrayList<String> dialogs;
    private boolean canTalk;


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
        LocationXYZ locationXYZ = getWorldLocation();
        float goX = locationXYZ.x;
        float goY = locationXYZ.y;
        //update the obj feet hitbox
        rectHitBoxBottom.top = goY + getHeight() * .95f;
        rectHitBoxBottom.left = goX + getWidth() * .2f;
        rectHitBoxBottom.bottom = goY + getHeight() * .98f;
        rectHitBoxBottom.right = goX + getWidth() * .8f;
        // Update obj head hitbox
        rectHitBoxTop.top = goY;
        rectHitBoxTop.left = goX + getWidth() * .3f;
        rectHitBoxTop.bottom = goY + getHeight() * .2f;
        rectHitBoxTop.right = goX + getWidth() * .7f;
        // Update obj left hitbox
        rectHitBoxLeft.top = goY + getHeight() * .2f;
        rectHitBoxLeft.left = goX + getWidth() * .2f;
        rectHitBoxLeft.bottom = goY + getHeight() * .8f;
        rectHitBoxLeft.right = goX + getWidth() * .3f;
        // Update obj right hitbox
        rectHitBoxRight.top = goY + getHeight() * .2f;
        rectHitBoxRight.left = goX + getWidth() * .71f;
        rectHitBoxRight.bottom = goY + getHeight() * .8f;
        rectHitBoxRight.right = goX + getWidth() * .8f;
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

    public int checkCollisions(RectHitBox rectHitBoxRight, RectHitBox rectHitBoxLeft,
                               RectHitBox rectHitBoxTop, RectHitBox rectHitBoxBottom) {
        int collided = 0;

        if (this.getRectHitBoxLeft().intersects(rectHitBoxRight)) {
            this.setWorldLocationX(rectHitBoxRight.right);
            collided = 1;
        }
        if (this.getRectHitBoxRight().intersects(rectHitBoxLeft)) {
            this.setWorldLocationX(rectHitBoxLeft.left - getWidth());
            collided = 1;
        }
        if (this.getRectHitBoxBottom().intersects(rectHitBoxTop)) {
            this.setWorldLocationY(rectHitBoxTop.top - getHeight());
            collided = 2;
        }
        if (this.getRectHitBoxTop().intersects(rectHitBoxBottom)) {
            this.setWorldLocationY(rectHitBoxBottom.bottom);
            collided = 2;
        }
        return collided;
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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isTalking() {
        return isTalking;
    }

    public void setTalking(boolean talking) {
        isTalking = talking;
    }

    public void setDialogs(ArrayList<String> dialogs) {
        this.dialogs = dialogs;
    }

    public ArrayList<String> getDialogs() {
        return dialogs;
    }

    public boolean isCanTalk() {
        return canTalk;
    }

    public void setCanTalk(boolean canTalk) {
        this.canTalk = canTalk;
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
}
