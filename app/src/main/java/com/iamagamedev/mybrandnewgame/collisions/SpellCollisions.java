package com.iamagamedev.mybrandnewgame.collisions;

import android.os.Handler;
import android.os.Looper;

import com.iamagamedev.mybrandnewgame.Constants.CharConstants;
import com.iamagamedev.mybrandnewgame.LevelManager;
import com.iamagamedev.mybrandnewgame.SoundManager;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject;

public class SpellCollisions {

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void checkForSpellCollisions(GameObject go, final LevelManager lm) {
        if (go == lm.spellObject) return;
        int col = 0;
        col = lm.spellObject.checkCollisions(go.getRectHitBoxRight(), go.getRectHitBoxLeft(),
                go.getRectHitBoxTop(), go.getRectHitBoxBottom());
        if (col > 0) {
            switch (go.getType()) {
                case CharConstants.ENEMY_SHIELD:
                    spellIsUsed(lm.spellObject);
                    break;
                case CharConstants.WALL:
                    go.setActive(false);
                    spellIsUsed(lm.spellObject);
                    break;
                case CharConstants.ENEMY:
                case CharConstants.ENEMY_TANKU:
                    break;
                default:
                    break;
            }
        }
    }

    private static void spellIsUsed(final SpellObject spellObject) {
        if (!spellObject.isActive()) return;
        SoundManager.getInstance().playSound("explode");
        spellObject.setActive(false);
        spellObject.setMoves(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                spellObject.setWorldLocation(-1000, -1000, 0);
            }
        }, 1000);
    }
}
