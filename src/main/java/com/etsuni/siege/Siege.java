package com.etsuni.siege;

import com.etsuni.siege.classes.*;
import com.etsuni.siege.matches.Match;
import com.etsuni.siege.matches.MatchVoter;
import com.etsuni.siege.matches.TDM;
import com.etsuni.siege.menus.MatchesMenu;
import com.etsuni.siege.tests.Tests;
import de.slikey.effectlib.EffectManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;


public final class Siege extends JavaPlugin {

    public static Knight knight;
    public static Archer archer;
    public static Berserker berserker;
    public static Paladin paladin;

    public static Assassin assassin;
    public static SiegeClassMenu siegeClassMenu;

    public static SiegeClassUtil siegeClassUtil;

    public static EffectManager effectManager;

    public static MatchesMenu matchesMenu;

    private File mapConfigFile;
    private FileConfiguration mapConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        knight = new Knight();
        archer = new Archer();
        berserker = new Berserker();
        paladin = new Paladin();
        assassin = new Assassin();
        matchesMenu = new MatchesMenu();

        siegeClassMenu = new SiegeClassMenu();
        siegeClassUtil = new SiegeClassUtil();

        effectManager = new EffectManager(this);

        matchesMenu.createMenu();

        //INITIALIZE MATCH LOOP
        Match.gameLoop();

        this.getCommand("siege").setExecutor(new Tests());
        this.getCommand("vote").setExecutor(new MatchVoter());
        this.getServer().getPluginManager().registerEvents(new Knight(), this);
        this.getServer().getPluginManager().registerEvents(new Berserker(), this);
        this.getServer().getPluginManager().registerEvents(new Archer(), this);
        this.getServer().getPluginManager().registerEvents(new Paladin(), this);
        this.getServer().getPluginManager().registerEvents(new Assassin(), this);
        this.getServer().getPluginManager().registerEvents(new MatchesMenu(), this);
        this.getServer().getPluginManager().registerEvents(new TDM(), this);

        createMapConfig();

        }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FileConfiguration getMapConfig() {
        return this.mapConfig;
    }

    private void createMapConfig() {
        mapConfigFile = new File(getDataFolder(), "maps.yml");
        if(!mapConfigFile.exists()) {
            mapConfigFile.getParentFile().mkdirs();
            saveResource("maps.yml", false);
        }

        mapConfig = new YamlConfiguration();
        try {
            mapConfig.load(mapConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
