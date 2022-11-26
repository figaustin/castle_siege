package com.etsuni.siege.classes;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Berserker extends SiegeClass implements Listener {

    //TODO ADD COOLDOWNS TO ABILITIES

    private CooldownManager cooldownManager;
    private HashMap<Player,Long> playerJump;

    public Berserker() {
        this.cooldownManager = new CooldownManager();
        this.playerJump = new HashMap<>();
    }

    public void giveKit(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Hand Axe"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.SHIELD);
        playerInventory.addItem(item);

        item = new ItemStack(Material.ENCHANTED_BOOK);
        meta.displayName(Component.text(ChatColor.LIGHT_PURPLE + "Frenzy"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.ENCHANTED_BOOK);
        meta.displayName(Component.text(ChatColor.LIGHT_PURPLE + "Smash"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.DIAMOND_HELMET);
        playerInventory.setHelmet(item);

        item = new ItemStack(Material.DIAMOND_LEGGINGS);
        playerInventory.setLeggings(item);

        item = new ItemStack(Material.DIAMOND_BOOTS);
        playerInventory.setBoots(item);
    }



    @EventHandler
    public void abilityOne(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if(!SiegeClassUtil.abilityCheck(player,action, SiegeClasses.BERSERKER)) {
            return;
        }
        PlayerInventory playerInventory = player.getInventory();
        if(action.isRightClick() && playerInventory.getItemInMainHand().displayName().toString().contains("Frenzy")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 2));
        }
    }

    @EventHandler
    public void abilityTwo(PlayerInteractEvent event) {

    }

    @Override
    public void ultimate(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if(!SiegeClassUtil.abilityCheck(player,action, SiegeClasses.BERSERKER)) {
            return;
        }
        PlayerInventory playerInventory = player.getInventory();
        Vector playerVector = player.getLocation().getDirection();

        if(action.isRightClick() && playerInventory.getItemInMainHand().displayName().toString().contains("Smash")) {
            if(!playerJump.containsKey(player)) {
                player.setVelocity(playerVector.multiply(1.5));
                Bukkit.broadcast(Component.text("Added player to list"));
                playerJump.put(player, System.currentTimeMillis());
            }
        }
    }

    @EventHandler
    public void onLand(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        if(!player.isOnGround()) {return;}
        if(this.playerJump.containsKey(player) && System.currentTimeMillis() - this.playerJump.get(player) > 500) {
            Bukkit.broadcast(Component.text("Jump Working!!!"));
            //TODO ADD EFFECT AND DAMAGE CIRCLE
            this.playerJump.remove(player);
        }
    }
}

