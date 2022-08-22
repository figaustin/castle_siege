package com.etsuni.siege.classes;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
}
