package com.etsuni.siege.classes;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayersClassList {

    private Map<UUID, SiegeClasses> playerClassMap = new HashMap<>();
    private static PlayersClassList instance = new PlayersClassList();

    private PlayersClassList() {

    }

    public static PlayersClassList getInstance() {
        return instance;
    }

    public Map<UUID, SiegeClasses> getPlayerClassMap() {
        return playerClassMap;
    }

    public SiegeClasses getPlayersClass(Player player) {
        SiegeClasses siegeClass = SiegeClasses.DEFAULT;

        for(UUID uuid : playerClassMap.keySet()) {
            if(uuid.equals(player.getUniqueId())) {
                siegeClass = playerClassMap.get(uuid);
            }
        }
        return siegeClass;
    }

    public void add(Player player, SiegeClasses siegeClass) {
        for(UUID uuid : playerClassMap.keySet()) {
            if(uuid.equals(player.getUniqueId())) {
                playerClassMap.replace(uuid, siegeClass);
                return;
            }
        }
        playerClassMap.put(player.getUniqueId(), siegeClass);
    }

    public void remove(Player player, SiegeClasses siegeClass) {
        playerClassMap.remove(player.getUniqueId());
    }

    public void clear() {
        playerClassMap.clear();
    }
}
