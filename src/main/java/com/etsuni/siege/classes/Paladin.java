package com.etsuni.siege.classes;

import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class Paladin implements Listener {

    public void giveKit(Player player) {
        if(Siege.siegeClassUtil.playerClasses.containsKey(player)) {
            Siege.siegeClassUtil.removePlayerFromSiegeClass(player);
        }
        Siege.siegeClassUtil.addPlayerToSiegeClass(player, "paladin");
        PlayerInventory playerInventory = player.getInventory();

        ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Holy Blade"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.GOLDEN_HELMET);
        playerInventory.setHelmet(item);

        item = new ItemStack(Material.GOLDEN_LEGGINGS);
        playerInventory.setLeggings(item);

        item = new ItemStack(Material.GOLDEN_BOOTS);
        playerInventory.setBoots(item);

        item = new ItemStack(Material.ENCHANTED_BOOK);


    }
}
