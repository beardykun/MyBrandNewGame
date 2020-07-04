package com.iamagamedev.mybrandnewgame.levels

import com.iamagamedev.mybrandnewgame.background.BackgroundData
import java.util.*

/**
 * Created by Михан on 06.06.2017.
 */
class LevelBattle : LevelData() {
    init {
        tiles = ArrayList()
        tiles?.also {
            it.add("                                  ")
            it.add("                                 .")
            it.add("         a                       z")
            it.add("wwwwwwwwwwwwww                   1")
            it.add("wwwwwwwwwwwwww                   s")
            it.add("wwwwwwwwwwwwww                    ")
            it.add("pwwwwwwwwwwwww                    ")
        }

        this.backgroundDataList = arrayListOf()
        this.backgroundDataList?.add(BackgroundData("mountain", true, -1, -3f, 10f, 10f, 15))
        this.backgroundDataList?.add(BackgroundData("forest", true, 1, 20f, 24f, 24f, 4))
    }
}