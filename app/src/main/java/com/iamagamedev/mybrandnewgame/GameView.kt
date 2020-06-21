package com.iamagamedev.mybrandnewgame

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.iamagamedev.mybrandnewgame.Constants.CharConstants
import com.iamagamedev.mybrandnewgame.Constants.MapNames
import com.iamagamedev.mybrandnewgame.collisions.EnemyCollisions
import com.iamagamedev.mybrandnewgame.collisions.HeroCollisions
import com.iamagamedev.mybrandnewgame.collisions.SpellCollisions
import com.iamagamedev.mybrandnewgame.gameObjects.EnemyObject
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject
import com.iamagamedev.mybrandnewgame.gameObjects.enemys.Enemy
import com.iamagamedev.mybrandnewgame.gameObjects.enemys.TankuNeko

/**
 * Created by Михан on 17.05.2017.
 */
class GameView(context: Context, screenWidth: Int, screenHeight: Int) : SurfaceView(context), Runnable {
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
    private fun loadLevel(level: String, hiroX: Float, hiroY: Float) {
        lm = null
        lm = LevelManager(context, viewport.pixelsPerMetreX,
                level, hiroX, hiroY)
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
                if (go.isActive) {

                    if (lm!!.isPlaying) {
                        go.update(fps)
                        HeroCollisions.checkForCollisions(go, lm!!, soundManager)
                        EnemyCollisions.checkForEnemyCollisions(go, lm!!)
                        SpellCollisions.checkForSpellCollisions(go)
                        setEnemyWayPoint(go)
                    }
                } else {
                        if (go is EnemyObject && go.isDead) {
                            go.update(fps)
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

    private fun setEnemyWayPoint(go: GameObject) {
        if (go.type == CharConstants.ENEMY) {
            val enemy = go as Enemy
            /*    Random random = new Random();
            int wayPointX = enemy.getStartLocationX() + (random.nextInt(6) - 3);
            int wayPointY = enemy.getStartLocationY() + (random.nextInt(6) - 3);
            for (GameObject gameObject : lm.gameObjects) {
                if ((int) gameObject.getWorldLocation().x == wayPointX || (int) gameObject.getWorldLocation().y == wayPointY) {
                    return;
                }
            }*/enemy.setWayPointHero(lm?.hero?.worldLocation!!)
            //enemy.setWayPoint(wayPointX, wayPointY);
            enemy.isUnderAttack(LevelManager.spellObject!!)
        } else if (go.type == CharConstants.ENEMY_TANKU) {
            val enemy = go as TankuNeko
            enemy.setWaypoint(lm?.hero?.worldLocation!!)
            enemy.isUnderAttack(LevelManager.spellObject!!)
        }
    }

    private fun draw() {
        if (ourHolder.surface.isValid) {
            canvas = ourHolder.lockCanvas()
            canvas?.drawColor(Color.GREEN)
            if (lm!!.level == MapNames.LEVEL_HOME) {
                canvas?.drawColor(Color.CYAN)
            }

            //drawBackground(0, -3);
            val toScreen2D = Rect()
            for (layer in -1..1) {
                for (go in lm!!.gameObjects) {
                    if (go.isVisible && go.worldLocation!!.z == layer) {
                        toScreen2D.set(viewport.worldToScreen(go.worldLocation!!.x,
                                go.worldLocation!!.y, go.width, go.height))
                        if (go.isAnimated) {
                            val flipper = Matrix()
                            if (go.facing == lm?.hero?.RIGHT) {
                                flipper.preScale(-1f, 1f)
                            } else if (go.facing == lm?.hero?.DOWN) {
                                flipper.preRotate(-90f)
                            } else if (go.facing == lm?.hero?.UP) {
                                flipper.preRotate(90f)
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
            //drawBackground(4, 0);
            debuggingInfo(debugging, canvas)
            if (!lm!!.hero!!.isTalking && lm?.isPlaying!! && lm?.level != MapNames.LEVEL_BATTLE) {
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
            if (bg.z < start && bg.z > stop) {
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

        //loadLevel("WorldMap", 3, 5);
        loadLevel(MapNames.LEVEL_FIRST, 1f, 1f)
        //loadLevel("LevelHome", 2, 8);
        //loadLevel("LevelBattle", 2, 2);
    }
}