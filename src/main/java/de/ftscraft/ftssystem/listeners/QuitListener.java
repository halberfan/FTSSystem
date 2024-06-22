/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
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

        Player p = event.getPlayer();
        TextComponent leaveMessage = Component.text(p.getName());

        if (p.hasPermission("ftssystem.join.lightblue")) {
            leaveMessage = leaveMessage.color(NamedTextColor.AQUA);
        } else if (p.hasPermission("ftssystem.join.darkred")) {
            leaveMessage = leaveMessage.color(NamedTextColor.DARK_RED);
        } else if (p.hasPermission("ftssystem.join.darkgreen")) {
            leaveMessage = leaveMessage.color(NamedTextColor.DARK_GREEN);
        } else if (p.hasPermission("ftssystem.join.red")) {
            leaveMessage = leaveMessage.color(NamedTextColor.RED);
        } else if (p.hasPermission("ftssystem.join.blue")) {
            leaveMessage = leaveMessage.color(NamedTextColor.BLUE);
        } else {
            leaveMessage = leaveMessage.color(NamedTextColor.GOLD);
        }
        leaveMessage = leaveMessage.append(Component.text(" hat Eldoria verlassen").color(NamedTextColor.WHITE));

        event.quitMessage(leaveMessage);

    }
}
