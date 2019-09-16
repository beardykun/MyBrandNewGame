package com.iamagamedev.mybrandnewgame.gameObjects.spells;

import com.iamagamedev.mybrandnewgame.Constants.SpellNames;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;

public class SpellObject extends GameObject {

    private static SpellObject spellObject;
    private GameObject currentSpell;

    private SpellObject(float startWorldX, float startWorldY, char type, int pixelsPerMetre) {
        setWidth(1);
        setHeight(1);
        setDamage(150);
        setVisible(true);
        setMoves(true);
        setActive(true);

        setType(type);
        setBitmapName(SpellNames.FIREBALL);
        setBadBitmapName(SpellNames.FIREBALL_BAD);
        setWorldLocation(startWorldX, startWorldY, 0);
        final int ANIMATION_FPS = 16;
        final int ANIMATION_FRAME_COUNT = 4;

        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setAnimated(pixelsPerMetre, true);
        setRectHitBox();
    }

    @Override
    public void update(long fps) {
        switch (this.getFacing()) {
            case LEFT:
                setxVelocity(-10);
                setyVelocity(0);
                break;
            case RIGHT:
                setxVelocity(10);
                setyVelocity(0);
                break;
            case UP:
                setyVelocity(-10);
                setxVelocity(0);
                break;
            case DOWN:
                setyVelocity(10);
                setxVelocity(0);
                break;
        }
        move(fps);
        setRectHitBox();
    }

    public static SpellObject getInstance(float startWorldX, float startWorldY, char type, int pixelsPerMetre) {
        if (spellObject == null) {
            spellObject = new SpellObject(startWorldX, startWorldY, type, pixelsPerMetre);
        }
        return spellObject;
    }

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

    public void setSpellType(String spellType) {
        switch (spellType){
            case SpellNames.FIREBALL:
                currentSpell = new FireBall();
                break;
        }
    }
    
}
