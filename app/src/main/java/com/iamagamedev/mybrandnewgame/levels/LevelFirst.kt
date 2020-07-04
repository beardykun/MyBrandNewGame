package com.iamagamedev.mybrandnewgame.levels

import com.iamagamedev.mybrandnewgame.background.BackgroundData
import java.util.*

/**
 * Created by Михан on 24.05.2017.
 */
class LevelFirst : LevelData() {
    init {
        tiles = ArrayList()
                  //          1         2         3         4         5         6         7
                  //0123456789012345678901234567890123456789012345678901234567890123456789012345
        tiles?.add("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww                            ")
        tiles?.add("w       w       w       w       w        w      w                          w                            ") //1
        tiles?.add("w   p   w   a   w   a   w   a   w    a   w      a                          w                            ")
        tiles?.add("w       w       w       w       w        w      w                          w                            ")
        tiles?.add("www wwwwwwww wwwwwww wwwwwww wwwwwww  wwww      w                          w                            ")
        tiles?.add("w                                               wwwwwwwwwwwwwwwwwwwwwwwwwwww                           .")
        tiles?.add("wwww wwwwwww wwwwwww wwwwwww wwwwwww  wwww      wl                         w                           z")
        tiles?.add("w       w       w       w       w        w      w                          w                           s")
        tiles?.add("w   a   w   a   w   a   w   a   w    a   w      w                          w                           1")
        tiles?.add("w       w       w       w       w        w      w                          w                            ")
        tiles?.add("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww      w                          w                            ") //10
        tiles?.add("w                                                          e               w                            ")
        tiles?.add("w                                                                          w                            ")
        tiles?.add("wwwwwww  wwwwwwwwwwwwwww  wwwwwwwwwwwwww  wwwwwww                          w                            ")
        tiles?.add("w               w               w               w                          w                            ")
        tiles?.add("w               w               w               w                          w                            ")
        tiles?.add("w       a       w        a      w       a       w                          w                            ")
        tiles?.add("w               w               w               w                          w                            ")
        tiles?.add("w               w               w               w                          w                            ")
        tiles?.add("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww                            ")

        //backgroundDataList = new ArrayList<>();
        //locations = ArrayList()
        //locations?.add(Location("LevelHome", 6f, 14f))
        this.backgroundDataList = arrayListOf()
        this.backgroundDataList?.add(BackgroundData("mountain", true, -1, -3f, 10f, 10f, 15))
        this.backgroundDataList?.add(BackgroundData("forest", true, 1, 20f, 24f, 24f, 4))
    }
}