package com.etsuni.siege.matches;

import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;


import java.util.ArrayList;

public class Match implements Listener {

    private Plugin plugin = Siege.getPlugin(Siege.class);

    private static Plugin pluginStatic = Siege.getPlugin(Siege.class);

    public static ArrayList<Match> matchList = new ArrayList<>();

    public static Match currentMatch;

    private ArrayList<Player> playersInMatch;

    private Boolean inProgress;

    private Gamemode gamemode;

    private int matchId;

    private int pointsCounterId;

    public Match() {
        this.inProgress = false;
        this.playersInMatch = new ArrayList<>();
    }

    /*
    * Check if there is no current match happening, if there isn't start the map/gamemode voting process
     */
    public static void gameLoop() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        scheduler.scheduleSyncRepeatingTask(pluginStatic, new Runnable() {
            @Override
            public void run() {
                if(matchList.isEmpty() && !MatchVoter.inProgress()) {
                    MatchVoter.matchVote();

                }
            }
        }, 100, 100);

    }

    /*
    * Static method to be able to start a new match from anywhere without having to create new instance
    * Could possibly make this not static.
     */
    public static void matchStarter(Gamemode gamemode) {
        Match match = new Match();
        currentMatch = match;
        match.startMatch(gamemode);
    }


    /*
    * Actual method to start a new match
    * Makes two new Runnables: one that runs the entire game length and will end the match, other one will repeat until the score
    * of the match reaches the max points and will end the match.
    * @param gamemode the gamemode that the match should be
     */
    public void startMatch(Gamemode gamemode) {
        this.gamemode = gamemode;

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        matchList.add(this);

        inProgress = true;

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


    /*
    * Teleport all players back to spawn, remove all players from any lists/maps they are in
     */
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

    /*
    * Method that is called in startMatch() to continously check teams' points to see if they are equal to the
    * match's max points.
     */
    public Boolean pointsChecker() {

        return gamemode.getAttack().getPoints() >= gamemode.getMaxPoints() ||
                this.gamemode.getDefense().getPoints() >= this.gamemode.getMaxPoints();
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
