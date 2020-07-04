package com.iamagamedev.mybrandnewgame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.iamagamedev.mybrandnewgame.collisions.EnemyCollisions
import com.iamagamedev.mybrandnewgame.collisions.HeroCollisions
import com.iamagamedev.mybrandnewgame.collisions.SpellCollisions
import com.iamagamedev.mybrandnewgame.constants.MapNames
import com.iamagamedev.mybrandnewgame.gameObjects.EnemyObject

/**
 * Created by Михан on 17.05.2017.
 */
@SuppressLint("ViewConstructor")
class GameView(context: Context, private val screenWidth: Int, screenHeight: Int) : SurfaceView(context), Runnable {
    @Volatile
    var running = false
    private var gameThread: Thread? = null
    private var ic: InputController? = null
    private val soundManager: SoundManager = SoundManager.instance!!
    private var mediaPlayerManager: MediaPlayerManager = MediaPlayerManager()
    private var lm: LevelManager? = null
    private val viewport: Viewport
    private var canvas: Canvas? = null
    private val paint: Paint = Paint()
    private val ourHolder: SurfaceHolder = holder
    private var startFrameTime: Long = 0
    private var timeThisFrame: Long = 0
    var fps: Long = 0
    private val debugging = true

    private fun loadLevel(level: String, heroX: Float, heroY: Float) {
        lm = null
        lm = LevelManager(context, viewport.pixelsPerMetreX, screenWidth,
                level, heroX, heroY)
        ic = InputController(viewport.screenWidth, viewport.screenHeight)
        viewport.setWorldCentre(lm!!.gameObjects[lm!!.heroIndex].worldLocation!!.x,
                lm!!.gameObjects[lm!!.heroIndex].worldLocation!!.y)
    }

