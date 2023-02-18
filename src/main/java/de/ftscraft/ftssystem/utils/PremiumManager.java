package de.ftscraft.ftssystem.utils;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
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
        premiumPlayers.forEach((uuid, aLong) -> {
            if(aLong < System.currentTimeMillis() / 1000) {
                premiumPlayers.remove(uuid);
            }
        });
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

