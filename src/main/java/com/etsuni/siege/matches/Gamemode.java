package com.etsuni.siege.matches;


import com.etsuni.siege.teams.SiegeTeam;

public class Gamemode {
    protected String gamemodeName;

    protected Integer maxPoints;

    protected Integer phases;

    protected Integer currentPhase;

    protected Integer length;

    protected SiegeTeam attack;
    protected SiegeTeam defense;

    public Gamemode() {
        this.attack = new SiegeTeam("Attack");
        this.defense = new SiegeTeam("Defense");
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

    public SiegeTeam getAttack() {
        return attack;
    }

    public void setAttack(SiegeTeam attack) {
        this.attack = attack;
    }

    public SiegeTeam getDefense() {
        return defense;
    }

    public void setDefense(SiegeTeam defense) {
        this.defense = defense;
    }

    public class TestingThis {

        private final String idk;
        public TestingThis() {
            this.idk = "idk";
        }

        public String getIdk() {
            return idk;
        }
    }
}
