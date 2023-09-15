/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final FtsSystem plugin;

    public QuitListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        User u = plugin.getUser(event.getPlayer());

        if (u == null)
            return;

        if (!u.getFights().isEmpty()) {
            event.getPlayer().setHealth(0);
            Bukkit.broadcastMessage("§cDer Spieler " + event.getPlayer().getName() + " hat sich im Kampf ausgeloggt und ist deshalb gestorben ;(");
        }

        u.save();
        plugin.getUser().remove(event.getPlayer().getName());

        String leaveMessage = "%s" + ChatColor.WHITE + " hat Parsifal verlassen!";

        boolean isChanged = false;

        Player p = event.getPlayer();

        if (p.hasPermission("ftssystem.join.lightblue")) {
            isChanged = true;
            leaveMessage = leaveMessage.replace("%s", ChatColor.AQUA + p.getName());
        }
        if (p.hasPermission("ftssystem.join.darkred")) {
            isChanged = true;
            leaveMessage = leaveMessage.replace("%s", ChatColor.DARK_RED + p.getName());
        }
        if (p.hasPermission("ftssystem.join.darkgreen")) {
            isChanged = true;
            leaveMessage = leaveMessage.replace("%s", ChatColor.DARK_GREEN + p.getName());
        }
        if (p.hasPermission("ftssystem.join.red")) {
            isChanged = true;
            leaveMessage = leaveMessage.replace("%s", ChatColor.RED + p.getName());
        }
        if (p.hasPermission("ftssystem.join.blue")) {
            isChanged = true;
            leaveMessage = leaveMessage.replace("%s", ChatColor.BLUE + p.getName());

        }
        if (p.hasPermission("ftssystem.join.darkred")) {
            isChanged = true;
            leaveMessage = leaveMessage.replace("%s", ChatColor.DARK_RED + p.getName());
        }

        if (!isChanged) {

            leaveMessage = leaveMessage.replace("%s", ChatColor.GOLD + p.getName());

        }

        event.setQuitMessage(leaveMessage);

    }
}
