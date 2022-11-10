package com.etsuni.siege.matches;

import com.etsuni.siege.Siege;
import com.etsuni.siege.teams.SiegeTeam;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class Match implements Listener {

    private Plugin plugin = Siege.getPlugin(Siege.class);

    private static Plugin pluginStatic = Siege.getPlugin(Siege.class);

    public static ArrayList<Match> matchList = new ArrayList<>();

    private ArrayList<Player> playersInMatch;

    private Boolean inProgress;

    private Gamemode gamemode;

    private int matchId;

    private int pointsCounterId;

    public Match() {
        this.inProgress = false;
        this.playersInMatch = new ArrayList<>();
    }


    public static void gameLoop() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        scheduler.scheduleSyncRepeatingTask(pluginStatic, new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcast(Component.text(matchList.toString()));
                if(matchList.isEmpty()) {
                    matchStarter();
                    Bukkit.broadcast(Component.text("Game looper found list empty, starting new match"));
                }
            }
        }, 100, 100);

    }

    public static void matchStarter() {
        Match match = new Match();
        match.startMatch(new TDM());
    }

    public void startMatch(Gamemode gamemode) {
        this.gamemode = gamemode;
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        matchList.add(this);

        inProgress = true;
        Integer maxPoints = this.gamemode.getMaxPoints();

        matchId = scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Bukkit.broadcast(Component.text("Match Stopped"));
                    endMatch();
                    scheduler.cancelTask(pointsCounterId);
                }
            }, gamemode.getLength() );

        Bukkit.broadcast(Component.text("Match Started with id - " + matchId));

        pointsCounterId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if(pointsChecker()) {
                    Bukkit.broadcast(Component.text("Ended match due to points"));
                    endMatch();
                    scheduler.cancelTask(pointsCounterId);
                    scheduler.cancelTask(matchId);
                }
            }
        },10, 10);
    }

    public Boolean pointsChecker() {

        return gamemode.getAttack().getPoints() >= gamemode.getMaxPoints() ||
                this.gamemode.getDefense().getPoints() >= this.gamemode.getMaxPoints();
    }
    public void endMatch() {
        if(inProgress) {
            inProgress = false;
            World world = Bukkit.getWorld("world");
            Location location = new Location(world, 134, 85, 195);

            for(Player player : playersInMatch){
                player.teleport(location);
                Bukkit.broadcast(Component.text("Test: Removed" + player.displayName() + "from match (player list)"));
            }
        }
        matchList.remove(this);
    }


    public void joinMatch(Player player) {
        if(inProgress) {
            playersInMatch.add(player);
            player.sendMessage(Component.text("You joined the match"));
        }else {
            player.sendMessage(Component.text("That match is not currently in progress"));
        }
    }

    public void leaveMatch(Player player) {
        playersInMatch.remove(player);
        player.sendMessage(Component.text("You have left the match"));
    }

    public Boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
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
