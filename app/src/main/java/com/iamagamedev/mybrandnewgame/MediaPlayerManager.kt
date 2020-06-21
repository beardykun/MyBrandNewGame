package com.iamagamedev.mybrandnewgame

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import java.io.IOException

class MediaPlayerManager {

    var mediaPlayer: MediaPlayer? = null

    fun loadMediaPlayer() {
        mediaPlayer = MediaPlayer()
        try {
            val assetManager = ThisApp.getInstance().assets
            val descriptor: AssetFileDescriptor = assetManager.openFd("unbeatable_guild.wav")
            mediaPlayer?.setDataSource(descriptor.fileDescriptor,
                    descriptor.startOffset, descriptor.length)
            mediaPlayer?.prepare()
            mediaPlayer?.isLooping = true
        } catch (e: IOException) {
            mediaPlayer = null
            e.printStackTrace()
        }
    }

    fun startPlayer() {
        mediaPlayer?.start()
    }

    fun stopPlayer(isFinishing: Boolean) {
        mediaPlayer?.pause()
        if (isFinishing) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
    }
}