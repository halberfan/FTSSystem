/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.configs;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.travelsystem.TravelType;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigManager {

    private ArrayList<String> autoMessages = new ArrayList<>();

    private int latestPunID = 0;

    private boolean wartung = false;

    private final FtsSystem plugin;

    public ConfigManager(FtsSystem plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration cfg = plugin.getConfig();
        //Defaults
        ArrayList<String> autoMessages = new ArrayList<>();
        autoMessages.add("§6Du möchtest den Server kostenlos unterstützen? §cHier kannst du voten: https://ftscraft.de/vote/");
        cfg.addDefault("messages", autoMessages);
        cfg.addDefault("latestPunishID", 0);
        cfg.addDefault("wartung", false);
        for (TravelType value : TravelType.values()) {
            cfg.addDefault("travel_price." + value.name().toLowerCase(), value.getPrice());
        }
        cfg.options().copyDefaults(true);
        plugin.saveConfig();

        /*
         * Get Data
         */

        //AutoMessage
        cfg = plugin.getConfig();
        autoMessages.clear();
        autoMessages.addAll(cfg.getStringList("messages"));
        this.autoMessages = autoMessages;

        //Latest ID for Punishments
        latestPunID = cfg.getInt("latestPunishID");

        wartung = cfg.getBoolean("wartung");
        for (TravelType value : TravelType.values()) {
            value.setPrice(cfg.getInt("travel_price." + value.name().toLowerCase()));
        }

    }

    public void setConfig(ConfigVal val, Object obj) {
        plugin.getConfig().set(val.getPath(), obj);
    }

    public ArrayList<String> getAutoMessages() {
        return autoMessages;
    }

    public int getLatestPunID() {
        return latestPunID;
    }

    public void save() {
        plugin.saveConfig();
    }

    public boolean isWartung() {
        return wartung;
    }
}

