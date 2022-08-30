package com.etsuni.siege.classes;

import com.etsuni.siege.Siege;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
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

import java.util.ArrayList;
import java.util.HashMap;

public class Berserker implements Listener {


    public ArrayList<Entity> playersJumping = new ArrayList<>();
    public HashMap<Player,Long> playerJump = new HashMap<>();
    public Berserker() {

    }

    public void giveKit(Player player) {
        if(Siege.siegeClassUtil.playerClasses.containsKey(player)) {
            Siege.siegeClassUtil.removePlayerFromSiegeClass(player);
        }

        Siege.siegeClassUtil.addPlayerToSiegeClass(player, "berserker");

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
    public void castAbilityOne(PlayerInteractEvent event) {
        //TODO MAKE THIS SPELL CLEANSE NEGATIVE EFFECTS ASWELL
        Action action = event.getAction();
        if(action.isLeftClick()) {return;}
        Player player = event.getPlayer();
        if(!Siege.siegeClassUtil.getPlayersSiegeClass(player).equalsIgnoreCase("berserker")){return;}
        PlayerInventory playerInventory = player.getInventory();
        if(action.isRightClick() && playerInventory.getItemInMainHand().displayName().toString().contains("Frenzy")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 2));
        }
    }

    @EventHandler
    public void castAbilityTwo(PlayerInteractEvent event) {
        Action action = event.getAction();
        if(action.isLeftClick()) {return;}
        Player player = event.getPlayer();
        if(!Siege.siegeClassUtil.getPlayersSiegeClass(player).equalsIgnoreCase("berserker")){return;}
        PlayerInventory playerInventory = player.getInventory();
        Vector playerVector = player.getLocation().getDirection();

        if(action.isRightClick() && playerInventory.getItemInMainHand().displayName().toString().contains("Smash")) {
            if(!this.playerJump.containsKey(player)) {
                player.setVelocity(playerVector.multiply(1.5));
                Bukkit.broadcast(Component.text("Added player to list"));
                this.playerJump.put(player, System.currentTimeMillis());
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
            //TODO SET BREAK BLOCKS TO FALSE FOR PROD; POSSIBLY CHANGE TO JUST AOE DAMAGE INSTEAD OF EXPLOSION
            location.createExplosion(player, 2.0F, false, true);
            this.playerJump.remove(player);
        }

    }

    public ArrayList<Entity> getPlayersJumping() {
        return playersJumping;
    }

    public HashMap<Player,Long> getPlayerJump() {
        return playerJump;
    }

    public void setPlayerJump(HashMap<Player, Long> playerJump) {
        this.playerJump = playerJump;
    }

    public void setPlayersJumping(ArrayList<Entity> playersJumping) {
        this.playersJumping = playersJumping;
    }
}
