package com.iamagamedev.mybrandnewgame.collisions;

import com.iamagamedev.mybrandnewgame.Constants.CharConstants;
import com.iamagamedev.mybrandnewgame.GameView;
import com.iamagamedev.mybrandnewgame.LevelManager;
import com.iamagamedev.mybrandnewgame.SoundManager;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Home;
import com.iamagamedev.mybrandnewgame.levels.Location;

public class HeroCollisions {
    private static GameObject collidedObject;

    private static int dialogLocationX;
    private static int dialogLocationY;

    public static void checkForCollisions(GameObject go, LevelManager lm, SoundManager soundManager) {
        if (go.getType() != CharConstants.SHIELD) {
            int hit = lm.hero.checkCollisions(go.getRectHitBoxRight(), go.getRectHitBoxLeft(),
                    go.getRectHitBoxTop(), go.getRectHitBoxBottom());
            if (hit > 0 && go.isCanTalk()) {
                collidedObject = go;
                GameView.showTalkButton = true;
                dialogLocationX = (int) lm.hero.getWorldLocation().x;
                dialogLocationY = (int) lm.hero.getWorldLocation().y;
            }
            if ((int) lm.hero.getWorldLocation().x != dialogLocationX || (int) lm.hero.getWorldLocation().y != dialogLocationY) {
                collidedObject = null;
                GameView.showTalkButton = false;
            }
            if (hit > 0) {
                lm.hero.setHealth(lm.hero.getHealth() - go.getDamage());
                switch (go.getType()) {
                    case CharConstants.HOME:
                        Home home = (Home) go;
                        Location t = home.getTarget();
                        //loadLevel(t.level, t.x, t.y);
                        break;
                    case CharConstants.ENEMY:
                        if (lm.hero.getHealth() <= 0) {
                            lm.hero.setAnimFrameCount(1);
                            lm.switchPlayingStatus();
                        } else {
                            //lm.hero.setWorldLocationX(lm.hero.getWorldLocation().x - 1);
                            if (hit == 1) {
                                lm.hero.setxVelocity(0);
                            }
                            if (hit == 2) {
                                lm.hero.setyVelocity(0);
                            }
                        }
                        break;
                    case CharConstants.TOWN:
                        soundManager.playSound("PutThatCookieDown");
                        break;
                    default:
                        if (hit == 1) {
                            lm.hero.setxVelocity(0);
                        }
                        if (hit == 2) {
                            lm.hero.setyVelocity(0);
                        }
                        break;
                }
            }
        }
    }

    public static GameObject getCollidedObject() {
        return collidedObject;
    }
}
