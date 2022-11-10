package com.etsuni.siege.teams;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.UUID;

public class SiegeTeam implements Listener {

    private ArrayList<Player> playersOnTeam;
    private String teamName;

    private Integer points;

    public SiegeTeam(String teamName) {
        this.playersOnTeam = new ArrayList<>();
        this.teamName = teamName;
        this.points = 0;
    }

    public void addPlayerToTeam(Player player, SiegeTeam siegeTeam) {
        if(!siegeTeam.getPlayersOnTeam().contains(player)) {
            ArrayList<Player> temp = siegeTeam.getPlayersOnTeam();
            temp.add(player);
            siegeTeam.setPlayersOnTeam(temp);
        }
    }

    public void removePlayerFromTeam(Player player, SiegeTeam siegeTeam) {
        ArrayList<Player> temp = siegeTeam.getPlayersOnTeam();
        temp.remove(player);
        siegeTeam.setPlayersOnTeam(temp);
    }

    public Boolean sameTeam(Player p1, Player p2, SiegeTeam siegeTeam) {
        return siegeTeam.getPlayersOnTeam().contains(p1) && siegeTeam.getPlayersOnTeam().contains(p2);
    }

    public Boolean isPlayerOnTeam(Player player) {
        ArrayList<Player> list = this.playersOnTeam;
        UUID playerUUID = player.getUniqueId();
        for(Player p : list) {
            if(p.getUniqueId().equals(playerUUID)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Player> getPlayersOnTeam() {
        return playersOnTeam;
    }

    public void setPlayersOnTeam(ArrayList<Player> playersOnTeam) {
        this.playersOnTeam = playersOnTeam;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
