/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private FtsSystem plugin;

    public QuitListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        User u = plugin.getUser(event.getPlayer());

        if(u == null)
            return;

        if(!u.getFights().isEmpty()) {
            event.getPlayer().setHealth(0);
            Bukkit.broadcastMessage("§cDer Spieler " + event.getPlayer().getName() + " hat sich im Kampf ausgeloggt und ist deshalb gestorben ;(");
        }

        u.save();
        plugin.getUser().remove(event.getPlayer().getName());
    }
}
