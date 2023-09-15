package de.ftscraft.ftssystem.utils;

import de.ftscraft.ftssystem.main.FtsSystem;
import kong.unirest.UnirestException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PremiumManager {

    private FtsSystem plugin;
    private HashMap<UUID, Long> premiumPlayers = new HashMap<>();

    public PremiumManager(FtsSystem plugin) {
        this.plugin = plugin;
    }

    public void removePremiumPlayer(UUID uuid) {
        premiumPlayers.remove(uuid);
    }

    public void addPremiumPlayer(UUID uuid, long duration) {
        if(premiumPlayers.containsKey(uuid)) {
            premiumPlayers.put(uuid, duration);
            return;
        }
        premiumPlayers.put(uuid, duration);
        try {
            plugin.getForumHook().addUserToPremiumGroup(Bukkit.getOfflinePlayer(uuid).getName());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkPremiumPlayers() {
        ArrayList<UUID> uuidsToRemove = new ArrayList<>();
        for (UUID uuid : premiumPlayers.keySet()) {
            if(premiumPlayers.get(uuid) < System.currentTimeMillis() / 1000) {
                uuidsToRemove.add(uuid);
            }
        }
        for (UUID uuid : uuidsToRemove) {
            premiumPlayers.remove(uuid);
        }
        try {
            plugin.getForumHook().updatePremiumGroup();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<UUID, Long> getPremiumPlayers() {
        return premiumPlayers;
    }
}

