package com.etsuni.siege.classes;

import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.List;

public class Paladin implements Listener {

    public void giveKit(Player player) {
        if(Siege.siegeClassUtil.playerClasses.containsKey(player)) {
            Siege.siegeClassUtil.removePlayerFromSiegeClass(player);
        }
        Siege.siegeClassUtil.addPlayerToSiegeClass(player, "paladin");
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();

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

        item = new ItemStack(Material.SHIELD);
        meta.displayName(Component.text("Paladin Shield"));
        item.setItemMeta(meta);
        playerInventory.setItemInOffHand(item);

        item = new ItemStack(Material.ENCHANTED_BOOK);
        meta.displayName(Component.text("Divine Regeneration"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.ENCHANTED_BOOK);
        meta.displayName(Component.text("Divine Shield"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);

    }


    @EventHandler
    public void castAbilityOne(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if(SiegeClassUtil.abilityDeny(player, action, "paladin") == false){
            player.sendMessage("nope");
            return;
        }
        PlayerInventory playerInventory = player.getInventory();
        ItemStack mainHand = playerInventory.getItemInMainHand();
        if(SiegeClassUtil.abilityCheck(action, mainHand, "Divine Regeneration")) {
            player.sendMessage("yup");
            Location location = player.getLocation();
            Collection<Player> players = location.getNearbyPlayers(10.0, 10.0);
            for(Player p : players) {
                if(!p.hasPotionEffect(PotionEffectType.REGENERATION)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
                }
            }
        }
    }

    @EventHandler
    public void castAbilityTwo(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if(SiegeClassUtil.abilityDeny(player, action, "paladin") == false) {
            return;
        }

        PlayerInventory playerInventory = player.getInventory();
        ItemStack mainHand = playerInventory.getItemInMainHand();
        if(SiegeClassUtil.abilityCheck(action, mainHand, "Divine Shield")) {
            if(!player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 0));
            }
        }
    }


}
