package com.iamagamedev.mybrandnewgame

import android.graphics.Rect
import android.view.MotionEvent
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject

/**
 * Created by Михан on 23.05.2017.
 */
class InputController(screenWidth: Int, screenHeight: Int) {
    val moveDirectionLeft: Rect
    val moveDirectionRight: Rect
    val moveDirectionUp: Rect
    val moveDirectionDown: Rect
    val fireButton: Rect
    val dialogButton: Rect
    val gameDialogRect: Rect
    private var dialogCount = 0
    private var currentObject: GameObject? = null
    fun handleIntent(motionEvent: MotionEvent, levelManager: LevelManager, sm: SoundManager?, vp: Viewport?) {
        val pointerCount = motionEvent.pointerCount
        synchronized(this) {
            for (i in 0 until pointerCount) {
                val x = motionEvent.getX(i).toInt()
                val y = motionEvent.getY(i).toInt()
                when (motionEvent.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_MOVE -> if (moveDirectionLeft.contains(x, y)) {
                        levelManager.hero!!.setPressingUp(false)
                        levelManager.hero!!.setPressingLeft(true)
                        levelManager.hero!!.setPressingDown(false)
                        levelManager.hero!!.setPressingRight(false)
                    } else if (moveDirectionRight.contains(x, y)) {
                        levelManager.hero!!.setPressingUp(false)
                        levelManager.hero!!.setPressingLeft(false)
                        levelManager.hero!!.setPressingDown(false)
                        levelManager.hero!!.setPressingRight(true)
                    } else if (moveDirectionUp.contains(x, y)) {
                        levelManager.hero!!.setPressingUp(true)
                        levelManager.hero!!.setPressingLeft(false)
                        levelManager.hero!!.setPressingDown(false)
                        levelManager.hero!!.setPressingRight(false)
                    } else if (moveDirectionDown.contains(x, y)) {
                        levelManager.hero!!.setPressingUp(false)
                        levelManager.hero!!.setPressingLeft(false)
                        levelManager.hero!!.setPressingDown(true)
                        levelManager.hero!!.setPressingRight(false)
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                        levelManager.hero!!.setPressingUp(false)
                        levelManager.hero!!.setPressingLeft(false)
                        levelManager.hero!!.setPressingDown(false)
                        levelManager.hero!!.setPressingRight(false)
                        if (fireButton.contains(x, y)) {
                            levelManager.hero!!.fireSpell()
                        } else if (GameView.showTalkButton && dialogButton.contains(x, y)) {
                            levelManager.hero!!.isTalking = true
                            levelManager.switchPlayingStatus()
                        } else if (gameDialogRect.contains(x, y) && levelManager.hero!!.isTalking && !levelManager.isPlaying) {
                            if (currentObject != null) {
                                if (dialogCount == currentObject!!.dialogs!!.size - 1) {
                                    dialogCount = 0
                                    levelManager.hero!!.isTalking = false
                                    levelManager.switchPlayingStatus()
                                    return
                                }
                                dialogCount++
                            }
                        }
                    }
                }
            }
        }
    }

    fun getDialog(gameObject: GameObject?): String {
        currentObject = gameObject
        var dialog = ""
        if (gameObject != null && gameObject.dialogs != null && !gameObject.dialogs!!.isEmpty()) {
            dialog = gameObject.dialogs!![dialogCount]
        }
        return dialog
    }

    init {
        val buttonWidth = screenWidth / 12
        val buttonHeight = screenHeight / 7
        val buttonPudding = buttonWidth / 80
        moveDirectionLeft = Rect(screenWidth - buttonWidth * 3 - buttonPudding, screenHeight - buttonPudding - buttonHeight - buttonHeight / 2,
                screenWidth - buttonWidth * 2 - buttonPudding * 2, screenHeight - buttonPudding - buttonHeight / 2)
        moveDirectionRight = Rect(screenWidth - buttonWidth, screenHeight - buttonPudding - buttonHeight - buttonHeight / 2,
                screenWidth - buttonPudding, screenHeight - buttonPudding - buttonHeight / 2)
        moveDirectionDown = Rect(screenWidth - buttonPudding - buttonWidth * 2, screenHeight - buttonHeight,
                screenWidth - buttonPudding - buttonWidth, screenHeight - buttonPudding)
        moveDirectionUp = Rect(screenWidth - buttonPudding - buttonWidth * 2, screenHeight - buttonPudding - buttonHeight * 2,
                screenWidth - buttonPudding - buttonWidth, screenHeight - buttonPudding * 3 - buttonHeight)
        fireButton = Rect(buttonPudding + buttonWidth / 2, screenHeight - buttonPudding - buttonHeight * 2,
                buttonPudding + (buttonWidth * 1.5).toInt(), screenHeight - buttonPudding * 3 - buttonHeight)
        dialogButton = Rect(buttonPudding * 2 + buttonWidth / 2 + buttonWidth, screenHeight - buttonPudding - buttonHeight * 2,
                buttonPudding * 2 + (buttonWidth * 1.5).toInt() + buttonWidth, screenHeight - buttonPudding * 3 - buttonHeight)
        gameDialogRect = Rect((screenWidth * .1f).toInt(), (screenHeight * .6f).toInt(),
                (screenWidth - screenWidth * .1f).toInt(), (screenHeight - screenHeight * .1f).toInt())
    }
}