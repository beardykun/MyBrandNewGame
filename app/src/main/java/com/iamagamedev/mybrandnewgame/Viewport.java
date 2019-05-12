package com.iamagamedev.mybrandnewgame;

import android.graphics.Rect;

/**
 * Created by Михан on 24.05.2017.
 */

public class Viewport {
    private LocationXYZ currentViewportWorldCentre;
    private Rect convertedRect;
    private int pixelsPerMetreX;
    private int pixelsPerMetreY;
    private int screenXResolution;
    private int screenYResolution;
    private int screenCentreX;
    private int screenCentreY;
    private int metresToShowX;
    private int metresToShowY;
    private int numClipped;

    public Viewport(int x, int y) {
        screenXResolution = x;
        screenYResolution = y;

        screenCentreX = screenXResolution / 2;
        screenCentreY = screenYResolution / 2;

        pixelsPerMetreX = screenXResolution / 20;
        pixelsPerMetreY = screenYResolution / 11;

        metresToShowX = 20;
        metresToShowY = 14;

        convertedRect = new Rect();
        currentViewportWorldCentre = new LocationXYZ();
    }

    public void setWorldCentre(float x, float y) {
        currentViewportWorldCentre.x = x;
        currentViewportWorldCentre.y = y;
    }

    public int getScreenWidth() {
        return screenXResolution;
    }

    public int getScreenHeight() {
        return screenYResolution;
    }

    public int getPixelsPerMetreX() {
        return pixelsPerMetreX;
    }

    public int getPixelsPerMetreY() {
        return pixelsPerMetreY;
    }

    public Rect worldToScreen(float objectX, float objectY,
                              float objectWidth, float objectHeight) {
        int left = (int) (screenCentreX - ((currentViewportWorldCentre.x - objectX)
                * pixelsPerMetreX));

        int top = (int) (screenCentreY - ((currentViewportWorldCentre.y - objectY)
                * pixelsPerMetreY));

        int right = (int) (left + (objectWidth * pixelsPerMetreX));

        int bottom = (int) (top + (objectHeight * pixelsPerMetreY));

        convertedRect.set(left, top, right, bottom);
        return convertedRect;
    }

    public boolean clipObjects(float objectX, float objectY,
                               float objectWidth, float objectHeight) {

        boolean clipped = true;

        if (objectX - objectWidth < currentViewportWorldCentre.x + (metresToShowX / 2)) {
            if (objectX + objectWidth > currentViewportWorldCentre.x - (metresToShowX / 2)) {
                if (objectY - objectHeight < currentViewportWorldCentre.y + (metresToShowY / 2)) {
                    if (objectY + objectHeight > currentViewportWorldCentre.y - (metresToShowY / 2)) {
                        clipped = false;
                    }
                }
            }
        }
        if (clipped)
            numClipped++;
        return clipped;
    }

    public int getNumClipped() {
        return numClipped;
    }

    public void resetNumClipped() {
        numClipped = 0;
    }

    public int getScreenCentreY() {
        return screenCentreY;
    }

    public float getCurrentViewportWorldCentreY() {
        return currentViewportWorldCentre.y;
    }
}
