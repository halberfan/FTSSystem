/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import de.ftscraft.ftssystem.poll.Umfrage;
import de.ftscraft.ftssystem.utils.PremiumManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
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

        plugin.getScoreboardManager().setPlayerPrefix(p);

        String joinMessage = "%s" + ChatColor.WHITE + " hat Parsifal betreten!";

        boolean isChanged = false;

        if (p.hasPermission("ftssystem.join.lightblue")) {
            isChanged = true;
            joinMessage = joinMessage.replace("%s", ChatColor.AQUA + p.getName());
        }
        if (p.hasPermission("ftssystem.join.darkred")) {
            isChanged = true;
            joinMessage = joinMessage.replace("%s", ChatColor.DARK_RED + p.getName());
        }
        if (p.hasPermission("ftssystem.join.darkgreen")) {
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

        if (!isChanged) {

            joinMessage = joinMessage.replace("%s", ChatColor.GOLD + p.getName());

        }

        event.setJoinMessage(joinMessage);

        User u = new User(plugin, event.getPlayer());

        if (!u.getDisturbStatus().equals(User.ChannelStatusSwitch.ON)) {

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

        if (u.hasNoobProtection()) {
            if (p.hasPermission("ftssystem.bürger")) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    u.setNoobProtection(false);
                    p.sendMessage(Messages.PREFIX + "Da du jetzt Bürger bist und du immer noch die Noobprotection an hattest, wurde sie jetzt entfernt");
                }, 20 * 4);
            }
        }

        PremiumManager premiumManager = plugin.getPremiumManager();
        if(p.hasPermission("group.premium")) {
            LuckPerms luckPerms = LuckPermsProvider.get();
            for (Node node : luckPerms.getUserManager().getUser(p.getUniqueId()).getNodes()) {
                if(node instanceof InheritanceNode) {
                    InheritanceNode inheritanceNode = (InheritanceNode) node;
                    if(inheritanceNode.getGroupName().equalsIgnoreCase("Premium")) {
                        premiumManager.addPremiumPlayer(p.getUniqueId(), inheritanceNode.getExpiry().getEpochSecond());
                    }
                }
            }
        } else {
            premiumManager.removePremiumPlayer(p.getUniqueId());
        }

    }

}
