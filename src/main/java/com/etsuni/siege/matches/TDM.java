package com.etsuni.siege.matches;

import com.etsuni.siege.teams.SiegeTeam;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.ArrayList;


public class TDM extends Gamemode implements Listener {


    public TDM() {
        this.gamemodeName = "Team Deathmatch";
        this.maxPoints = 20;
        this.phases = 1;
        this.currentPhase = 1;
        this.length = 1000;
    }

    @EventHandler
    public void addPointsToKillersTeam(PlayerDeathEvent event) {
        Player killer = event.getPlayer().getKiller();
        if(killer == null) {
            return;
        }
        if(attack.isPlayerOnTeam(killer)) {
            attack.setPoints(attack.getPoints() + 1);

        }
        else if(defense.isPlayerOnTeam(killer)) {
            defense.setPoints(defense.getPoints() + 1);

        }

    }

    @EventHandler
    public void addPoints(PlayerDropItemEvent event) {
        this.attack.setPoints(this.attack.getPoints() + 1);
        Bukkit.broadcast(Component.text("Attack points = " + this.attack.getPoints()));
    }

}
