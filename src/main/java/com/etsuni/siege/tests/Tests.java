package com.etsuni.siege.tests;

import com.etsuni.siege.Siege;
import com.etsuni.siege.classes.Archer;
import com.etsuni.siege.classes.Knight;
import com.etsuni.siege.classes.SiegeClassMenu;
import com.etsuni.siege.matches.Match;
import com.etsuni.siege.teams.SiegeTeam;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Tests implements CommandExecutor {
    public Match match = new Match(false, new ArrayList<Player>(), 1000,
            new SiegeTeam(new ArrayList<Player>(), "Attack"),
            new SiegeTeam(new ArrayList<Player>(), "Defense")
    );
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;


        if(command.getName().equalsIgnoreCase("siege")) {
            if(args[0].equalsIgnoreCase("knight")) {
                Siege.knight.giveKit(player);
            }
            else if(args[0].equalsIgnoreCase("archer")) {
                Siege.archer.giveKit(player);
            }
            else if(args[0].equalsIgnoreCase("berserker")) {
                Siege.berserker.giveKit(player);
            }
            else if(args[0].equalsIgnoreCase("startmatch")) {
                match.startMatch();
            }
            else if(args[0].equalsIgnoreCase("join")) {
                match.joinMatch(player);
            }
            else if(args[0].equalsIgnoreCase("leave")) {
                match.leaveMatch(player);
            }
            else if(args[0].equalsIgnoreCase("classmenu")) {
                player.openInventory(Siege.siegeClassMenu.openClassMenu());
            }
            else if(args[0].equalsIgnoreCase("getclassname")) {
                player.sendMessage(Component.text(Siege.siegeClassUtil.getPlayersSiegeClass(player)));
            }
        }
        return false;
    }
}
