package com.iamagamedev.mybrandnewgame.gameObjects.enemys;

import android.graphics.PointF;
import android.util.Log;

import com.iamagamedev.mybrandnewgame.Constants.CharConstants;
import com.iamagamedev.mybrandnewgame.Constants.ObjectNames;
import com.iamagamedev.mybrandnewgame.Constants.SpellNames;
import com.iamagamedev.mybrandnewgame.LocationXYZ;
import com.iamagamedev.mybrandnewgame.RectHitBox;
import com.iamagamedev.mybrandnewgame.Utils;
import com.iamagamedev.mybrandnewgame.gameObjects.EnemyObject;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.EnemyShieldObject;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.EnemySpellObject;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject;

import java.util.ArrayList;

/**
 * Created by Михан on 17.05.2017.
 */

public class Enemy extends EnemyObject {

    private long lastWaypointSetTime;
    private PointF currentWaypoint;
    private final float MAX_X_VELOCITY = 1.8f;
    private final float MAX_Y_VELOCITY = 1.8f;
    private int pixelPerMetre;

    public Enemy(float worldStartX, float worldStartY,
                 char type, int pixelPerMetre) {
        this.pixelPerMetre = pixelPerMetre;
        final float HEIGHT = 1;
        final float WIDTH = 1;
        final float HEALTH = 150;
        final float DAMAGE = 15;

        setWidth(WIDTH);
        setHeight(HEIGHT);
        setHealth(HEALTH);
        setDamage(DAMAGE);

        setEnemySkill(80);
        setEnemyTired(0);
        setEnemyFear(0);

        setType(type);
        setMoves(true);
        setActive(true);
        setVisible(true);
        setFacing(LEFT);
        setCanTalk(true);
        setDialogs(getDialogsList());

        currentWaypoint = new PointF();

        setBitmapName(ObjectNames.ENEMY);
        setBadBitmapName(ObjectNames.ENEMY_BAD);

        setWorldLocation(worldStartX, worldStartY, 1);
        setRectHitBox();
    }

    @Override
    public void update(long fps) {
        if (currentWaypoint.x > getWorldLocation().x) {
            setxVelocity(MAX_X_VELOCITY);
        } else if (currentWaypoint.x < getWorldLocation().x) {
            setxVelocity(-MAX_X_VELOCITY);
        } else {
            setxVelocity(0);
        }
        if (currentWaypoint.y >= getWorldLocation().y) {
            setyVelocity(MAX_Y_VELOCITY);
        } else if (currentWaypoint.y < getWorldLocation().y) {
            setyVelocity(-MAX_Y_VELOCITY);
        } else {
            setyVelocity(0);
        }
        move(fps);
        setRectHitBox();
        //fireSpell();
        //updateShieldLocation();
    }

    public void setWaypoint(LocationXYZ heroLocation) {
        if (System.currentTimeMillis() > lastWaypointSetTime + 2000) {
            lastWaypointSetTime = System.currentTimeMillis();
            currentWaypoint.x = heroLocation.x + 5;
            currentWaypoint.y = heroLocation.y;
        }
    }

    public int checkForEnemyCollisions(RectHitBox rectHitBox) {
        int collided = 0;

        if (this.getRectHitBox().intersects(rectHitBox)) {
            if (this.getRectHitBox().top < rectHitBox.bottom) {
                //this.setWorldLocationY(rectHitBox.bottom);
                collided = 1;
            } else if (this.getRectHitBox().bottom > rectHitBox.top) {
                //this.setWorldLocationY(rectHitBox.top);
                collided = 1;
            } else if (this.getRectHitBox().left < rectHitBox.right) {
                //this.setWorldLocationX(rectHitBox.right);
                collided = 2;
            } else if (this.getRectHitBox().right > rectHitBox.left) {
                //this.setWorldLocationX(rectHitBox.left);
                collided = 2;
            }
        }
        return collided;
    }

    private void fireSpell() {
        EnemySpellObject enemySpellObject = EnemySpellObject.Companion.getInstance(
                this.getWorldLocation().x, this.getWorldLocation().y, CharConstants.ENEMY_SPELL, pixelPerMetre);
        Utils.fireSpell(enemySpellObject, this, SpellNames.FIREBALL);
    }

    public void isUnderAttack(SpellObject spellObject) {
        if (spellObject.getWorldLocation().x + 3 == this.getWorldLocation().x) {
            if (successfulDefenceOrAttack()) {
                updateShieldLocation();
            }
        }
    }

    private void updateShieldLocation() {
        EnemyShieldObject enemyShieldObject = EnemyShieldObject.Companion.getInstance(
                getWorldLocation().x, getWorldLocation().y, CharConstants.ENEMY_SHIELD, pixelPerMetre);
        enemyShieldObject.setWorldLocation(getWorldLocation().x - 0.5f, getWorldLocation().y - 0.5f, 0);
    }

    private ArrayList<String> getDialogsList() {
        ArrayList<String> dialogs = new ArrayList<>(3);
        dialogs.add("Hello who are you?");
        dialogs.add("にゃんにゃんニャンコだぜ！");
        dialogs.add("What the f*ck are you talking shithead?");
        dialogs.add("It is にゃんこ大戦争! 死ね！");
        return dialogs;
    }
}
