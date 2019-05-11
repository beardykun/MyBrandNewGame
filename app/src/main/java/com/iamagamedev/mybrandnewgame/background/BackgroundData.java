package com.iamagamedev.mybrandnewgame.background;

/**
 * Created by Михан on 31.05.2017.
 */

public class BackgroundData {
    String bitmapName;
    boolean isParallax;

    int layer;
    float startY;
    float endY;
    float speed;
    int height;
    int width;

    public BackgroundData(String bitmapName, boolean isParallax,
                          int layer, float startY, float endY, float speed, int height){
        this.bitmapName = bitmapName;
        this.isParallax = isParallax;
        this.layer = layer;
        this.startY = startY;
        this.endY = endY;
        this.speed = speed;
        this.height = height;
    }
}
