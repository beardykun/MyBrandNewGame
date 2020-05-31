package com.iamagamedev.mybrandnewgame.gameObjects.spells;

import com.iamagamedev.mybrandnewgame.Constants.SpellNames;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;

public abstract class SpellObject extends GameObject {

    private int waitingTime = 3000;

    public void updatePosition(float x, float y) {
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
        this.setWorldLocation(x, y, getWorldLocation().z);
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
