package com.iamagamedev.mybrandnewgame

import android.content.Context
import android.graphics.Bitmap
import com.iamagamedev.mybrandnewgame.Constants.CharConstants
import com.iamagamedev.mybrandnewgame.Constants.MapNames
import com.iamagamedev.mybrandnewgame.background.Background
import com.iamagamedev.mybrandnewgame.gameObjects.GameObject
import com.iamagamedev.mybrandnewgame.gameObjects.Hero
import com.iamagamedev.mybrandnewgame.gameObjects.enemys.Enemy
import com.iamagamedev.mybrandnewgame.gameObjects.enemys.TankuNeko
import com.iamagamedev.mybrandnewgame.gameObjects.spells.*
import com.iamagamedev.mybrandnewgame.gameObjects.worldObjects.*
import com.iamagamedev.mybrandnewgame.levels.*
import java.util.*

/**
 * Created by Михан on 24.05.2017.
 */
class LevelManager(context: Context?, pixelsPerMetre: Int,
                   var level: String, hiroX: Float, hiroY: Float) {
    private var mapWidth = 0
    private var mapHeight = 0
    @JvmField
    var hero: Hero? = null
    var heroIndex = 0
    var currentIndex = -1
    var isPlaying = true
        private set
    private var levelData: LevelData? = null
    @JvmField
    var gameObjects: ArrayList<GameObject>
    var backgrounds: ArrayList<Background>? = null
    @JvmField
    var enemisList: ArrayList<Int>
    private val bitmapsArray: Array<Bitmap?>
    private val badBitmapArray: Array<Bitmap?>
    private fun getLevelName(levelName: String) {
        when (levelName) {
            MapNames.LEVEL_FIRST -> levelData = LevelFirst()
            MapNames.LEVEL_HOME -> levelData = LevelHome()
            MapNames.LEVEL_BATTLE -> levelData = LevelBattle()
            MapNames.WORLD_MAP -> levelData = WorldMap()
        }
    }

    fun getBitmap(blockType: Char): Bitmap? {
        return bitmapsArray[getPosition(blockType)]
    }

    private fun getBitmapIndex(blockType: Char): Int {
        return getPosition(blockType)
    } // End getBitmapIndex()

    fun getBadBitmap(blockType: Char): Bitmap? {
        return badBitmapArray[getPosition(blockType)]
    }

    private fun loadMapData(context: Context?, pixelsPerMetre: Int,
                            heroX: Float, heroY: Float) {
        var c: Char
        var entranceIndex = -1
        mapHeight = levelData!!.tiles.size
        mapWidth = levelData!!.tiles[0].length
        for (i in levelData!!.tiles.indices) {
            for (j in levelData!!.tiles[i].indices) {
                c = levelData!!.tiles[i][j]
                if (c != ' ') {
                    currentIndex++
                    when (c) {
                        CharConstants.GRASS -> gameObjects.add(Grass(j.toFloat(), i.toFloat(), c))
                        CharConstants.PLAYER -> {
                            gameObjects.add(Hero(context, heroX, heroY, pixelsPerMetre))
                            heroIndex = currentIndex
                            hero = gameObjects[heroIndex] as Hero
                        }
                        CharConstants.ENEMY -> {
                            gameObjects.add(Enemy(j.toFloat(), i.toFloat(), c, pixelsPerMetre))
                            enemisList.add(currentIndex)
                        }
                        CharConstants.ENEMY_TANKU -> {
                            gameObjects.add(TankuNeko(j.toFloat(), i.toFloat(), c, pixelsPerMetre))
                            enemisList.add(currentIndex)
                        }
                        CharConstants.WALL -> gameObjects.add(Wall(j.toFloat(), i.toFloat(), c))
                        CharConstants.HOME -> {
                            entranceIndex++
                            gameObjects.add(Home(j.toFloat(), i.toFloat(), c, levelData!!.locations[entranceIndex]))
                        }
                        CharConstants.FLORE -> gameObjects.add(Flore(j.toFloat(), i.toFloat(), c))
                        CharConstants.MOUNTAIN -> gameObjects.add(Mountain(j.toFloat(), i.toFloat(), c))
                        CharConstants.TOWN -> gameObjects.add(Town(j.toFloat(), i.toFloat(), c))
                        CharConstants.FOREST -> gameObjects.add(Forest(j.toFloat(), i.toFloat(), c))
                        CharConstants.SPELL -> {
                            gameObjects.add(HeroSpell(j.toFloat(), i.toFloat(), c, pixelsPerMetre))
                            spellObject = gameObjects[currentIndex] as HeroSpell
                        }
                        CharConstants.SHIELD -> {
                            gameObjects.add(ShieldObject(j.toFloat(), i.toFloat(), c, pixelsPerMetre))
                            shieldObject = gameObjects[currentIndex] as ShieldObject
                        }
                        CharConstants.ENEMY_SPELL -> {
                            gameObjects.add(EnemySpellObject(j.toFloat(), i.toFloat(), c, pixelsPerMetre))
                            enemySpellObject = gameObjects[currentIndex] as EnemySpellObject
                        }
                        CharConstants.ENEMY_SHIELD -> {
                            gameObjects.add(EnemyShieldObject(j.toFloat(), i.toFloat(), c, pixelsPerMetre))
                            enemyShieldObject = gameObjects[currentIndex] as EnemyShieldObject
                        }
                    }
                    if (bitmapsArray[getBitmapIndex(c)] == null) {
                        bitmapsArray[getBitmapIndex(c)] = gameObjects[currentIndex].prepareBitmap(context!!,
                                gameObjects[currentIndex].bitmapName, pixelsPerMetre)
                    }
                    if (badBitmapArray[getBitmapIndex(c)] == null) {
                        badBitmapArray[getBitmapIndex(c)] = gameObjects[currentIndex].prepareBitmap(context!!,
                                gameObjects[currentIndex].badBitmapName, pixelsPerMetre)
                    }
                }
            }
        }
    }

    fun switchPlayingStatus() {
        isPlaying = !isPlaying
    }

    /*private void loadBackgrounds(Context context, int pixelsPerMetre, int screenWidth) {
        backgrounds = new ArrayList<>();
        for (BackgroundData bgData : levelData.backgroundDataList) {
            backgrounds.add(new Background(context, pixelsPerMetre, screenWidth, bgData));
        }
    }*/
    private fun getPosition(type: Char): Int {
        return when (type) {
            CharConstants.GRASS -> 1
            CharConstants.PLAYER -> 2
            CharConstants.WALL -> 3
            CharConstants.ENEMY -> 4
            CharConstants.HOME -> 5
            CharConstants.FLORE -> 6
            CharConstants.MOUNTAIN -> 8
            CharConstants.TOWN -> 9
            CharConstants.FOREST -> 10
            CharConstants.SPELL -> 11
            CharConstants.SHIELD -> 12
            CharConstants.ENEMY_SPELL -> 13
            CharConstants.ENEMY_SHIELD -> 14
            CharConstants.ENEMY_TANKU -> 15
            else -> 0
        }
    }

    companion object {
        @JvmField
        var pixelsPerMetre = 0
        @JvmField
        var spellObject: SpellObject? = null
        var enemyShieldObject: EnemyShieldObject? = null
        @JvmField
        var shieldObject: ShieldObject? = null
        @JvmField
        var enemySpellObject: EnemySpellObject? = null
    }

    init {
        Companion.pixelsPerMetre = pixelsPerMetre
        getLevelName(level)
        gameObjects = ArrayList()
        enemisList = ArrayList()
        bitmapsArray = arrayOfNulls(20)
        badBitmapArray = arrayOfNulls(20)
        loadMapData(context, pixelsPerMetre, hiroX, hiroY)
        //loadBackgrounds(context, pixelsPerMetre, screenWidth);
    }
}