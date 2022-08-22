package com.etsuni.siege.matches;

import com.etsuni.siege.Siege;
import com.etsuni.siege.teams.SiegeTeam;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Match implements Listener, CommandExecutor {

    private Plugin plugin = Siege.getPlugin(Siege.class);
    private ArrayList<Player> playersInMatch;
    private Boolean inProgress;

    private SiegeTeam attack;
    private SiegeTeam defense;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    private Integer length;

    public Match(Boolean inProgress, ArrayList<Player> playersInMatch, Integer length, SiegeTeam attack, SiegeTeam defense) {
        this.inProgress = inProgress;
        this.playersInMatch = playersInMatch;
        this.length = length;
        this.attack = attack;
        this.defense = defense;
    }

    public void startMatch() {
        if(!this.getInProgress()) {
            this.setInProgress(true);
            this.attack = new SiegeTeam(new ArrayList<Player>(), "Attack");
            this.defense = new SiegeTeam(new ArrayList<Player>(), "Defense");
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            Bukkit.broadcast(Component.text("Match Started"));
            scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Bukkit.broadcast(Component.text("Match Stopped"));
                    endMatch();
                }
            }, this.getLength() );

        }
    }

    public void endMatch() {
        if(this.getInProgress()) {
            this.setInProgress(false);
            World world = Bukkit.getWorld("world");
            Location location = new Location(world, 134, 85, 195);

            for(Player player : getPlayersInMatch()){
                player.teleport(location);
                Bukkit.broadcast(Component.text("Test: Removed" + player.displayName() + "from match (player list)"));
            }
            this.setPlayersInMatch(new ArrayList<Player>());

        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if(command.getName().equalsIgnoreCase("attack")) {
            this.attack.addPlayerToTeam(player);
        }
        else if (command.getName().equalsIgnoreCase("defense")) {
            this.defense.addPlayerToTeam(player);
        }
        return false;
    }

    //TODO Make it so you can join CERTAIN matches not just one and make sure it is in progress
    public void joinMatch(Player player) {
        if(this.getInProgress()) {
            this.getPlayersInMatch().add(player);
            player.sendMessage(Component.text("You joined the match"));
        }else {
            player.sendMessage(Component.text("That match is not currently in progress"));
        }

    }

    public void leaveMatch(Player player) {
        this.getPlayersInMatch().remove(player);
        player.sendMessage(Component.text("You have left the match"));
    }

    public Boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }

    public ArrayList<Player> getPlayersInMatch() {
        return playersInMatch;
    }

    public void setPlayersInMatch(ArrayList<Player> playersInMatch) {
        this.playersInMatch = playersInMatch;
    }


}
