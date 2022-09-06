package com.etsuni.siege.classes;

import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
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

import java.util.ArrayList;
import java.util.List;

public class Assassin implements Listener {

    public List<Player> invisiblePlayers = new ArrayList<>();


    public void giveKit(Player player) {
        if(Siege.siegeClassUtil.playerClasses.containsKey(player)) {
            Siege.siegeClassUtil.removePlayerFromSiegeClass(player);
        }
        Siege.siegeClassUtil.addPlayerToSiegeClass(player, "assassin");
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();

        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Rapier"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.CHAINMAIL_HELMET);
        playerInventory.setHelmet(item);

        item = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        playerInventory.setLeggings(item);

        item = new ItemStack(Material.CHAINMAIL_BOOTS);
        playerInventory.setBoots(item);

        item = new ItemStack(Material.ENCHANTED_BOOK);
        meta.displayName(Component.text("Shadow Cloak"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.ENCHANTED_BOOK);
        meta.displayName(Component.text("TBD"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);
    }

    @EventHandler
    public void castAbilityOne(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if(SiegeClassUtil.abilityDeny(player, action, "assassin") == false) {
            return;
        }
        PlayerInventory playerInventory = player.getInventory();
        ItemStack mainHand = playerInventory.getItemInMainHand();
        if(SiegeClassUtil.abilityCheck(action, mainHand, "Shadow Cloak")) {
            if(!this.invisiblePlayers.contains(player)) {
                this.invisiblePlayers.add(player);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 4, false, false, true));

            }
        }
    }

    @EventHandler
    public void invisibleExit(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if(action.isRightClick()) {return;}
        if(action.isLeftClick() && this.invisiblePlayers.contains(player)) {
            this.invisiblePlayers.remove(player);
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);

        }
    }
}
