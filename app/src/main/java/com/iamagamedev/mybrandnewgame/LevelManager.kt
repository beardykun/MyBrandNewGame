package com.iamagamedev.mybrandnewgame

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.iamagamedev.mybrandnewgame.background.Background
import com.iamagamedev.mybrandnewgame.constants.CharConstants
import com.iamagamedev.mybrandnewgame.constants.MapNames
import com.iamagamedev.mybrandnewgame.gameObjects.EnemyObject
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
class LevelManager(context: Context?, pixelsPerMetre: Int, screenWidth: Int,
                   var level: String, heroX: Float, heroY: Float) {
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
    var enemiesList: ArrayList<Int>
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
        levelData?.also {
            mapHeight = it.tiles!!.size
            mapWidth = it.tiles!![0].length
        }
        for (i in levelData?.tiles!!.indices) {
            for (j in levelData?.tiles!![i].indices) {
                c = levelData?.tiles!![i][j]
                if (c != ' ') {
                    currentIndex++
                    when (c) {
                        CharConstants.GRASS -> gameObjects.add(Grass(j.toFloat(), i.toFloat(), c))
                        CharConstants.FRESCO -> gameObjects.add(Fresco(j.toFloat(), i.toFloat(), c))
                        CharConstants.PLAYER -> {
                            gameObjects.add(Hero(context, heroX, heroY, pixelsPerMetre))
                            heroIndex = currentIndex
                            hero = gameObjects[heroIndex] as Hero
                        }
                        CharConstants.ENEMY -> {
                            gameObjects.add(Enemy(j.toFloat(), i.toFloat(), c, pixelsPerMetre))
                            enemiesList.add(currentIndex)
                        }
                        CharConstants.ENEMY_TANKU -> {
                            gameObjects.add(TankuNeko(j.toFloat(), i.toFloat(), c, pixelsPerMetre))
                            enemiesList.add(currentIndex)
                        }
                        CharConstants.WALL -> gameObjects.add(Wall(j.toFloat(), i.toFloat(), c))
                        CharConstants.HOME -> {
                            entranceIndex++
                            gameObjects.add(Home(j.toFloat(), i.toFloat(), c, levelData!!.locations!![entranceIndex]))
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

    private fun loadBackgrounds(context: Context, pixelsPerMetre: Int, screenWidth: Int) {
        backgrounds = arrayListOf()
        for (bgData in levelData?.backgroundDataList!!) {
            backgrounds?.add(Background(context, pixelsPerMetre, screenWidth, bgData));
        }
    }

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
            CharConstants.FRESCO -> 16
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
        enemiesList = ArrayList()
        bitmapsArray = arrayOfNulls(20)
        badBitmapArray = arrayOfNulls(20)
        loadMapData(context, pixelsPerMetre, heroX, heroY)
        loadBackgrounds(context!!, pixelsPerMetre, screenWidth)
        setWayPoints()
    }

    private fun setWayPoints() {
        for (enemy in gameObjects) {
            if (enemy.type == 'a' || enemy.type == 'e') {
                var startTileIndex = -1
                var waypointX1 = -1f
                var waypointX2 = -1f
                for (tile in gameObjects) {
                    startTileIndex++
                    if (tile.worldLocation!!.x == enemy.worldLocation!!.x) {
                        for (i in 0..4) { // left for loop
                            if (!gameObjects[startTileIndex - i].isActive) {
                                //set the left waypoint
                                waypointX1 = gameObjects[startTileIndex - (i + 1)].worldLocation!!.x
                                Log.d("set x1 = ", "" + waypointX1)
                                break // Leave left for loop
                            } else {
                                //set to max 5 tiles as no non traversible tile found
                                waypointX1 = gameObjects[startTileIndex - 3].worldLocation!!.x
                            }
                        } // end get left waypoint
                        for (i in 0..4) { // right for loop
                            if (!gameObjects[startTileIndex + i].isActive) {
                                //set the right waypoint
                                waypointX2 = gameObjects[startTileIndex + (i - 1)].worldLocation!!.x
                                //Log.d("set x2 = ", "" + waypointX2);
                                break // Leave right for loop
                            } else {
                                //set to max 5 tiles away
                                waypointX2 = gameObjects[startTileIndex + 3].worldLocation!!.x
                            }
                        } // end get right waypoint
                        (enemy as EnemyObject).setWaypoints(waypointX1, waypointX2)
                        Log.i("after fors x1 = ", "" + waypointX1);
                    }
                }
            }
        }
    }
}