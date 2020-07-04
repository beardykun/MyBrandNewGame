package com.iamagamedev.mybrandnewgame.levels

import com.iamagamedev.mybrandnewgame.background.BackgroundData
import com.iamagamedev.mybrandnewgame.constants.MapNames
import java.util.*

/**
 * Created by Михан on 08.06.2017.
 */
class WorldMap : LevelData() {
    init {
        tiles = ArrayList()
                             //1         2         3         4         5         6         7         8
                   //012345678901234567890123456789012345678901234567890123456789012345678901234567890123
        tiles!!.add("pmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm") //0
        tiles!!.add("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm") //1
        tiles!!.add("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm") //2
        tiles!!.add("mmmmmmmmmmm     mmmmm                mmmm                                           ") //3
        tiles!!.add("   mmm            m   aaa             m                                             ") //4
        tiles!!.add("                                                                                    ") //5
        tiles!!.add("                             t                                                      ") //6
        tiles!!.add("                                                                                    ") //7
        tiles!!.add("              ffffffffffff                                                          ") //8
        tiles!!.add("              ffffffffffff                                                          ") //9
        tiles!!.add("              ffffffffffff                                                          ") //10
        tiles!!.add("              ffffffffffff                                                          ") //11
        tiles!!.add("              ffffffffffff                                                          ") //12
        tiles!!.add("                                                                                    ") //13
        tiles!!.add("                                                                                    ") //14
        tiles!!.add("                                                                                    ") //15
        tiles!!.add("                                                                                    ") //16
        tiles!!.add("                                                                                    ") //17
        tiles!!.add("                                                                                    ") //18
        tiles!!.add("                                                                                    ") //19
        tiles!!.add("                                                                                    ") //20
        tiles!!.add("                                                                                    ") //21
        tiles!!.add("                                                                                    ") //22
        tiles!!.add("                                                                                    ") //23
        tiles!!.add("                                                                                    ") //24
        tiles!!.add("                                                                                    ") //25
        tiles!!.add("                                                                                    ") //26
        tiles!!.add("                                                                                    ") //27
        tiles!!.add("                                                                                    ") //28
        tiles!!.add("                                                                                    ") //29
        tiles!!.add("                                                                                    ") //30
        tiles!!.add("                                                                                    ") //31
        locations = ArrayList()
        locations!!.add(Location(MapNames.LEVEL_FIRST, 32f, 5f))

        this.backgroundDataList = arrayListOf()
        this.backgroundDataList?.add(BackgroundData("mountain", true, -1, -3f, 10f, 10f, 15))
        this.backgroundDataList?.add(BackgroundData("forest", true, 1, 20f, 24f, 24f, 4))
    }
}