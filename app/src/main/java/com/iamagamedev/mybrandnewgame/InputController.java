package com.iamagamedev.mybrandnewgame;

import android.graphics.Rect;
import android.view.MotionEvent;

import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;

/**
 * Created by Михан on 23.05.2017.
 */

public class InputController {
    private Rect moveDirectionLeft;
    private Rect moveDirectionRight;
    private Rect moveDirectionUp;
    private Rect moveDirectionDown;
    private Rect fireButton;
    private Rect dialogButton;
    private Rect gameDialogRect;
    private int dialogCount;
    private GameObject currentObject;

    public InputController(int screenWidth, int screenHeight) {
        int buttonWidth = screenWidth / 12;
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

        fireButton = new Rect(buttonPudding + buttonWidth / 2, screenHeight - buttonPudding - buttonHeight * 2,
                buttonPudding + (int) (buttonWidth * 1.5), screenHeight - buttonPudding * 3 - buttonHeight);

        dialogButton = new Rect(buttonPudding * 2 + buttonWidth / 2 + buttonWidth, screenHeight - buttonPudding - buttonHeight * 2,
                buttonPudding * 2 + (int) (buttonWidth * 1.5) + buttonWidth, screenHeight - buttonPudding * 3 - buttonHeight);

        gameDialogRect = new Rect((int) (screenWidth * .1f), (int) (screenHeight * .6f),
                (int) (screenWidth - screenWidth * .1f), (int) (screenHeight - screenHeight * .1f));
    }

    public void handleIntent(MotionEvent motionEvent, LevelManager levelManager, SoundManager sm, Viewport vp) {
        int pointerCount = motionEvent.getPointerCount();

        synchronized (this) {
            for (int i = 0; i < pointerCount; i++) {
                int x = (int) motionEvent.getX(i);
                int y = (int) motionEvent.getY(i);

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_MOVE:
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
                        } else if (moveDirectionUp.contains(x, y)) {
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
                    case MotionEvent.ACTION_POINTER_UP:
                        levelManager.hero.setPressingUp(false);
                        levelManager.hero.setPressingLeft(false);
                        levelManager.hero.setPressingDown(false);
                        levelManager.hero.setPressingRight(false);
                        if (fireButton.contains(x, y)) {
                            levelManager.hero.fireSpell();
                        } else if (GameView.showTalkButton && dialogButton.contains(x, y)) {
                            levelManager.hero.setTalking(true);
                            levelManager.switchPlayingStatus();
                        } else if (gameDialogRect.contains(x, y) && levelManager.hero.isTalking() && !levelManager.isPlaying()) {
                            if (currentObject != null) {
                                if (dialogCount == currentObject.getDialogs().size() - 1) {
                                    dialogCount = 0;
                                    levelManager.hero.setTalking(false);
                                    levelManager.switchPlayingStatus();
                                    return;
                                }
                                dialogCount++;
                            }
                        }
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

    public Rect getFireButton() {
        return fireButton;
    }

    public Rect getDialogButton() {
        return dialogButton;
    }

    public Rect getGameDialogRect() {
        return gameDialogRect;
    }

    public String getDialog(GameObject gameObject) {
        currentObject = gameObject;
        String dialog = "";
        if (gameObject != null && gameObject.getDialogs() != null && !gameObject.getDialogs().isEmpty()) {
            dialog = gameObject.getDialogs().get(dialogCount);
        }
        return dialog;
    }
}
