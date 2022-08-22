package com.etsuni.siege.classes;

import com.destroystokyo.paper.ParticleBuilder;
import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class Knight implements Listener {

    public Knight() {}
    public void giveKit(Player player) {
        if(Siege.siegeClassUtil.playerClasses.containsKey(player)) {
            Siege.siegeClassUtil.removePlayerFromSiegeClass(player);
        }

        Siege.siegeClassUtil.addPlayerToSiegeClass(player, "knight");

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

    @EventHandler
    public void castAbilityOne(PlayerInteractEvent event) {
        Action action = event.getAction();
        if(action.isLeftClick()) {return;}
        Player player = event.getPlayer();
        if(!Siege.siegeClassUtil.getPlayersSiegeClass(player).equalsIgnoreCase("knight")){return;}
        PlayerInventory playerInventory = player.getInventory();

        if(action.isRightClick() && playerInventory.getItemInMainHand().getType().equals(Material.REDSTONE)) {
            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200,1));
            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
        }
    }

    @EventHandler
    public void castAbilityTwo(PlayerInteractEvent event) {
        Action action = event.getAction();
        if(action.isLeftClick()) {return;}
        Player player = event.getPlayer();
        if(!Siege.siegeClassUtil.getPlayersSiegeClass(player).equalsIgnoreCase("knight")){return;}
        PlayerInventory playerInventory = player.getInventory();
        Location playerLocation = player.getLocation();

        if(action.isRightClick() && playerInventory.getItemInMainHand().getType().equals(Material.GLOWSTONE_DUST)) {
            Collection<LivingEntity> entities = playerLocation.getNearbyLivingEntities(10.00);

            for(LivingEntity livingEntity : entities) {
                livingEntity.damage(5.0, player);
            }
        }
    }
}
