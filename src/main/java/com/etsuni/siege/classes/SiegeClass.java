package com.etsuni.siege.classes;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class SiegeClass {

    public abstract void giveKit(Player player);
    public abstract void abilityOne(PlayerInteractEvent event);
    public abstract void abilityTwo(PlayerInteractEvent event);
    public abstract void ultimate(PlayerInteractEvent event);



}
