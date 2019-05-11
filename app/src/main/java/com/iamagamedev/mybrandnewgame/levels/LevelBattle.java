package com.iamagamedev.mybrandnewgame.levels;

import java.util.ArrayList;

/**
 * Created by Михан on 06.06.2017.
 */

public class LevelBattle extends LevelData {

    public LevelBattle(){
        tiles = new ArrayList<>();
        this.tiles.add("..........");
        this.tiles.add("..........");
        this.tiles.add(".p......x.");
        this.tiles.add("wwwwwwwwww");
    }
}
