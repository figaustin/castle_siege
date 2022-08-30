package com.etsuni.siege.classes;

import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SiegeClassUtil {

    public HashMap<Player, String> playerClasses = new HashMap<>();

    public SiegeClassUtil() {}

    public void addPlayerToSiegeClass(Player player, String siegeClass) {
        playerClasses.put(player, siegeClass);
    }

    public void removePlayerFromSiegeClass(Player player) {
        playerClasses.remove(player);
    }

    public String getPlayersSiegeClass(Player player) {
        String siegeClass = "";
        if(playerClasses.containsKey(player)) {
            siegeClass = playerClasses.get(player);
        }
        return siegeClass;
    }

    public static Boolean abilityDeny(Player player, Action action, String name) {
        if(action.isLeftClick()) {return false;}
        if(!Siege.siegeClassUtil.getPlayersSiegeClass(player).equalsIgnoreCase(name)){return false;}
        return true;
    }

    public static Boolean abilityCheck(Action action, ItemStack item, String name) {
        return action.isRightClick() && item.displayName().toString().contains(name);
    }
}