    override fun run() {
        while (running) {
            startFrameTime = System.currentTimeMillis()
            update()
            draw()
            timeThisFrame = System.currentTimeMillis() - startFrameTime
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame
            }
        }
    }

    private fun update() {
        for (go in lm!!.gameObjects) {
            if (go.isTalking) return
            if (!viewport.clipObjects(go.worldLocation!!.x,
                            go.worldLocation!!.y,
                            go.width, go.height)) {
                go.isVisible = true
                if (lm!!.isPlaying) {
                    if (go.isActive) {

                        go.update(fps)
                        if (go is EnemyObject && go.isAggressive) {
                            go.setWayPoint(lm?.hero?.worldLocation!!)
                        }

                        lm?.also {
                            HeroCollisions.checkForCollisions(go, it, soundManager)
                            EnemyCollisions.checkForEnemyCollisions(go, it)
                        }
                        SpellCollisions.checkForSpellCollisions(go)
                    } else {
                        if (go is EnemyObject && go.isDead) {
                            go.update(fps)
                        }
                    }
                }
            } else {
                go.isVisible = false
            }
            if (lm!!.isPlaying) {
                viewport.setWorldCentre(lm!!.gameObjects[lm!!.heroIndex].worldLocation!!.x,
                        lm!!.gameObjects[lm!!.heroIndex].worldLocation!!.y)
            }
        }
    }

    private fun draw() {
        if (ourHolder.surface.isValid) {
            canvas = ourHolder.lockCanvas()
            canvas?.drawColor(Color.GRAY)
            if (lm!!.level == MapNames.LEVEL_HOME) {
                canvas?.drawColor(Color.CYAN)
            }

            //drawBackground(3, -3);
            val toScreen2D = Rect()
            for (layer in -1..1) {
                for (go in lm!!.gameObjects) {
                    if (go.isVisible && go.worldLocation!!.z == layer) {
                        toScreen2D.set(viewport.worldToScreen(go.worldLocation!!.x,
                                go.worldLocation!!.y, go.width, go.height))
                        if (go.isAnimated) {
                            val flipper = Matrix()
                            when (go.facing) {
                                lm?.hero?.RIGHT -> {
                                    flipper.preScale(-1f, 1f)
                                }
                                lm?.hero?.DOWN -> {
                                    flipper.preRotate(-90f)
                                }
                                lm?.hero?.UP -> {
                                    flipper.preRotate(90f)
                                }
                            }
                            val r = go.getRectToDraw(System.currentTimeMillis())
                            if (go.isActive) {
                                val b = Bitmap.createBitmap(lm?.getBitmap(go.type)!!,
                                        r.left, r.top, r.width(), r.height(), flipper, true)
                                canvas?.drawBitmap(b, toScreen2D.left.toFloat(), toScreen2D.top.toFloat(), paint)
                            } else {
                                val b = Bitmap.createBitmap(lm?.getBadBitmap(go.type)!!,
                                        r.left, r.top, r.width(), r.height(), flipper, true)
                                canvas?.drawBitmap(b, toScreen2D.left.toFloat(), toScreen2D.top.toFloat(), paint)
                            }
                        } else {
                            if (go.isActive) {
                                canvas?.drawBitmap(lm?.getBitmap(go.type)!!,
                                        toScreen2D.left.toFloat(), toScreen2D.top.toFloat(), paint)
                            } else {
                                canvas?.drawBitmap(lm?.getBadBitmap(go.type)!!,
                                        toScreen2D.left.toFloat(), toScreen2D.top.toFloat(), paint)
                            }
                        }
                        if (lm?.hero?.isTalking!! && showTalkButton) {
                            drawDialog(canvas)
                        }
                    }
                }
            }
            debuggingInfo(debugging, canvas)
            if (!lm!!.hero!!.isTalking && lm?.isPlaying!!) {
                paint.color = Color.BLACK
                val moveDirectionLeft = ic!!.moveDirectionLeft
                canvas?.drawRect(moveDirectionLeft, paint)
                val moveDirectionRight = ic!!.moveDirectionRight
                canvas?.drawRect(moveDirectionRight, paint)
                val moveDirectionDown = ic!!.moveDirectionDown
                canvas?.drawRect(moveDirectionDown, paint)
                val moveDirectionUp = ic!!.moveDirectionUp
                canvas?.drawRect(moveDirectionUp, paint)
                canvas?.drawRect(ic!!.fireButton, paint)
                if (showTalkButton) canvas?.drawRect(ic!!.dialogButton, paint)
            }
            ourHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun drawBackground(start: Int, stop: Int) {
        var fromRect1 = Rect()
        var toRect1 = Rect()
        var fromRect2 = Rect()
        var toRect2 = Rect()
        for (bg in lm?.backgrounds!!) {
            if (bg.z in (stop + 1) until start) {
                // Is this layer in the viewport?
                // Clip anything off-screen
                if (!viewport.clipObjects(-1f, bg.y, 1000f, bg.height.toFloat())) {
                    val floatstartY = viewport.screenCentreY - (viewport.currentViewportWorldCentreY - bg.y) * viewport.pixelsPerMetreY
                    val startY = floatstartY.toInt()
                    val floatendY = viewport.screenCentreY - (viewport.currentViewportWorldCentreY - bg.endY) * viewport.pixelsPerMetreY
                    val endY = floatendY.toInt()

                    //define what portion of bitmaps to capture and what coordinates to draw them at
                    fromRect1 = Rect(0, 0, bg.width - bg.xClip, bg.height)
                    toRect1 = Rect(bg.xClip, startY, bg.width, endY)
                    fromRect2 = Rect(bg.width - bg.xClip, 0, bg.width, bg.height)
                    toRect2 = Rect(0, startY, bg.xClip, endY)
                }

                //draw backgrounds
                if (!bg.reversedFirst) {
                    canvas!!.drawBitmap(bg.bitmap, fromRect1, toRect1, paint)
                    canvas!!.drawBitmap(bg.bitmapReversed, fromRect2, toRect2, paint)
                } else {
                    canvas!!.drawBitmap(bg.bitmap, fromRect2, toRect2, paint)
                    canvas!!.drawBitmap(bg.bitmapReversed, fromRect1, toRect1, paint)
                }
                //(bg.xClip -= lm!!.hero.getxVelocity() / (20 / bg.speed).toInt()).toInt()
                if (bg.xClip >= bg.width) {
                    bg.xClip = 0
                    bg.reversedFirst = !bg.reversedFirst
                } else if (bg.xClip <= 0) {
                    bg.xClip = bg.width
                    bg.reversedFirst = !bg.reversedFirst
                }
            }
        }
    }

    fun resume() {
        mediaPlayerManager.startPlayer()
        running = true
        gameThread = Thread(this)
        gameThread!!.start()
    }

    fun pause(isFinishing: Boolean) {
        mediaPlayerManager.stopPlayer(isFinishing)
        running = false
        try {
            gameThread!!.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (ic != null) {
            ic!!.handleIntent(event, lm!!, soundManager, viewport)
        }
        return true
    }

    private fun debuggingInfo(debugging: Boolean, canvas: Canvas?) {
        if (debugging) {
            paint.textSize = 26f
            paint.textAlign = Paint.Align.LEFT
            paint.color = Color.RED
            canvas!!.drawText("fps:$fps", 10f, 60f, paint)
            canvas.drawText("num objects:" +
                    lm!!.gameObjects.size, 10f, 80f, paint)
            canvas.drawText("num clipped:" +
                    viewport.numClipped, 10f, 100f, paint)
            canvas.drawText("playerX:" +
                    lm!!.gameObjects[lm!!.heroIndex].worldLocation!!.x, 10f, 120f, paint)
            canvas.drawText("playerY:" +
                    lm!!.gameObjects[lm!!.heroIndex].worldLocation!!.y, 10f, 140f, paint)
            //for reset the number of clipped objects each frame
            viewport.resetNumClipped()
        } // End if(debugging)
    }

    private fun drawDialog(canvas: Canvas?) {
        paint.textSize = 70f
        paint.textAlign = Paint.Align.LEFT
        paint.color = Color.BLACK
        canvas!!.drawRect(ic!!.gameDialogRect, paint)
        paint.color = Color.WHITE
        canvas.drawText(ic!!.getDialog(HeroCollisions.collidedObject), ic!!.gameDialogRect.left + 30.toFloat(),
                ic!!.gameDialogRect.top + 100.toFloat(), paint)
    }

    companion object {
        @JvmField
        var showTalkButton = false
    }

    init {
        mediaPlayerManager.loadMediaPlayer()
        soundManager.loadSound()
        viewport = Viewport(screenWidth, screenHeight)

        //loadLevel(MapNames.WORLD_MAP, 3f, 5f)
        loadLevel(MapNames.LEVEL_FIRST, 1f, 1f)
        //loadLevel(MapNames.LEVEL_HOME, 2f, 8f)
        //loadLevel(MapNames.LEVEL_BATTLE, 2f, 2f)
    }
}