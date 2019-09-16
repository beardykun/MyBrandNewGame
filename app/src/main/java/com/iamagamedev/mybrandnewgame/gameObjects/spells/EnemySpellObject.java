package com.iamagamedev.mybrandnewgame.gameObjects.spells;

import com.iamagamedev.mybrandnewgame.Constants.SpellNames;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;

public class EnemySpellObject extends GameObject {

    private static EnemySpellObject spellObject;

    private EnemySpellObject(float startWorldX, float startWorldY, char type, int pixelsPerMetre) {
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
                default:
                    setxVelocity(0);
                    setyVelocity(0);
                    break;

        }
        move(fps);
        setRectHitBox();
    }

    public static EnemySpellObject getInstance(float x, float y, char c, int pix){
        if(spellObject == null){
            spellObject = new EnemySpellObject(x, y, c, pix);
        }
        return spellObject;
    }

    public void updatePosition(float x, float y){
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
}
