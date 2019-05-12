package com.iamagamedev.mybrandnewgame.gameObjects.spells;

import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;

public class SpellObject extends GameObject {

    public SpellObject(float startWorldX, float startWorldY, char type) {
        setWidth(1);
        setHeight(1);
        setDamage(150);
        setVisible(true);
        setMoves(true);
        setActive(true);

        setType(type);
        setBitmapName("fireball");
        setWorldLocation(startWorldX, startWorldY, 0);
        setRectHitBox();
    }

    @Override
    public void update(long fps) {

    }
}
