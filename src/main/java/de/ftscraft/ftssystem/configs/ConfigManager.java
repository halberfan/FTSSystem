/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.configs;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ConfigManager {

    private ArrayList<String> autoMessages = new ArrayList<>();

    private FtsSystem plugin;

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
        cfg.options().copyDefaults(true);
        plugin.saveConfig();

        //Get Data
        cfg = plugin.getConfig();
        autoMessages.clear();
        List messages = cfg.getList("messages");
        String[] amessages = (String[]) messages.toArray(new String[messages.size()]);
        autoMessages.addAll(Arrays.asList(amessages));
        this.autoMessages = autoMessages;
    }

    public ArrayList<String> getAutoMessages() {
        return autoMessages;
    }

}
