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
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Match implements Listener, CommandExecutor {

    private Plugin plugin = Siege.getPlugin(Siege.class);

    private ArrayList<Player> playersInMatch;
    private Boolean inProgress;

    private SiegeTeam attack;
    private SiegeTeam defense;

    private HashMap<SiegeTeam, Integer> teamsPoints;

    private Gamemode gamemode;

    public Match() {
        this.inProgress = false;
        this.attack = new SiegeTeam("Attack");
        this.defense = new SiegeTeam("Defense");
        this.teamsPoints = new HashMap<>();
        this.playersInMatch = new ArrayList<>();
    }

    public void startMatch(Match match) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        Bukkit.broadcast(Component.text("Match Started"));
        //TODO MAKE SOMETHING HAPPEN WHEN MATCH STARTS
        match.setInProgress(true);


        scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Bukkit.broadcast(Component.text("Match Stopped"));
                    endMatch(match);
                }
            }, 1000 ); // SWITCH TO GAMEMODE.LENGTH ? ??? ?
        Bukkit.broadcast(Component.text("This is the message after delayed task"));
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcast(Component.text("Counterrr"));
            }
        },10, 1000);

        }


    public void endMatch(Match match) {
        if(match.getInProgress()) {
            match.setInProgress(false);
            World world = Bukkit.getWorld("world");
            Location location = new Location(world, 134, 85, 195);

            for(Player player : match.getPlayersInMatch()){
                player.teleport(location);
                clearTeams();
                Bukkit.broadcast(Component.text("Test: Removed" + player.displayName() + "from match (player list)"));
            }
            match.clearTeams();

        }
    }


    //This uses a temp variable to empty the team player arrays
    public void clearTeams() {
        ArrayList<Player> attackPlayers = attack.getPlayersOnTeam();
        ArrayList<Player> defensePlayers = defense.getPlayersOnTeam();
        attackPlayers = new ArrayList<Player>();
        defensePlayers = new ArrayList<Player>();
        attack.setPlayersOnTeam(attackPlayers);
        defense.setPlayersOnTeam(defensePlayers);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if(command.getName().equalsIgnoreCase("attack")) {
            attack.addPlayerToTeam(player, attack);
        }
        else if (command.getName().equalsIgnoreCase("defense")) {
            defense.addPlayerToTeam(player, defense);
        }
        return false;
    }

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

    public Gamemode getGamemode() {
        return gamemode;
    }

    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public HashMap<SiegeTeam, Integer> getTeamsPoints() {
        return teamsPoints;
    }

    public void setTeamsPoints(HashMap<SiegeTeam, Integer> teamsPoints) {
        this.teamsPoints = teamsPoints;
    }

    public ArrayList<Player> getPlayersInMatch() {
        return playersInMatch;
    }

    public void setPlayersInMatch(ArrayList<Player> playersInMatch) {
        this.playersInMatch = playersInMatch;
    }

}
