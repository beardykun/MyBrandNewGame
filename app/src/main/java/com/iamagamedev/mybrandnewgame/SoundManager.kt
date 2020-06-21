package com.iamagamedev.mybrandnewgame

import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.SoundPool

/**
 * Created by Михан on 24.05.2017.
 */
class SoundManager private constructor() {
    private var soundPool: SoundPool? = null
    private var fireBall = -1
    private var heroEnemy = -1
    private var fireballExplode = -1
    fun loadSound() {
        soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        try {
            val assetManager = ThisApp.getInstance().assets
            var descriptor: AssetFileDescriptor
            descriptor = assetManager.openFd("PutThatCookieDown.ogg")
            fireBall = soundPool!!.load(descriptor, 0)
            descriptor = assetManager.openFd("hit_guard.ogg")
            heroEnemy = soundPool!!.load(descriptor, 0)
            descriptor = assetManager.openFd("explode.ogg")
            fireballExplode = soundPool!!.load(descriptor, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playSound(sound: String?) {
        when (sound) {
            "PutThatCookieDown" -> soundPool!!.play(fireBall, 1f, 1f, 0, 0, 1f)
            "hit_guard" -> soundPool!!.play(heroEnemy, 1f, 1f, 0, 0, 1f)
            "explode" -> soundPool!!.play(fireballExplode, 1f, 1f, 0, 0, 1f)
        }
    }

    fun playPTCD() {
        soundPool!!.play(fireBall, 1f, 1f, 0, 0, 1f)
    }

    companion object {
        private var soundManager: SoundManager? = null
        val instance: SoundManager?
            get() {
                if (soundManager == null) {
                    soundManager = SoundManager()
                }
                return soundManager
            }
    }
}