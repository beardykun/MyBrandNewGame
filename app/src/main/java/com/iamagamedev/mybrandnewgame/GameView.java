package com.iamagamedev.mybrandnewgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.iamagamedev.mybrandnewgame.Constants.CharConstants;
import com.iamagamedev.mybrandnewgame.Constants.MapNames;
import com.iamagamedev.mybrandnewgame.background.Background;
import com.iamagamedev.mybrandnewgame.collisions.EnemyCollisions;
import com.iamagamedev.mybrandnewgame.collisions.HeroCollisions;
import com.iamagamedev.mybrandnewgame.gameObjects.EnemyObject;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;
import com.iamagamedev.mybrandnewgame.gameObjects.enemys.Enemy;
import com.iamagamedev.mybrandnewgame.gameObjects.enemys.TankuNeko;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Home;
import com.iamagamedev.mybrandnewgame.levels.Location;

import java.util.Random;


/**
 * Created by Михан on 17.05.2017.
 */

public class GameView extends SurfaceView implements Runnable {

    volatile boolean running;
    private Thread gameThread = null;

    private InputController ic;
    private SoundManager soundManager;
    private LevelManager lm;
    private Viewport viewport;

    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder ourHolder;

    private Context context;

    long startFrameTime;
    long timeThisFrame;
    long fps;
    private boolean debugging = true;
    public static boolean showTalkButton;

    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);

        this.context = context;
        ourHolder = getHolder();
        paint = new Paint();
        soundManager = SoundManager.getInstance();
        soundManager.loadSound();

        viewport = new Viewport(screenWidth, screenHeight);

        //loadLevel("WorldMap", 3, 5);
        loadLevel(MapNames.LEVEL_FIRST, 1, 1);
        //loadLevel("LevelHome", 2, 8);
        //loadLevel("LevelBattle", 2, 2);
    }

    private void loadLevel(String level, float hiroX, float hiroY) {
        lm = null;

        lm = new LevelManager(context, viewport.getPixelsPerMetreX(),
                level, hiroX, hiroY);
        ic = new InputController(viewport.getScreenWidth(), viewport.getScreenHeight());

        viewport.setWorldCentre(lm.gameObjects.get(lm.hiroIndex).getWorldLocation().x,
                lm.gameObjects.get(lm.hiroIndex).getWorldLocation().y);
    }

    @Override
    public void run() {
        while (running) {
            startFrameTime = System.currentTimeMillis();
            update();
            draw();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    private void update() {
        for (GameObject go : lm.gameObjects) {
            if (go.isTalking()) return;
            if (go.isActive()) {
                if (!viewport.clipObjects(go.getWorldLocation().x,
                        go.getWorldLocation().y,
                        go.getWidth(), go.getHeight())) {
                    go.setVisible(true);

                    if (lm.isPlaying()) {
                        go.update(fps);
                        HeroCollisions.checkForCollisions(go, lm, soundManager);
                        EnemyCollisions.checkForEnemyCollisions(go, lm);

                        setEnemyWayPoint(go);
                    }

                } else {
                    go.setVisible(false);
                }
            }
            if (lm.isPlaying()) {
                viewport.setWorldCentre(lm.gameObjects.get(lm.hiroIndex).getWorldLocation().x,
                        lm.gameObjects.get(lm.hiroIndex).getWorldLocation().y);
            }
        }
    }

    private void setEnemyWayPoint(GameObject go) {
        if (go.getType() == CharConstants.ENEMY) {
            Enemy enemy = (Enemy) go;
        /*    Random random = new Random();
            int wayPointX = enemy.getStartLocationX() + (random.nextInt(6) - 3);
            int wayPointY = enemy.getStartLocationY() + (random.nextInt(6) - 3);
            for (GameObject gameObject : lm.gameObjects) {
                if ((int) gameObject.getWorldLocation().x == wayPointX || (int) gameObject.getWorldLocation().y == wayPointY) {
                    return;
                }
            }*/
            enemy.setWayPointHero(lm.hero.getWorldLocation());
            //enemy.setWayPoint(wayPointX, wayPointY);
            enemy.isUnderAttack(lm.spellObject);
        } else if (go.getType() == CharConstants.ENEMY_TANKU) {
            TankuNeko enemy = (TankuNeko) go;
            enemy.setWaypoint(lm.hero.getWorldLocation());
            enemy.isUnderAttack(lm.spellObject);
        }
    }

    private void draw() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.GREEN);
            if (lm.level.equals(MapNames.LEVEL_HOME)) {
                canvas.drawColor(Color.CYAN);
            }

            //drawBackground(0, -3);

            Rect toScreen2D = new Rect();

            for (int layer = -1; layer <= 1; layer++) {
                for (GameObject go : lm.gameObjects) {
                    if (go.isVisible() && go.getWorldLocation().z == layer) {
                        toScreen2D.set(viewport.worldToScreen(go.getWorldLocation().x,
                                go.getWorldLocation().y, go.getWidth(), go.getHeight()));

                        if (go.isAnimated()) {
                            Matrix flipper = new Matrix();
                            if (go.getFacing() == lm.hero.RIGHT) {
                                flipper.preScale(-1, 1);
                            } else if (go.getFacing() == lm.hero.DOWN) {
                                flipper.preRotate(-90);
                            } else if (go.getFacing() == lm.hero.UP) {
                                flipper.preRotate(90);
                            }
                            Rect r = go.getRectToDraw(System.currentTimeMillis());
                            if (go.isActive()) {
                                Bitmap b = Bitmap.createBitmap(lm
                                                .getBitmap(go.getType()),
                                        r.left, r.top, r.width(), r.height(), flipper, true);
                                canvas.drawBitmap(b, toScreen2D.left, toScreen2D.top, paint);
                            } else {
                                Bitmap b = Bitmap.createBitmap(lm
                                                .getBadBitmap(go.getType()),
                                        r.left, r.top, r.width(), r.height(), flipper, true);
                                canvas.drawBitmap(b, toScreen2D.left, toScreen2D.top, paint);
                            }
                        } else {
                            if (go.isActive()) {
                                canvas.drawBitmap(lm.getBitmap(go.getType()),
                                        toScreen2D.left, toScreen2D.top, paint);
                            } else {
                                canvas.drawBitmap(lm.getBadBitmap(go.getType()),
                                        toScreen2D.left, toScreen2D.top, paint);
                            }
                        }
                        if (lm.hero.isTalking() && showTalkButton) {
                            drawDialog(canvas);
                        }
                    }
                }
            }
            //drawBackground(4, 0);
            debuggingInfo(debugging, canvas);

            if (!lm.hero.isTalking() && lm.isPlaying() && !lm.level.equals(MapNames.LEVEL_BATTLE)) {
                paint.setColor(Color.BLACK);

                Rect moveDirectionLeft = ic.getMoveDirectionLeft();
                canvas.drawRect(moveDirectionLeft, paint);
                Rect moveDirectionRight = ic.getMoveDirectionRight();
                canvas.drawRect(moveDirectionRight, paint);
                Rect moveDirectionDown = ic.getMoveDirectionDown();
                canvas.drawRect(moveDirectionDown, paint);
                Rect moveDirectionUp = ic.getMoveDirectionUp();
                canvas.drawRect(moveDirectionUp, paint);
                canvas.drawRect(ic.getFireButton(), paint);
                if (showTalkButton)
                    canvas.drawRect(ic.getDialogButton(), paint);
            }

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawBackground(int start, int stop) {

        Rect fromRect1 = new Rect();
        Rect toRect1 = new Rect();
        Rect fromRect2 = new Rect();
        Rect toRect2 = new Rect();

        for (Background bg : lm.backgrounds) {

            if (bg.z < start && bg.z > stop) {
                // Is this layer in the viewport?
                // Clip anything off-screen
                if (!viewport.clipObjects(-1, bg.y, 1000, bg.height)) {

                    float floatstartY = ((viewport.getScreenCentreY() - ((viewport.getCurrentViewportWorldCentreY() - bg.y) * viewport.getPixelsPerMetreY())));
                    int startY = (int) floatstartY;

                    float floatendY = ((viewport.getScreenCentreY() - ((viewport.getCurrentViewportWorldCentreY() - bg.endY) * viewport.getPixelsPerMetreY())));
                    int endY = (int) floatendY;

                    //define what portion of bitmaps to capture and what coordinates to draw them at
                    fromRect1 = new Rect(0, 0, bg.width - bg.xClip, bg.height);
                    toRect1 = new Rect(bg.xClip, startY, bg.width, endY);

                    fromRect2 = new Rect(bg.width - bg.xClip, 0, bg.width, bg.height);
                    toRect2 = new Rect(0, startY, bg.xClip, endY);
                }

                //draw backgrounds
                if (!bg.reversedFirst) {

                    canvas.drawBitmap(bg.bitmap, fromRect1, toRect1, paint);
                    canvas.drawBitmap(bg.bitmapReversed, fromRect2, toRect2, paint);
                } else {
                    canvas.drawBitmap(bg.bitmap, fromRect2, toRect2, paint);
                    canvas.drawBitmap(bg.bitmapReversed, fromRect1, toRect1, paint);
                }


                bg.xClip -= lm.hero.getxVelocity() / (20 / bg.speed);
                if (bg.xClip >= bg.width) {
                    bg.xClip = 0;
                    bg.reversedFirst = !bg.reversedFirst;
                } else if (bg.xClip <= 0) {
                    bg.xClip = bg.width;
                    bg.reversedFirst = !bg.reversedFirst;

                }
            }
        }
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ic != null) {
            ic.handleIntent(event, lm, soundManager, viewport);
        }
        return true;
    }

    private void debuggingInfo(boolean debugging, Canvas canvas) {
        if (debugging) {
            paint.setTextSize(26);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(Color.RED);
            canvas.drawText("fps:" + fps, 10, 60, paint);

            canvas.drawText("num objects:" +
                    lm.gameObjects.size(), 10, 80, paint);

            canvas.drawText("num clipped:" +
                    viewport.getNumClipped(), 10, 100, paint);

            canvas.drawText("playerX:" +
                            lm.gameObjects.get(lm.hiroIndex).
                                    getWorldLocation().x,
                    10, 120, paint);
            canvas.drawText("playerY:" +
                            lm.gameObjects.get(lm.hiroIndex).

                                    getWorldLocation().y,
                    10, 140, paint);
            //for reset the number of clipped objects each frame
            viewport.resetNumClipped();
        }// End if(debugging)
    }

    private void drawDialog(Canvas canvas) {
        paint.setTextSize(70);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLACK);
        canvas.drawRect(ic.getGameDialogRect(), paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(ic.getDialog(HeroCollisions.getCollidedObject()), ic.getGameDialogRect().left + 30,
                ic.getGameDialogRect().top + 100, paint);
    }
}
