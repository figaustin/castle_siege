package com.etsuni.siege.classes;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;

public class SiegeClassMenu implements Listener {

    public Inventory siegeClassMenu;

    public SiegeClassMenu() {}

    public Inventory openClassMenu() {
        Component invTitle = Component.text(ChatColor.GREEN + "" + ChatColor.BOLD + "Choose A Class");
        siegeClassMenu = Bukkit.createInventory(null, 54, invTitle);

        //TODO Make items and names configurable
        Component knightTitle = Component.text(ChatColor.GOLD + "" + ChatColor.BOLD + "Knight");
        Component rangerTitle = Component.text(ChatColor.GREEN + "" + ChatColor.BOLD + "Ranger");
        Component berserkerTitle = Component.text(Color.RED + "" + ChatColor.BOLD + "Berserker");
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = item.getItemMeta();

        //Knight
        meta.displayName(knightTitle);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        siegeClassMenu.setItem(22, item);

        //Archer
        item = new ItemStack(Material.BOW, 1);
        meta.displayName(rangerTitle);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        siegeClassMenu.setItem(21, item);

        //Berserker
        item = new ItemStack(Material.IRON_AXE,1);
        meta.displayName(berserkerTitle);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        siegeClassMenu.setItem(23, item);
        return siegeClassMenu;
    }

}
