package com.etsuni.siege.tests;

import com.etsuni.siege.Siege;
import com.etsuni.siege.matches.Match;
import com.etsuni.siege.matches.TDM;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Tests implements CommandExecutor {
    public Match match = new Match();
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
            else if(args[0].equalsIgnoreCase("paladin")) {
                Siege.paladin.giveKit(player);
            }
            else if(args[0].equalsIgnoreCase("assassin")) {
                Siege.assassin.giveKit(player);
            }
            else if(args[0].equalsIgnoreCase("startmatch")) {
                match.startMatch(new TDM());
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
            else if(args[0].equalsIgnoreCase("playersinmatch")) {
                player.sendMessage(Component.text(match.getPlayersInMatch().toString()));
            }
            else if(args[0].equalsIgnoreCase("playersonattack")) {
                player.sendMessage(Component.text(match.getGamemode().getAttack().getPlayersOnTeam().toString()));
            }
            else if(args[0].equalsIgnoreCase("attack")) {
                match.getGamemode().getAttack().addPlayerToTeam(player, match.getGamemode().getAttack());
            }
            else if(args[0].equalsIgnoreCase("givepoints")) {
                match.getGamemode().getAttack().setPoints(75);
            }
            else if(args[0].equalsIgnoreCase("openmatchmenu")) {
                player.openInventory(Siege.matchesMenu.menu);
            }
        }
        return false;
    }
}
