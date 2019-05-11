package com.iamagamedev.mybrandnewgame.levels;

import java.util.ArrayList;

/**
 * Created by Михан on 02.06.2017.
 */

public class LevelHome extends LevelData{

    public LevelHome(){
        tiles = new ArrayList<>();
        this.tiles.add("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        this.tiles.add("w........w......................w");
        this.tiles.add("w........w......................w");
        this.tiles.add("w........w......................w");
        this.tiles.add("w...............................w");
        this.tiles.add("w........w......................w");
        this.tiles.add("w........w......................w");
        this.tiles.add("wwwwwwwwwwwwwwwwwwww.wwwwwwwwwwww");
        this.tiles.add("w2..2..2..2....w................w");
        this.tiles.add("w..............w................w");
        this.tiles.add("w..............w................w");
        this.tiles.add("w...............................w");
        this.tiles.add("w..............w................w");
        this.tiles.add("w..............w................w");
        this.tiles.add("wwwwwwpwwwwwwwwwwwwwwwwwwwwwwwwww");
        this.tiles.add(".................................");



        this.locations = new ArrayList<>();
        this.locations.add(new Location("LevelFirst", 32f, 5f));
    }
}
