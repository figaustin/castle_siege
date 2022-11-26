package com.etsuni.siege.matches;

import com.etsuni.siege.Siege;
import com.etsuni.siege.maps.SiegeMap;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatchVoter implements CommandExecutor {

    private static Plugin plugin = Siege.getPlugin(Siege.class);
    private static HashMap<Gamemode, Integer> votes;

    private static Gamemode votedMode;

    private static Boolean inProgress;

    private TDM tdm = new TDM();
    private KOTH koth = new KOTH();

    private ArrayList<SiegeMap> siegeMaps;

    public MatchVoter() {
        votes = new HashMap<>();
        inProgress = false;
    }

    public static void matchVote() {
        //PULL MAPS FROM CONFIG FILE ??

        if(inProgress) {
            return;
        }

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        //GET RANDOM LIST OF MAPS AND THEIR RESPECTIVE GAMEMODES AND PICK A FEW TO BE VOTED ON
        Bukkit.broadcast(Component.text("Please vote for a gamemode!"));

        Integer currentHighest = 0;
        scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcast(Component.text("The chosen gamemode was: " + votedMode.getGamemodeName()));
                Match.matchStarter(votedMode);
            }
        }, 1200);

        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Map.Entry<Gamemode, Integer> vote : votes.entrySet()) {
                    if(vote.getValue() > currentHighest) {
                        votedMode = vote.getKey();
                    }
                }
            }
        }, 20, 20);
        inProgress = true;
    }

    public static Boolean inProgress() {
        return inProgress;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("vote")) {
            if(args[0].equalsIgnoreCase("TDM")) {
                if(votes.containsKey(tdm)) {
                    votes.replace(tdm, votes.get(tdm) + 1);
                }else{
                    votes.put(tdm, 1);
                }
            }
            else if(args[0].equalsIgnoreCase("KOTH")) {
                if(votes.containsKey(koth)) {
                    votes.replace(koth, votes.get(koth) + 1);
                }else{
                    votes.put(koth, 1);
                }
            }
            else if(args[0].equalsIgnoreCase("getvotes")) {
                Bukkit.broadcast(Component.text(votes.toString()));
            }
        }

        return false;
    }
}
