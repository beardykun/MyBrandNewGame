package com.iamagamedev.mybrandnewgame

import android.graphics.Rect

/**
 * Created by Михан on 24.05.2017.
 */
class Viewport(val screenWidth: Int, val screenHeight: Int) {
    private val currentViewportWorldCentre: LocationXYZ = LocationXYZ()
    private val convertedRect: Rect = Rect()
    val pixelsPerMetreX: Int = screenWidth / 20
    val pixelsPerMetreY: Int = screenHeight / 11
    private val screenCentreX: Int = screenWidth / 2
    val screenCentreY: Int = screenHeight / 2
    private val metresToShowX: Int = 20
    private val metresToShowY: Int = 14
    var numClipped = 0
        private set

    fun setWorldCentre(x: Float, y: Float) {
        currentViewportWorldCentre.x = x
        currentViewportWorldCentre.y = y
    }

    fun worldToScreen(objectX: Float, objectY: Float,
                      objectWidth: Float, objectHeight: Float): Rect {
        val left = (screenCentreX - ((currentViewportWorldCentre.x - objectX)
                * pixelsPerMetreX)).toInt()
        val top = (screenCentreY - ((currentViewportWorldCentre.y - objectY)
                * pixelsPerMetreY)).toInt()
        val right = (left + objectWidth * pixelsPerMetreX).toInt()
        val bottom = (top + objectHeight * pixelsPerMetreY).toInt()
        convertedRect[left, top, right] = bottom
        return convertedRect
    }

    fun clipObjects(objectX: Float, objectY: Float,
                    objectWidth: Float, objectHeight: Float): Boolean {
        var clipped = true
        if (objectX - objectWidth < currentViewportWorldCentre.x + metresToShowX / 2) {
            if (objectX + objectWidth > currentViewportWorldCentre.x - metresToShowX / 2) {
                if (objectY - objectHeight < currentViewportWorldCentre.y + metresToShowY / 2) {
                    if (objectY + objectHeight > currentViewportWorldCentre.y - metresToShowY / 2) {
                        clipped = false
                    }
                }
            }
        }
        if (clipped) numClipped++
        return clipped
    }

    fun resetNumClipped() {
        numClipped = 0
    }

    val currentViewportWorldCentreY: Float
        get() = currentViewportWorldCentre.y

}