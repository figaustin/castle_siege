package com.etsuni.siege.teams;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class SiegeTeam implements Listener {

    private ArrayList<Player> playersOnTeam;
    private String teamName;

    public SiegeTeam(ArrayList<Player> playersOnTeam, String teamName) {
        this.playersOnTeam = playersOnTeam;
        this.teamName = teamName;
    }

    public void addPlayerToTeam(Player player) {
        if(!this.getPlayersOnTeam().contains(player)) {
            this.getPlayersOnTeam().add(player);
        }
    }

    public void removePlayerFromTeam(Player player) {
        this.getPlayersOnTeam().remove(player);
    }


    //Handles Friendly Fire
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Player damagee = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if(sameTeam(damagee, damager)) {
            event.setCancelled(true);
        }
    }

    public Boolean sameTeam(Player p1, Player p2) {
        return this.getPlayersOnTeam().contains(p1) && this.getPlayersOnTeam().contains(p2);
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
}
