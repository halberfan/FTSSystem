/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import de.ftscraft.ftssystem.poll.Umfrage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private FtsSystem plugin;

    public JoinListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

        String joinMessage = "%s" + ChatColor.WHITE + " hat das Kaiserreich betreten!";

        boolean isChanged = false;

        if (p.hasPermission("ftssystem.join.red")) {
            isChanged = true;
            joinMessage = joinMessage.replace("%s", ChatColor.RED + p.getName());

        }

        if (p.hasPermission("ftssystem.join.blue")) {
            isChanged = true;
            joinMessage = joinMessage.replace("%s", ChatColor.BLUE + p.getName());

        }
        if (p.hasPermission("ftssystem.join.darkred")) {
            isChanged = true;
            joinMessage = joinMessage.replace("%s", ChatColor.DARK_RED + p.getName());
        }

        if(!isChanged) {

            joinMessage = joinMessage.replace("%s", ChatColor.GOLD + p.getName());

        }

        event.setJoinMessage(joinMessage);

        User u = new User(plugin, event.getPlayer());

        Umfrage umfrage = plugin.getUmfrage();
        if (umfrage != null && umfrage.isStarted()) {
            if (!umfrage.getTeilnehmer().contains(event.getPlayer())) {

                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {


                        umfrage.sendToPlayer(event.getPlayer());

                    }
                }, 20 * 2);


            }
        }

    }

}
