package com.iamagamedev.mybrandnewgame.collisions;

import android.os.Handler;

import com.iamagamedev.mybrandnewgame.Constants.CharConstants;
import com.iamagamedev.mybrandnewgame.Constants.SpellNames;
import com.iamagamedev.mybrandnewgame.LevelManager;
import com.iamagamedev.mybrandnewgame.gameObjects.EnemyObject;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;


public class EnemyCollisions {

    public static void checkForEnemyCollisions(GameObject go, LevelManager lm) {
        for (int i = 0; i < lm.enemisList.size(); i++) {
            EnemyObject enemy = (EnemyObject) lm.gameObjects.get(lm.enemisList.get(i));
            if (go == enemy) return;
            int col = 0;
            col = enemy.checkCollisions(go.getRectHitBoxRight(), go.getRectHitBoxLeft(),
                    go.getRectHitBoxTop(), go.getRectHitBoxBottom());
            if (col > 0) {
                switch (go.getType()) {
                    case CharConstants.SHIELD:
                    case CharConstants.SPELL:
                    case CharConstants.WALL:
                    case CharConstants.ENEMY:
                    case CharConstants.ENEMY_TANKU:
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
