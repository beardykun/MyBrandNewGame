package com.iamagamedev.mybrandnewgame.levels

import com.iamagamedev.mybrandnewgame.background.BackgroundData
import java.util.*

/**
 * Created by Михан on 24.05.2017.
 */
open class LevelData {
    @JvmField
    var tiles: ArrayList<String>? = null
    var backgroundDataList: ArrayList<BackgroundData>? = null
    @JvmField
    var locations: ArrayList<Location>? = null
}