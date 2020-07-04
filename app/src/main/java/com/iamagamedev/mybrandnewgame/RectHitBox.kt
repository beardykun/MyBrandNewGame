package com.iamagamedev.mybrandnewgame

/**
 * Created by Михан on 25.05.2017.
 */
class RectHitBox {
    var top = 0f
    var left = 0f
    var bottom = 0f
    var right = 0f
    fun intersects(rectHitBox: RectHitBox): Boolean {
        var hit = false
        if (right > rectHitBox.left
                && left < rectHitBox.right) {
            if (top < rectHitBox.bottom
                    && bottom > rectHitBox.top) {
                hit = true
            }
        }
        return hit
    }

}