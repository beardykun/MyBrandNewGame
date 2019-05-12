package com.iamagamedev.mybrandnewgame;

import android.content.Context;
import android.graphics.Bitmap;

import com.iamagamedev.mybrandnewgame.background.Background;
import com.iamagamedev.mybrandnewgame.gameObjects.enemys.Enemy;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Flore;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Forest;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Grass;
import com.iamagamedev.mybrandnewgame.gameObjects.Hero;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Home;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Mountain;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Town;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Wall;
import com.iamagamedev.mybrandnewgame.levels.LevelBattle;
import com.iamagamedev.mybrandnewgame.levels.LevelHome;
import com.iamagamedev.mybrandnewgame.levels.LevelData;
import com.iamagamedev.mybrandnewgame.levels.LevelFirst;
import com.iamagamedev.mybrandnewgame.levels.WorldMap;

import java.util.ArrayList;

/**
 * Created by Михан on 24.05.2017.
 */

public class LevelManager {

    String level;
    int mapWidth;
    int mapHeight;

    Hero hero;
    int hiroIndex;
    int currentIndex = -1;

    private boolean playing;

    private LevelData levelData;
    public ArrayList<GameObject> gameObjects;
    ArrayList<Background> backgrounds;
    ArrayList<Integer> enemisList;

    Bitmap[] bitmapsArray;
    private Bitmap[] badBitmapArray;

    public LevelManager(Context context, int pixelsPerMetre,
                        String level, float hiroX, float hiroY) {
        this.level = level;

        switch (level) {
            case "LevelFirst":
                levelData = new LevelFirst();
                break;
            case "LevelHome":
                levelData = new LevelHome();
                break;
            case "LevelBattle":
                levelData = new LevelBattle();
                break;
            case "WorldMap":
                levelData = new WorldMap();
                break;
        }

        gameObjects = new ArrayList<>();
        enemisList = new ArrayList<>();

        bitmapsArray = new Bitmap[11];
        badBitmapArray = new Bitmap[11];


        loadMapData(context, pixelsPerMetre, hiroX, hiroY);
        //loadBackgrounds(context, pixelsPerMetre, screenWidth);

    }

    public Bitmap getBitmap(char blockType) {
        int index;
        switch (blockType) {
            case ' ':
                index = 0;
                break;
            case 'g':
                index = 1;
                break;
            case 'p':
                index = 2;
                break;
            case 'w':
                index = 3;
                break;
            case 'e':
                index = 4;
                break;
            case 'h':
                index = 5;
                break;
            case '2':
                index = 6;
                break;
            case 'm':
                index = 8;
                break;
            case 't':
                index = 9;
                break;
            case 'f':
                index = 10;
                break;
            default:
                index = 0;
                break;
        }
        return bitmapsArray[index];
    }

    public int getBitmapIndex(char blockType) {
        int index;
        switch (blockType) {
            case ' ':
                index = 0;
                break;
            case 'g':
                index = 1;
                break;
            case 'p':
                index = 2;
                break;
            case 'w':
                index = 3;
                break;
            case 'e':
                index = 4;
                break;
            case 'h':
                index = 5;
                break;
            case '2':
                index = 6;
                break;
            case 'm':
                index = 8;
                break;
            case 't':
                index = 9;
                break;
            case 'f':
                index = 10;
                break;
            case '.':
                index = 11;
                break;
            default:
                index = 0;
                break;

        }// End switch
        return index;
    }// End getBitmapIndex()

    public Bitmap getBadBitmap(char blockType) {
        int index;
        switch (blockType) {
            case ' ':
                index = 0;
                break;
            case 'g':
                index = 1;
                break;
            case 'p':
                index = 2;
                break;
            case 'w':
                index = 3;
                break;
            case 'e':
                index = 4;
                break;
            case 'h':
                index = 5;
                break;
            case '2':
                index = 6;
                break;
            case 'm':
                index = 8;
                break;
            case 't':
                index = 9;
                break;
            case 'f':
                index = 10;
                break;
            default:
                index = 0;
                break;
        }
        return badBitmapArray[index];
    }

    public void loadMapData(Context context, int pixelsPerMetre,
                            float hiroX, float hiroY) {
        char c;
        int entranceIndex = -1;

        mapHeight = levelData.tiles.size();
        mapWidth = levelData.tiles.get(0).length();

        for (int i = 0; i < levelData.tiles.size(); i++) {
            for (int j = 0; j < levelData.tiles.get(i).length(); j++) {
                c = levelData.tiles.get(i).charAt(j);

                if (c != ' ') {
                    currentIndex++;
                    switch (c) {
                        case 'g':
                            gameObjects.add(new Grass(j, i, c));
                            break;
                        case 'p':
                            gameObjects.add(new Hero(context, hiroX, hiroY, pixelsPerMetre));
                            hiroIndex = currentIndex;
                            hero = (Hero) gameObjects.get(hiroIndex);
                            break;
                        case 'e':
                            gameObjects.add(new Enemy(j, i, c));
                            enemisList.add(currentIndex);
                            break;
                        case 'w':
                            gameObjects.add(new Wall(j, i, c));
                            break;
                        case 'h':
                            entranceIndex++;
                            gameObjects.add(new Home(j, i, c, levelData.locations.get(entranceIndex)));
                            break;
                        case '2':
                            gameObjects.add(new Flore(j, i, c));
                            break;
                        case 'm':
                            gameObjects.add(new Mountain(j, i, c));
                            break;
                        case 't':
                            gameObjects.add(new Town(j, i, c));
                            break;
                        case 'f':
                            gameObjects.add(new Forest(j, i, c));
                            break;
                        case '.':
                            gameObjects.add(new SpellObject(j, i, c));
                            break;

                    }
                    if (bitmapsArray[getBitmapIndex(c)] == null) {
                        bitmapsArray[getBitmapIndex(c)] =
                                gameObjects.get(currentIndex).prepareBitmap(context,
                                        gameObjects.get(currentIndex).getBitmapName(), pixelsPerMetre);
                    }
                    if (badBitmapArray[getBitmapIndex(c)] == null) {
                        badBitmapArray[getBitmapIndex(c)] =
                                gameObjects.get(currentIndex).prepareBitmap(context,
                                        gameObjects.get(currentIndex).getBadBitmapName(), pixelsPerMetre);
                    }
                }
            }
        }
    }

    public void switchPlayingStatus() {
        playing = !playing;
    }

    public boolean isPlaying() {
        return playing;
    }

    /*private void loadBackgrounds(Context context, int pixelsPerMetre, int screenWidth) {
        backgrounds = new ArrayList<>();
        for (BackgroundData bgData : levelData.backgroundDataList) {
            backgrounds.add(new Background(context, pixelsPerMetre, screenWidth, bgData));
        }
    }*/
}
