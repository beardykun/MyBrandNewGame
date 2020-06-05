package com.iamagamedev.mybrandnewgame;

import android.os.Handler;
import android.os.Looper;

import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject;

public class Utils {
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void fireSpell(final SpellObject spellObject, GameObject gameObject, String spellType) {
        SoundManager.getInstance().playPTCD();
        spellObject.setActive(false);
        spellObject.setMoves(false);
        spellObject.setVisible(false);
        spellObject.setFacing(gameObject.getFacing());
        spellObject.setSpellType(spellType, spellObject);
        spellObject.updatePosition(gameObject.getWorldLocation().x, gameObject.getWorldLocation().y);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                spellObject.setAnimated(LevelManager.pixelsPerMetre, true);
                spellObject.setVisible(true);
                spellObject.setActive(true);
                spellObject.setMoves(true);
            }
        }, spellObject.getWaitingTime());
    }
}
