package com.iamagamedev.mybrandnewgame.gameObjects;

import android.content.Context;

import com.iamagamedev.mybrandnewgame.Constants.CharConstants;
import com.iamagamedev.mybrandnewgame.Constants.ObjectNames;
import com.iamagamedev.mybrandnewgame.Constants.SpellNames;
import com.iamagamedev.mybrandnewgame.RectHitBox;
import com.iamagamedev.mybrandnewgame.Utils;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.HeroSpell;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.ShieldSpellObject;

import java.util.ArrayList;

/**
 * Created by Михан on 17.05.2017.
 */

public class Hero extends GameObject {

    private HeroSpell spellObject;

    private ArrayList<String> spellObjects;

    private final float MAX_VELOCITY = 2;
    private int pixelPerMetre;

    private boolean pressingRight, pressingLeft, pressingUp, pressingDown;

    public Hero(Context context, float worldStartX, float worldStartY,
                int pixelsPerMetre) {

        this.pixelPerMetre = pixelsPerMetre;
        spellObjects = new ArrayList<>();
        spellObjects.add(SpellNames.FIREBALL);

        final float HEALTH = 50;
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);
        setHealth(HEALTH);

        setxVelocity(0);
        setyVelocity(0);
        setFacing(LEFT);

        setMoves(true);
        setActive(true);
        setVisible(true);
        setType(CharConstants.PLAYER);

        setBitmapName(ObjectNames.MAGE);
        setBadBitmapName(ObjectNames.MAGE_DEAD);

        final int ANIMATION_FPS = 16;
        final int ANIMATION_FRAME_COUNT = 4;

        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setAnimated(pixelsPerMetre, true);

        setWorldLocation(worldStartX, worldStartY, 1);
    }


    @Override
    public void update(long fps) {
        if (pressingRight) {
            this.setxVelocity(MAX_VELOCITY);
        } else if (pressingLeft) {
            this.setxVelocity(-MAX_VELOCITY);
        } else {
            this.setxVelocity(0);
        }
        if (pressingUp) {
            this.setyVelocity(-MAX_VELOCITY);
        } else if (pressingDown) {
            this.setyVelocity(MAX_VELOCITY);
        } else {
            this.setyVelocity(0);
        }

        if (this.getxVelocity() > 0) {
            setFacing(RIGHT);
        } else if (this.getxVelocity() < 0) {
            setFacing(LEFT);
        } else if (this.getyVelocity() > 0) {
            setFacing(DOWN);
        } else if (this.getyVelocity() < 0) {
            setFacing(UP);
        }

        this.move(fps);

        setRectHitBox();
        //updateShieldLocation();
    }

    public void setPressingDown(boolean pressingDown) {
        this.pressingDown = pressingDown;
    }

    public void setPressingUp(boolean pressingUp) {
        this.pressingUp = pressingUp;
    }

    public void setPressingLeft(boolean pressingLeft) {
        this.pressingLeft = pressingLeft;
    }

    public void setPressingRight(boolean pressingRight) {
        this.pressingRight = pressingRight;
    }

    public void fireSpell() {
        spellObject = HeroSpell.Companion.getInstance(this.getWorldLocation().x,
                this.getWorldLocation().y, CharConstants.SPELL, pixelPerMetre);
        Utils.fireSpell(spellObject, this, SpellNames.FIREBALL);
    }

    private void updateShieldLocation() {
        ShieldSpellObject shieldSpellObject = ShieldSpellObject.Companion.getInstance(
                getWorldLocation().x, getWorldLocation().y, CharConstants.SHIELD, pixelPerMetre);
        shieldSpellObject.setWorldLocation(getWorldLocation().x - 0.5f, getWorldLocation().y - 0.5f, 0);
    }
}
