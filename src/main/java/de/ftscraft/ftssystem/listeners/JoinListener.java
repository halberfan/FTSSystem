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
import org.bukkit.Sound;
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

        plugin.getScoreboardManager().setPlayerPrefix(p);

        String joinMessage = "%s" + ChatColor.WHITE + " hat Parsifal betreten!";

        boolean isChanged = false;

        if(p.hasPermission("ftssystem.join.lightblue")) {
            isChanged = true;
            joinMessage = joinMessage.replace("%s", ChatColor.AQUA + p.getName());
        }
        if(p.hasPermission("ftssystem.join.darkred")) {
            isChanged = true;
            joinMessage = joinMessage.replace("%s", ChatColor.DARK_RED + p.getName());
        }
        if(p.hasPermission("ftssystem.join.darkgreen")) {
            isChanged = true;
            joinMessage = joinMessage.replace("%s", ChatColor.DARK_GREEN + p.getName());
        }
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

        if(!u.isApproved()) {

            Bukkit.getScheduler().runTaskLater(plugin, () -> {

                p.sendMessage("§4Achtung: §cDu bist noch nicht freigeschalten! Wenn du das erste mal auf dem Server bist, mach das Tutorial. Wenn du soweit bist, lese das Regelwerk auf https://ftscraft.de/regelwerk/ und suche nach einem Wort welches nicht in den Kontext passt! Dies ist das Passwort. Schalte dich frei mit §e/passwort PASSWORT");

                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

            }, 20 * 4);

        }

    }

}
