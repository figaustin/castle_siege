package com.etsuni.siege.menus;

import com.etsuni.siege.Siege;
import com.etsuni.siege.matches.Gamemode;
import com.etsuni.siege.matches.Match;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MatchesMenu implements Listener {

    public Inventory menu;

    private ArrayList<Match> matchesList;

    public MatchesMenu() {
        this.matchesList = new ArrayList<>();
    }

    public Inventory createMenu() {
        Component title = Component.text(ChatColor.GOLD + "" + ChatColor.BOLD + "" +
                "Choose A Match To Join");
        menu = Bukkit.createInventory(null, 54, title);

        ItemStack item = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta meta = item.getItemMeta();

        Component newMatchTitle = Component.text("New Match");

        meta.displayName(newMatchTitle);
        item.setItemMeta(meta);
        menu.setItem(53, item);


        return menu;
    }

    @EventHandler
    public void menuClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Integer slot = event.getSlot();

        if(slot == 53) {
        }




        event.setCancelled(true);
    }

}
