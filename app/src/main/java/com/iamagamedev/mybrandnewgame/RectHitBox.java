package com.iamagamedev.mybrandnewgame;

/**
 * Created by Михан on 25.05.2017.
 */

public class RectHitBox {
    public float top;
    public float left;
    public float bottom;
    public float right;


    public boolean intersects(RectHitBox rectHitBox){
        boolean hit = false;

        if (this.right > rectHitBox.left
                && this.left < rectHitBox.right){
            if (this.top < rectHitBox.bottom
                    && this.bottom > rectHitBox.top){
                hit = true;
            }
        }
        return hit;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getBottom() {
        return bottom;
    }

    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getTop() {
        return top;
    }

}
