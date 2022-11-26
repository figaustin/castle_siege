package com.etsuni.siege.classes;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    public Boolean setCooldown(Player player, Map<UUID, Long> cooldownMap, Integer cooldownTime, String abilityName){
        UUID playerUUID = player.getUniqueId();
        if(cooldownMap.containsKey(playerUUID)){
            if(cooldownMap.get(playerUUID) > System.currentTimeMillis()) {
                long timeLeft = (cooldownMap.get(playerUUID)) - System.currentTimeMillis() / 1000;
                player.sendMessage(Component.text(ChatColor.RED + "" + timeLeft + ChatColor.GOLD + " second cooldown left on " + abilityName + "!"));
                return false;
            }
        }
        cooldownMap.put(playerUUID, System.currentTimeMillis() + (cooldownTime * 1000));
        return true;
    }
}
