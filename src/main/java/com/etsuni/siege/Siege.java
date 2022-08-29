package com.etsuni.siege;

import com.etsuni.siege.classes.*;
import com.etsuni.siege.matches.Match;
import com.etsuni.siege.teams.SiegeTeam;
import com.etsuni.siege.tests.Tests;
import com.sun.jndi.ldap.Ber;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Siege extends JavaPlugin {

    public static Knight knight;
    public static Archer archer;
    public static Berserker berserker;

    public static SiegeClassMenu siegeClassMenu;

    public static SiegeClassUtil siegeClassUtil;
    @Override
    public void onEnable() {
        // Plugin startup logic
        knight = new Knight();
        archer = new Archer();
        berserker = new Berserker();
        siegeClassMenu = new SiegeClassMenu();
        siegeClassUtil = new SiegeClassUtil();

        this.getCommand("siege").setExecutor(new Tests());
        this.getCommand("attack").setExecutor(new Match(true, new ArrayList<Player>(), 100,
                new SiegeTeam(new ArrayList<Player>(), "Attack"),
                new SiegeTeam(new ArrayList<Player>(), "Defense")));
        this.getCommand("defense").setExecutor(new Match(true, new ArrayList<Player>(), 100,
                new SiegeTeam(new ArrayList<Player>(), "Attack"),
                new SiegeTeam(new ArrayList<Player>(), "Defense")));
        this.getServer().getPluginManager().registerEvents(new Knight(), this);
        this.getServer().getPluginManager().registerEvents(new Berserker(), this);
        this.getServer().getPluginManager().registerEvents(new Archer(), this);
        }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
