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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
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

    private final FtsSystem plugin;

    public JoinListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

        plugin.getScoreboardManager().setPlayerPrefix(p);

        event.joinMessage(generateJoinMessage(p));

        User u = new User(plugin, event.getPlayer());

        if (!u.getDisturbStatus().equals(User.ChannelStatusSwitch.ON)) {

            Umfrage umfrage = plugin.getUmfrage();
            if (umfrage != null && umfrage.isStarted()) {
                if (!umfrage.getTeilnehmer().contains(event.getPlayer())) {

                    Bukkit.getScheduler().runTaskLater(plugin, () -> umfrage.sendToPlayer(event.getPlayer()), 20 * 2);


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
        if (p.hasPermission("group.premium")) {
            LuckPerms luckPerms = LuckPermsProvider.get();
            for (Node node : luckPerms.getUserManager().getUser(p.getUniqueId()).getNodes()) {
                if (node instanceof InheritanceNode inheritanceNode) {
                    if (inheritanceNode.getGroupName().equalsIgnoreCase("Premium")) {
                        if (inheritanceNode.getExpiry() != null)
                            premiumManager.addPremiumPlayer(p.getUniqueId(), inheritanceNode.getExpiry().getEpochSecond());
                    }
                }
            }
        } else {
            premiumManager.removePremiumPlayer(p.getUniqueId());
        }

    }

    private TextComponent generateJoinMessage(Player p) {
        TextComponent joinMessage = Component.text(p.getName());

        if (p.hasPermission("ftssystem.join.lightblue")) {
            joinMessage = joinMessage.color(NamedTextColor.AQUA);
        } else if (p.hasPermission("ftssystem.join.darkred")) {
            joinMessage = joinMessage.color(NamedTextColor.DARK_RED);
        } else if (p.hasPermission("ftssystem.join.darkgreen")) {
            joinMessage = joinMessage.color(NamedTextColor.DARK_GREEN);
        } else if (p.hasPermission("ftssystem.join.red")) {
            joinMessage = joinMessage.color(NamedTextColor.RED);
        } else if (p.hasPermission("ftssystem.join.blue")) {
            joinMessage = joinMessage.color(NamedTextColor.BLUE);
        } else {
            joinMessage = joinMessage.color(NamedTextColor.GOLD);
        }
        return joinMessage.append(Component.text(" hat Eldoria verlassen").color(NamedTextColor.WHITE));
    }

}
