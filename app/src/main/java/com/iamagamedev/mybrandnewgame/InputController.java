package com.iamagamedev.mybrandnewgame;

import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Михан on 23.05.2017.
 */

public class InputController {
    private Rect moveDirectionLeft;
    private Rect moveDirectionRight;
    private Rect moveDirectionUp;
    private Rect moveDirectionDown;
    private int screenX;
    private int screenY;

    public InputController(int screenWidth, int screenHeight) {
        screenX = screenWidth;
        screenY = screenHeight;
        int buttonWidth = screenWidth / 8;
        int buttonHeight = screenHeight / 7;
        int buttonPudding = buttonWidth / 80;

        moveDirectionLeft = new Rect(screenWidth - buttonWidth * 3 - buttonPudding, screenHeight - buttonPudding - buttonHeight - buttonHeight / 2,
                screenWidth - buttonWidth * 2 - buttonPudding * 2, screenHeight - buttonPudding - buttonHeight / 2);

        moveDirectionRight = new Rect(screenWidth - buttonWidth, screenHeight - buttonPudding - buttonHeight - buttonHeight / 2,
                screenWidth - buttonPudding, screenHeight - buttonPudding - buttonHeight / 2);

        moveDirectionDown = new Rect(screenWidth - buttonPudding - buttonWidth * 2, screenHeight - buttonHeight,
                screenWidth - buttonPudding - buttonWidth, screenHeight - buttonPudding);

        moveDirectionUp = new Rect(screenWidth - buttonPudding - buttonWidth * 2, screenHeight - buttonPudding - buttonHeight * 2,
                screenWidth - buttonPudding - buttonWidth, screenHeight - buttonPudding * 3 - buttonHeight);
    }

    public void handleIntent(MotionEvent motionEvent, LevelManager levelManager, SoundManager sm, Viewport vp) {
        int pointerCount = motionEvent.getPointerCount();

        synchronized (this) {
            for (int i = 0; i < pointerCount; i++) {
                int x = (int) motionEvent.getX(i);
                int y = (int) motionEvent.getY(i);

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if (!levelManager.isPlaying()){
                            levelManager.switchPlayingStatus();
                        }
                        if (moveDirectionLeft.contains(x, y)) {
                            levelManager.hero.setPressingUp(false);
                            levelManager.hero.setPressingLeft(true);
                            levelManager.hero.setPressingDown(false);
                            levelManager.hero.setPressingRight(false);
                        } else if (moveDirectionRight.contains(x, y)) {
                            levelManager.hero.setPressingUp(false);
                            levelManager.hero.setPressingLeft(false);
                            levelManager.hero.setPressingDown(false);
                            levelManager.hero.setPressingRight(true);
                        }else if (moveDirectionUp.contains(x, y)) {
                            levelManager.hero.setPressingUp(true);
                            levelManager.hero.setPressingLeft(false);
                            levelManager.hero.setPressingDown(false);
                            levelManager.hero.setPressingRight(false);
                        } else if (moveDirectionDown.contains(x, y)) {
                            levelManager.hero.setPressingUp(false);
                            levelManager.hero.setPressingLeft(false);
                            levelManager.hero.setPressingDown(true);
                            levelManager.hero.setPressingRight(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:

                        levelManager.hero.setPressingUp(false);
                        levelManager.hero.setPressingLeft(false);
                        levelManager.hero.setPressingDown(false);
                        levelManager.hero.setPressingRight(false);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (moveDirectionLeft.contains(x, y)) {
                            levelManager.hero.setPressingUp(false);
                            levelManager.hero.setPressingLeft(true);
                            levelManager.hero.setPressingDown(false);
                            levelManager.hero.setPressingRight(false);
                        } else if (moveDirectionRight.contains(x, y)) {
                            levelManager.hero.setPressingUp(false);
                            levelManager.hero.setPressingLeft(false);
                            levelManager.hero.setPressingDown(false);
                            levelManager.hero.setPressingRight(true);
                        }else if (moveDirectionUp.contains(x, y)) {
                            levelManager.hero.setPressingUp(true);
                            levelManager.hero.setPressingLeft(false);
                            levelManager.hero.setPressingDown(false);
                            levelManager.hero.setPressingRight(false);
                        } else if (moveDirectionDown.contains(x, y)) {
                            levelManager.hero.setPressingUp(false);
                            levelManager.hero.setPressingLeft(false);
                            levelManager.hero.setPressingDown(true);
                            levelManager.hero.setPressingRight(false);
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_UP:

                        levelManager.hero.setPressingUp(false);
                        levelManager.hero.setPressingLeft(false);
                        levelManager.hero.setPressingDown(false);
                        levelManager.hero.setPressingRight(false);
                        break;
                }
            }
        }
    }

    public Rect getMoveDirectionLeft() {
        return moveDirectionLeft;
    }

    public Rect getMoveDirectionRight() {
        return moveDirectionRight;
    }

    public Rect getMoveDirectionDown() {
        return moveDirectionDown;
    }

    public Rect getMoveDirectionUp() {
        return moveDirectionUp;
    }
}
