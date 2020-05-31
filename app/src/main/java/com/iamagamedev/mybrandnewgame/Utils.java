package com.iamagamedev.mybrandnewgame;

import android.os.Handler;
import android.os.Looper;

import com.iamagamedev.mybrandnewgame.Constants.CharConstants;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject;

public class Utils {

    public static void fireSpell(SpellObject spellObject, GameObject gameObject, String spellType) {
        if (spellObject.isVisible())return;
        SoundManager.getInstance().playPTCD();
        spellObject.setFacing(gameObject.getFacing());
        spellObject.setSpellType(spellType, spellObject);
        spellObject.updatePosition(gameObject.getWorldLocation().x, gameObject.getWorldLocation().y);
        spellObject.setMoves(false);
        Handler handler = new Handler(Looper.getMainLooper());
        final SpellObject finalSpellObject = spellObject;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finalSpellObject.setMoves(true);
            }
        },spellObject.getWaitingTime());
    }
}
