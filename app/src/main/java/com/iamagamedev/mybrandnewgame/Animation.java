package com.iamagamedev.mybrandnewgame;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by Михан on 25.05.2017.
 */

public class Animation {
    Bitmap bitmapSheet;
    private String bitmapName;
    private Rect soursRect;
    private int frameCount;
    private int currentFrame;
    private long frameTicker;
    private int framePeriod;
    private float frameWidth;
    private float frameHeight;
    private int pixelsPerMetre;

    public Animation(String bitmapName, float frameHeight,
                     float frameWidth, int animFps, int frameCount, int pixelsPerMetre) {
        this.currentFrame = 0;
        this.frameCount = frameCount;
        this.frameWidth = frameWidth * pixelsPerMetre;
        this.frameHeight = frameHeight * pixelsPerMetre;
        soursRect = new Rect(0, 0, (int) this.frameWidth, (int) this.frameHeight);
        framePeriod = 1000 / animFps;
        frameTicker = 0L;
        this.bitmapName = "" + bitmapName;
        this.pixelsPerMetre = pixelsPerMetre;
    }

    public Rect getCurrentFrame(long time, float xVelocity,
                                float yVelocity, boolean moves) {
        if (xVelocity != 0 || yVelocity != 0 || !moves) {

            if (time > frameTicker + framePeriod) {
                frameTicker = time;
                currentFrame++;
                if (currentFrame >= frameCount) {
                    currentFrame = 0;
                }
            }
        }
        this.soursRect.left = currentFrame * (int) frameWidth;
        this.soursRect.right = this.soursRect.left + (int) frameWidth;

        return soursRect;
    }
}
