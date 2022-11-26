package com.etsuni.siege.classes;

import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SiegeClassUtil {

    public SiegeClassUtil() {}


    public static Boolean abilityCheck(Player player, Action action, SiegeClasses siegeClass) {
        return !action.isLeftClick() && PlayersClassList.getInstance().getPlayersClass(player).equals(siegeClass);
    }

    public static Boolean itemCheck(Action action, ItemStack item, String name) {
        return action.isRightClick() && item.displayName().toString().contains(name);
    }
}
