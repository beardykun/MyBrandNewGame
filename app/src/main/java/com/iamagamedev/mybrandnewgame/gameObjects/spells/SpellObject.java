package com.iamagamedev.mybrandnewgame.gameObjects.spells;

import android.util.Log;

import com.iamagamedev.mybrandnewgame.Constants.SpellNames;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;

public abstract class SpellObject extends GameObject {

    private int waitingTime = 3000;
    private float x;
    private float y;

    public void updatePosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.setWorldLocation(x, y, getWorldLocation().z);
    }

    @Override
    public void update(long fps) {
        switch (this.getFacing()) {
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
        }
        super.move(fps);
    }

    public void setSpellType(String spellType, SpellObject spellObject) {
        switch (spellType) {
            case SpellNames.FIREBALL:
                SpellUtils.Companion.setFireball(spellObject);
                break;
        }
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    @Override
    public void setMoves(boolean moves) {
        super.setMoves(moves);
    }
}
