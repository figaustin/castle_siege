package com.etsuni.siege.classes;

import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

    @EventHandler
    public void changeArrow(PlayerInteractEvent event) {
        Action action = event.getAction();
        if(action.isLeftClick()){return;}
        Player player = event.getPlayer();
        if(!Siege.siegeClassUtil.getPlayersSiegeClass(player).equalsIgnoreCase("archer")) {
            return;
        }
        PlayerInventory playerInventory = player.getInventory();
        ItemStack mainHand = playerInventory.getItemInMainHand();
        Integer slot = playerInventory.getHeldItemSlot();
        if(action.isRightClick() && mainHand.getType().equals(Material.TIPPED_ARROW)) {
            Bukkit.broadcast(Component.text("Tipped arrow found"));
            ItemStack poisonArrow = new ItemStack(Material.TIPPED_ARROW);
            PotionMeta poisonMeta = (PotionMeta) poisonArrow.getItemMeta();
            poisonMeta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 60, 1, true, true, true), false);
            poisonMeta.setColor(Color.LIME);
            poisonMeta.displayName(Component.text("Poison Arrow"));
            poisonArrow.setItemMeta(poisonMeta);

            ItemStack shackleArrow = new ItemStack(Material.TIPPED_ARROW);
            PotionMeta shackleMeta = (PotionMeta) shackleArrow.getItemMeta();
            shackleMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1), false);
            shackleMeta.setColor(Color.BLACK);
            shackleMeta.displayName(Component.text("Shackle Arrow"));
            shackleArrow.setItemMeta(shackleMeta);

            ItemStack damageArrow = new ItemStack(Material.TIPPED_ARROW);
            PotionMeta damageMeta = (PotionMeta) damageArrow.getItemMeta();
            damageMeta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 3, 1), true);
            damageMeta.setColor(Color.MAROON);
            damageMeta.displayName(Component.text("Razor Sharp Arrow"));
            damageArrow.setItemMeta(damageMeta);
            damageArrow.setItemMeta(damageMeta);

            if(mainHand.getItemMeta().equals(damageMeta)) {
                Bukkit.broadcast(Component.text("Sharp arrow found"));
                playerInventory.setItem(slot, poisonArrow);
            }
            else if(mainHand.getItemMeta().equals(poisonMeta)) {
                playerInventory.setItem(slot, shackleArrow);
            }
            else if(mainHand.getItemMeta().equals(shackleMeta)) {
                playerInventory.setItem(slot, damageArrow);
            }
        }
    }

    @EventHandler
    public void castAbilityOne(PlayerInteractEvent event) {
        Action action = event.getAction();
        if(action.isLeftClick()){return;}
        Player player = event.getPlayer();
        if(!Siege.siegeClassUtil.getPlayersSiegeClass(player).equalsIgnoreCase("archer")) {
            return;
        }
        PlayerInventory playerInventory = player.getInventory();
        ItemStack mainHand = playerInventory.getItemInMainHand();

        //TODO ACTUALLY MAKE ABILITY DO SOMETHING

    }
}
