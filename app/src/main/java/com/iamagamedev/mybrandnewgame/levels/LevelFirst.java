package com.iamagamedev.mybrandnewgame.levels;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Михан on 24.05.2017.
 */

public class LevelFirst extends LevelData {
    public LevelFirst() {
        tiles = new ArrayList<String>();
                      //012345678901234567890123456789012345678901234567890
        this.tiles.add("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        this.tiles.add("w                                                                          w"); //1
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");//10
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("w                                                                          w");
        this.tiles.add("wwwwwwwwwwwwwwwwwwwwpwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");

        //backgroundDataList = new ArrayList<>();
        locations = new ArrayList<>();
        locations.add(new Location("LevelHome", 6f, 14f));
        Log.i("tag", locations.get(0).x + "");

        //this.backgroundDataList.add(new BackgroundData("mountain", true, -1, 3, 18, 10, 15));
        //this.backgroundDataList.add(new BackgroundData("forest", true, 1, 20, 24, 24, 4));
    }
}
