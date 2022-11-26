package com.etsuni.siege.classes;

import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
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

public class Knight extends SiegeClass implements Listener {

    private CooldownManager cooldownManager;

    public Knight(){
        this.cooldownManager = new CooldownManager();
    }


    public void giveKit(Player player) {

        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Longsword"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.NETHERITE_HELMET, 1);
        playerInventory.setHelmet(item);

        item = new ItemStack(Material.NETHERITE_LEGGINGS, 1);
        playerInventory.setLeggings(item);

        item = new ItemStack(Material.NETHERITE_BOOTS);
        playerInventory.setBoots(item);
    }

    //TODO MAKE ABILITIES USE SKILL BOOKS AND CHECK FOR THEM IN INVENTORY/MAIN HAND
    @EventHandler
    public void abilityOne(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if(!SiegeClassUtil.abilityCheck(player,action, SiegeClasses.KNIGHT)){return;}
        PlayerInventory playerInventory = player.getInventory();

        if(action.isRightClick() && playerInventory.getItemInMainHand().getType().equals(Material.REDSTONE)) {
            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200,1));
            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
        }
    }


    //TODO WORK ON THIS ABILITY ; MAKE IT BETTER/ACTUALLY WORK
    @EventHandler
    public void abilityTwo(PlayerInteractEvent event) {
        Action action = event.getAction();
        if(action.isLeftClick()) {return;}
        Player player = event.getPlayer();
        if(!SiegeClassUtil.abilityCheck(player, action, SiegeClasses.KNIGHT)){return;}
        PlayerInventory playerInventory = player.getInventory();
        Location playerLocation = player.getLocation();

        if(action.isRightClick() && playerInventory.getItemInMainHand().getType().equals(Material.GLOWSTONE_DUST)) {
            Collection<LivingEntity> entities = playerLocation.getNearbyLivingEntities(10.00);

            for(LivingEntity livingEntity : entities) {
                livingEntity.damage(5.0, player);
            }
        }
    }

    @EventHandler
    public void ultimate(PlayerInteractEvent event) {

    }
}
