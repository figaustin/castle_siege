package com.etsuni.siege.matches;

import org.bukkit.event.Listener;

public class KOTH extends Gamemode implements Listener {

    public KOTH() {
        this.gamemodeName = "King Of The Hill";
        this.maxPoints = 100;
        this.phases = 1;
        this.currentPhase = 1;
        this.length = 500;
    }
}
