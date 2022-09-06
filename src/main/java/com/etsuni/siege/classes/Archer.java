package com.etsuni.siege.classes;

import com.etsuni.siege.Siege;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Archer implements Listener {

    private final CooldownManager cooldownManager = new CooldownManager();

    public ArrayList<Player> playersList = new ArrayList<>();


    private final Map<UUID, Long> reloadCooldowns = new HashMap<>();
    private final int reloadCooldown = 30;



    public void giveKit(Player player) {
        if(Siege.siegeClassUtil.playerClasses.containsKey(player)) {
            Siege.siegeClassUtil.removePlayerFromSiegeClass(player);
        }

        Siege.siegeClassUtil.addPlayerToSiegeClass(player, "archer");

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
        if(action.isLeftClick()){return;}
        Player player = event.getPlayer();
        if(!Siege.siegeClassUtil.getPlayersSiegeClass(player).equalsIgnoreCase("archer")) {
            return;
        }
        PlayerInventory playerInventory = player.getInventory();
        ItemStack mainHand = playerInventory.getItemInMainHand();
        int amnt = mainHand.getAmount();
        Integer slot = playerInventory.getHeldItemSlot();
        if(action.isRightClick() && mainHand.getType().equals(Material.TIPPED_ARROW)) {

            Bukkit.broadcast(Component.text("Tipped arrow found"));
            ItemStack poisonArrow = new ItemStack(Material.TIPPED_ARROW, amnt);
            PotionMeta poisonMeta = (PotionMeta) poisonArrow.getItemMeta();
            poisonMeta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 60, 1, false, false), false);
            poisonMeta.setColor(Color.LIME);
            poisonMeta.displayName(Component.text("Poison Arrow"));
            poisonArrow.setItemMeta(poisonMeta);

            ItemStack shackleArrow = new ItemStack(Material.TIPPED_ARROW, amnt);
            PotionMeta shackleMeta = (PotionMeta) shackleArrow.getItemMeta();
            shackleMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1, false, false), false);
            shackleMeta.setColor(Color.BLACK);
            shackleMeta.displayName(Component.text("Shackle Arrow"));
            shackleArrow.setItemMeta(shackleMeta);

            ItemStack damageArrow = new ItemStack(Material.TIPPED_ARROW, amnt);
            PotionMeta damageMeta = (PotionMeta) damageArrow.getItemMeta();
            damageMeta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 3, 1, false, false), true);
            damageMeta.setColor(Color.MAROON);
            damageMeta.displayName(Component.text("Razor Sharp Arrow"));
            damageArrow.setItemMeta(damageMeta);
            damageArrow.setItemMeta(damageMeta);

            if(mainHand.getItemMeta().displayName().toString().contains("Razor Sharp Arrow")) {
                Bukkit.broadcast(Component.text("Sharp arrow found"));
                playerInventory.setItem(slot, poisonArrow);
            }
            else if(mainHand.getItemMeta().displayName().toString().contains("Poison Arrow")) {
                playerInventory.setItem(slot, shackleArrow);
            }
            else if(mainHand.getItemMeta().displayName().toString().contains("Shackle Arrow")) {
                playerInventory.setItem(slot, damageArrow);
            }
        }
    }

    @EventHandler
    public void castAbilityOne(PlayerInteractEvent event) {
        Action action = event.getAction();
        if(action.isLeftClick()){return;}
        Player player = event.getPlayer();
        if(SiegeClassUtil.abilityDeny(player, action, "archer") == false){
            return;
        }
        PlayerInventory playerInventory = player.getInventory();
        ItemStack mainHand = playerInventory.getItemInMainHand();
        ItemStack bow = playerInventory.getItem(0);

        if(SiegeClassUtil.abilityCheck(action, mainHand, "Explosive Shot")){
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
        }
        arrow.remove();

    }

    @EventHandler
    public void reload(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if(action.isLeftClick()){return;}
        Block block = event.getClickedBlock();


        if(action.isRightClick() && block.getType().equals(Material.BARREL) && Siege.siegeClassUtil.getPlayersSiegeClass(player).equalsIgnoreCase("archer")){
            if(reloadCooldowns.containsKey(player.getUniqueId())) {
                if(reloadCooldowns.get(player.getUniqueId()) > System.currentTimeMillis()) {
                    long timeLeft = (reloadCooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
                    player.sendMessage(Component.text(ChatColor.RED + "" + timeLeft + ChatColor.GOLD + " second cooldown left on " + "Reload" + "!"));
                    return;
                }
            }
            PlayerInventory playerInventory = player.getInventory();
            ItemStack item = playerInventory.getItem(1);
            int qty = 64 - item.getAmount();
            item.add(qty);
            reloadCooldowns.put(player.getUniqueId(), System.currentTimeMillis() + (reloadCooldown * 1000));
        }
    }



}
