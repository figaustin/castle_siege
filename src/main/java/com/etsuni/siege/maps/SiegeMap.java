package com.etsuni.siege.maps;


import com.etsuni.siege.matches.Gamemode;

public class SiegeMap {

    private String name;
    private Gamemode gamemode;
    private Integer time;

    public SiegeMap(String name, Gamemode gamemode, Integer time) {
        this.name = name;
        this.gamemode = gamemode;
        this.time = time;
    }




}
