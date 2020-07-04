package com.iamagamedev.mybrandnewgame

import android.app.Activity
import android.graphics.Point
import android.media.AudioManager
import android.os.Bundle
import android.view.KeyEvent

class GameActivity : Activity() {
    private var gameView: GameView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        volumeControlStream = AudioManager.STREAM_MUSIC
        gameView = GameView(this, size.x, size.y)
        setContentView(gameView)
    }

    override fun onResume() {
        super.onResume()
        gameView!!.resume()
    }

    override fun onPause() {
        super.onPause()
        gameView!!.pause(isFinishing)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return false
    }
}