package com.etsuni.siege;

import com.etsuni.siege.classes.*;
import com.etsuni.siege.matches.Match;
import com.etsuni.siege.teams.SiegeTeam;
import com.etsuni.siege.tests.Tests;
import com.sun.jndi.ldap.Ber;
import de.slikey.effectlib.EffectManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public final class Siege extends JavaPlugin {

    public static Knight knight;
    public static Archer archer;
    public static Berserker berserker;
    public static Paladin paladin;

    public static Assassin assassin;
    public static SiegeClassMenu siegeClassMenu;

    public static SiegeClassUtil siegeClassUtil;

    public static EffectManager effectManager;
    @Override
    public void onEnable() {
        // Plugin startup logic
        knight = new Knight();
        archer = new Archer();
        berserker = new Berserker();
        paladin = new Paladin();
        assassin = new Assassin();

        siegeClassMenu = new SiegeClassMenu();
        siegeClassUtil = new SiegeClassUtil();

        effectManager = new EffectManager(this);

        this.getCommand("siege").setExecutor(new Tests());
        this.getServer().getPluginManager().registerEvents(new Knight(), this);
        this.getServer().getPluginManager().registerEvents(new Berserker(), this);
        this.getServer().getPluginManager().registerEvents(new Archer(), this);
        this.getServer().getPluginManager().registerEvents(new Paladin(), this);
        this.getServer().getPluginManager().registerEvents(new Assassin(), this);
        }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
