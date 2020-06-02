package com.iamagamedev.mybrandnewgame;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

/**
 * Created by Михан on 24.05.2017.
 */

public class SoundManager {

    private SoundPool soundPool;
    private int fireBall = -1;
    private int hiroEnemy = -1;
    private int fireballExplode = -1;
    private static SoundManager soundManager;

    private SoundManager() {
    }

    public static SoundManager getInstance() {
        if (soundManager == null) {
            soundManager = new SoundManager();
        }
        return soundManager;
    }

    public void loadSound() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            AssetManager assetManager = ThisApp.getInstance().getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("PutThatCookieDown.ogg");
            fireBall = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("hit_guard.ogg");
            hiroEnemy = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("explode.ogg");
            fireballExplode = soundPool.load(descriptor, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound(String sound) {
        switch (sound) {
            case "PutThatCookieDown":
                soundPool.play(fireBall, 1, 1, 0, 0, 1);
                break;
            case "hit_guard":
                soundPool.play(hiroEnemy, 1, 1, 0, 0, 1);
                break;
            case "explode":
                soundPool.play(fireballExplode, 1, 1, 0, 0, 1);
                break;
        }
    }
    public void playPTCD(){
        soundPool.play(fireBall, 1, 1, 0, 0, 1);
    }
}
