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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Match implements Listener {

    private Plugin plugin = Siege.getPlugin(Siege.class);

    private ArrayList<Player> playersInMatch;
    private Boolean inProgress;

    private SiegeTeam attack;
    private SiegeTeam defense;


    private Gamemode gamemode;

    private int matchId;
    private int pointsCounterId;

    public Match(Gamemode gamemode) {
        this.inProgress = false;
        this.attack = new SiegeTeam("Attack");
        this.defense = new SiegeTeam("Defense");
        this.playersInMatch = new ArrayList<>();
        this.gamemode = gamemode;
    }

    public void startMatch() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        Bukkit.broadcast(Component.text("Match Started"));
        //TODO MAKE SOMETHING HAPPEN WHEN MATCH STARTS
        this.inProgress = true;
        Integer maxPoints = this.gamemode.getMaxPoints();

        this.matchId = scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Bukkit.broadcast(Component.text("Match Stopped"));
                    endMatch();
                }
            }, 1000 ); // SWITCH TO GAMEMODE.LENGTH ? ??? ?

        this.pointsCounterId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if(pointsChecker()) {
                    Bukkit.broadcast(Component.text("Ended match due to points"));
                    endMatch();
                    scheduler.cancelTask(pointsCounterId);
                    scheduler.cancelTask(matchId);
                }
                Bukkit.broadcast(Component.text("heyy!!! this is the pointsCounter thing!!!"));
            }
        },10, 10);

        }


    public Boolean pointsChecker() {
        return this.attack.getPoints() >= this.gamemode.getMaxPoints() ||
                this.defense.getPoints() >= this.gamemode.getMaxPoints();
    }
    public void endMatch() {
        if(this.inProgress) {
            this.inProgress = false;
            World world = Bukkit.getWorld("world");
            Location location = new Location(world, 134, 85, 195);

            for(Player player : this.playersInMatch){
                player.teleport(location);
                clearTeams();
                Bukkit.broadcast(Component.text("Test: Removed" + player.displayName() + "from match (player list)"));
            }
            this.clearTeams();

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

    public void joinMatch(Player player) {
        if(this.inProgress) {
            this.playersInMatch.add(player);
            player.sendMessage(Component.text("You joined the match"));
        }else {
            player.sendMessage(Component.text("That match is not currently in progress"));
        }
    }

    public void leaveMatch(Player player) {
        this.playersInMatch.remove(player);
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

    public ArrayList<Player> getPlayersInMatch() {
        return playersInMatch;
    }

    public void setPlayersInMatch(ArrayList<Player> playersInMatch) {
        this.playersInMatch = playersInMatch;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getPointsCounterId() {
        return pointsCounterId;
    }

    public void setPointsCounterId(int pointsCounterId) {
        this.pointsCounterId = pointsCounterId;
    }
}
