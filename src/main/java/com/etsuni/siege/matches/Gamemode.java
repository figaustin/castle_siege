package com.etsuni.siege.matches;


import com.etsuni.siege.teams.SiegeTeam;

import java.util.HashMap;

public class Gamemode {

    protected String gamemodeName;

    protected Integer maxPoints;

    protected Integer phases;

    protected Integer currentPhase;

    protected Integer length;

    public Gamemode () {
        this.maxPoints = 75;
    }

    public String getGamemodeName() {
        return gamemodeName;
    }

    public void setGamemodeName(String gamemodeName) {
        this.gamemodeName = gamemodeName;
    }

    public Integer getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Integer getPhases() {
        return phases;
    }

    public void setPhases(Integer phases) {
        this.phases = phases;
    }

    public Integer getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Integer currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
