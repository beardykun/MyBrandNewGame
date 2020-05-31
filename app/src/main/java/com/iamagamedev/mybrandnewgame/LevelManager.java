package com.iamagamedev.mybrandnewgame;

import android.content.Context;
import android.graphics.Bitmap;

import com.iamagamedev.mybrandnewgame.Constants.CharConstants;
import com.iamagamedev.mybrandnewgame.Constants.MapNames;
import com.iamagamedev.mybrandnewgame.background.Background;
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject;
import com.iamagamedev.mybrandnewgame.gameObjects.Hero;
import com.iamagamedev.mybrandnewgame.gameObjects.enemys.Enemy;
import com.iamagamedev.mybrandnewgame.gameObjects.enemys.TankuNeko;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.EnemyShieldObject;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.EnemySpellObject;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.HeroSpell;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.ShieldSpellObject;
import com.iamagamedev.mybrandnewgame.gameObjects.spells.SpellObject;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Flore;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Forest;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Grass;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Home;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Mountain;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Town;
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.Wall;
import com.iamagamedev.mybrandnewgame.levels.LevelBattle;
import com.iamagamedev.mybrandnewgame.levels.LevelData;
import com.iamagamedev.mybrandnewgame.levels.LevelFirst;
import com.iamagamedev.mybrandnewgame.levels.LevelHome;
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
    SpellObject spellObject;
    int hiroIndex;
    int currentIndex = -1;

    private boolean playing = true;

    private LevelData levelData;
    public ArrayList<GameObject> gameObjects;
    ArrayList<Background> backgrounds;
    ArrayList<Integer> enemisList;

    private Bitmap[] bitmapsArray;
    private Bitmap[] badBitmapArray;

    public LevelManager(Context context, int pixelsPerMetre,
                        String level, float hiroX, float hiroY) {
        this.level = level;

        getLevelName(level);

        gameObjects = new ArrayList<>();
        enemisList = new ArrayList<>();

        bitmapsArray = new Bitmap[20];
        badBitmapArray = new Bitmap[20];


        loadMapData(context, pixelsPerMetre, hiroX, hiroY);
        //loadBackgrounds(context, pixelsPerMetre, screenWidth);

    }

    private void getLevelName(String levelName) {
        switch (levelName) {
            case MapNames.LEVEL_FIRST:
                levelData = new LevelFirst();
                break;
            case MapNames.LEVEL_HOME:
                levelData = new LevelHome();
                break;
            case MapNames.LEVEL_BATTLE:
                levelData = new LevelBattle();
                break;
            case MapNames.WORLD_MAP:
                levelData = new WorldMap();
                break;
        }
    }

    public Bitmap getBitmap(char blockType) {
        return bitmapsArray[getPosition(blockType)];
    }

    public int getBitmapIndex(char blockType) {
        return getPosition(blockType);
    }// End getBitmapIndex()

    public Bitmap getBadBitmap(char blockType) {
        return badBitmapArray[getPosition(blockType)];
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
                        case CharConstants.GRASS:
                            gameObjects.add(new Grass(j, i, c));
                            break;
                        case CharConstants.PLAYER:
                            gameObjects.add(new Hero(context, hiroX, hiroY, pixelsPerMetre));
                            hiroIndex = currentIndex;
                            hero = (Hero) gameObjects.get(hiroIndex);
                            break;
                        case CharConstants.ENEMY:
                            gameObjects.add(new Enemy(j, i, c, pixelsPerMetre));
                            enemisList.add(currentIndex);
                            break;
                        case CharConstants.ENEMY_TANKU:
                            gameObjects.add(new TankuNeko(j, i, c, pixelsPerMetre));
                            enemisList.add(currentIndex);
                            break;
                        case CharConstants.WALL:
                            gameObjects.add(new Wall(j, i, c));
                            break;
                        case CharConstants.HOME:
                            entranceIndex++;
                            gameObjects.add(new Home(j, i, c, levelData.locations.get(entranceIndex)));
                            break;
                        case CharConstants.FLORE:
                            gameObjects.add(new Flore(j, i, c));
                            break;
                        case CharConstants.MOUNTAIN:
                            gameObjects.add(new Mountain(j, i, c));
                            break;
                        case CharConstants.TOWN:
                            gameObjects.add(new Town(j, i, c));
                            break;
                        case CharConstants.FOREST:
                            gameObjects.add(new Forest(j, i, c));
                            break;
                        case CharConstants.SPELL:
                            gameObjects.add(HeroSpell.Companion.getInstance(j, i, c, pixelsPerMetre));
                            spellObject = (SpellObject) gameObjects.get(currentIndex);
                            break;
                        case CharConstants.SHIELD:
                            gameObjects.add(ShieldSpellObject.Companion.getInstance(j, i, c, pixelsPerMetre));
                            break;
                        case CharConstants.ENEMY_SPELL:
                            gameObjects.add(EnemySpellObject.Companion.getInstance(j, i, c, pixelsPerMetre));
                            break;
                        case CharConstants.ENEMY_SHIELD:
                            gameObjects.add(EnemyShieldObject.Companion.getInstance(j, i, c, pixelsPerMetre));
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

    private int getPosition(char type) {
        int index;
        switch (type) {
            case CharConstants.GRASS:
                index = 1;
                break;
            case CharConstants.PLAYER:
                index = 2;
                break;
            case CharConstants.WALL:
                index = 3;
                break;
            case CharConstants.ENEMY:
                index = 4;
                break;
            case CharConstants.HOME:
                index = 5;
                break;
            case CharConstants.FLORE:
                index = 6;
                break;
            case CharConstants.MOUNTAIN:
                index = 8;
                break;
            case CharConstants.TOWN:
                index = 9;
                break;
            case CharConstants.FOREST:
                index = 10;
                break;
            case CharConstants.SPELL:
                index = 11;
                break;
            case CharConstants.SHIELD:
                index = 12;
                break;
            case CharConstants.ENEMY_SPELL:
                index = 13;
                break;
            case CharConstants.ENEMY_SHIELD:
                index = 14;
                break;
            case CharConstants.ENEMY_TANKU:
                index = 15;
                break;
            default:
                index = 0;
                break;
        }
        return index;
    }
}
