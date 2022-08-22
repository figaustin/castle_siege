package com.etsuni.siege.classes;

import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Archer implements Listener {

    public ArrayList<Player> playersList = new ArrayList<>();
    public Archer() {

    }

    public void giveKit(Player player) {
        if(Siege.siegeClassUtil.playerClasses.containsKey(player)) {
            Siege.siegeClassUtil.removePlayerFromSiegeClass(player);
        }

        Siege.siegeClassUtil.addPlayerToSiegeClass(player, "archer");

        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Longbow"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.WOODEN_SWORD, 1);
        playerInventory.addItem(item);

        item = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta damageMeta = (PotionMeta) item.getItemMeta();
        damageMeta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 3, 1), true);
        damageMeta.setColor(Color.MAROON);
        damageMeta.displayName(Component.text("Razor Sharp Arrow"));
        item.setItemMeta(damageMeta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.LEATHER_HELMET);
        playerInventory.setHelmet(item);

        item = new ItemStack(Material.LEATHER_LEGGINGS);
        playerInventory.setLeggings(item);

        item = new ItemStack(Material.LEATHER_BOOTS);
        playerInventory.setBoots(item);

    }

    public void castAbilityOne(Player player) {

    }
}
