package com.etsuni.siege.classes;

import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class Archer extends SiegeClass implements Listener {

    private CooldownManager cooldownManager;

    private Map<UUID, Long> reloadCooldownMap;
    private int reloadCooldown;

    public Archer() {
        this.cooldownManager = new CooldownManager();
        this.reloadCooldownMap = new HashMap<>();
        this.reloadCooldown = 30;
    }

    public void giveKit(Player player) {

        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.ARROW_INFINITE,1, false);
        meta.displayName(Component.text("Longbow"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.TIPPED_ARROW, 64);
        PotionMeta damageMeta = (PotionMeta) item.getItemMeta();
        damageMeta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 3, 0), true);
        damageMeta.setColor(Color.MAROON);
        damageMeta.displayName(Component.text("Razor Sharp Arrow"));
        item.setItemMeta(damageMeta);
        playerInventory.addItem(item);

        item = new ItemStack(Material.WOODEN_SWORD, 1);
        playerInventory.addItem(item);


        item = new ItemStack(Material.LEATHER_HELMET);
        playerInventory.setHelmet(item);

        item = new ItemStack(Material.LEATHER_LEGGINGS);
        playerInventory.setLeggings(item);

        item = new ItemStack(Material.LEATHER_BOOTS);
        playerInventory.setBoots(item);

        item = new ItemStack(Material.ENCHANTED_BOOK);
        meta.displayName(Component.text("Explosive Shot"));
        item.setItemMeta(meta);
        playerInventory.addItem(item);


    }

    @EventHandler
    public void changeArrow(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if(!SiegeClassUtil.abilityCheck(player, action, SiegeClasses.ARCHER)) {
            return;
        }
        PlayerInventory playerInventory = player.getInventory();
        ItemStack mainHand = playerInventory.getItemInMainHand();
        int slot = playerInventory.getHeldItemSlot();
        if(action.isRightClick() && mainHand.getType().equals(Material.TIPPED_ARROW)) {
            playerInventory.setItem(slot, changeArrow(mainHand));
        }
    }

    private ItemStack changeArrow(ItemStack arrow) {
        PotionMeta arrowMeta = (PotionMeta) arrow.getItemMeta();
        String name = Objects.requireNonNull(arrowMeta.displayName()).toString();

        switch(name) {
            case "Poison Arrow":
                arrowMeta.clearCustomEffects();
                arrowMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1, false, false), false);
                arrowMeta.setColor(Color.BLACK);
                arrowMeta.displayName(Component.text("Shackle Arrow"));
                arrow.setItemMeta(arrowMeta);
                break;
            case "Shackle Arrow":
                arrowMeta.clearCustomEffects();
                arrowMeta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 3, 1, false, false), true);
                arrowMeta.setColor(Color.MAROON);
                arrowMeta.displayName(Component.text("Razor Sharp Arrow"));
                arrow.setItemMeta(arrowMeta);
                break;
            case "Razor Sharp Arrow":
                arrowMeta.clearCustomEffects();
                arrowMeta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 60, 1 ,false, false), false);
                arrowMeta.setColor(Color.LIME);
                arrowMeta.displayName(Component.text("Poison Arrow"));
                break;
            default:
                return arrow;
        }

        return arrow;
    }

    @EventHandler
    public void abilityOne(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if(!SiegeClassUtil.abilityCheck(player,action, SiegeClasses.ARCHER)){
            return;
        }
        PlayerInventory playerInventory = player.getInventory();
        ItemStack mainHand = playerInventory.getItemInMainHand();
        ItemStack bow = playerInventory.getItem(0);

        if(SiegeClassUtil.itemCheck(action, mainHand, "Explosive Shot")){
            if(bow != null && !bow.hasItemFlag(ItemFlag.HIDE_DYE)) {
                bow.addItemFlags(ItemFlag.HIDE_DYE);
                bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
            }
        }

    }


    @EventHandler
    public void shootExplosiveShot(ProjectileHitEvent event) {
        Arrow arrow = (Arrow) event.getEntity();
        Player player = (Player) arrow.getShooter();
        Location location = arrow.getLocation();
        World world = arrow.getWorld();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!item.getType().equals(Material.BOW)) {return;}
        if(item.getItemMeta().hasItemFlag(ItemFlag.HIDE_DYE)) {
            world.createExplosion(location, 3, false, false);
            item.removeItemFlags(ItemFlag.HIDE_DYE);
        }
        arrow.remove();

    }

    @EventHandler
    public void reload(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();
        if(block == null) {
            return;
        }

        if(block.getType().equals(Material.BARREL) && SiegeClassUtil.abilityCheck(player,action, SiegeClasses.ARCHER)){
                cooldownManager.setCooldown(player, reloadCooldownMap, reloadCooldown, "Reload");
            }
            PlayerInventory playerInventory = player.getInventory();
            ItemStack item = playerInventory.getItem(1);
            int qty = 64 - item.getAmount();
            item.add(qty);
        }

    @Override
    public void abilityTwo(PlayerInteractEvent event) {
        //TODO
    }
    @Override
    public void ultimate(PlayerInteractEvent event) {
        //TODO
    }
}

