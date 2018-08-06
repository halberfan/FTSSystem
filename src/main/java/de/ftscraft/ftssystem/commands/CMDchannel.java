/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.channel.Channel;
import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import de.ftscraft.ftssystem.utils.FTSCommand;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDchannel implements FTSCommand {

    private FtsSystem plugin;

    public CMDchannel(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("channel").setExecutor(this);
    }

    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(Messages.ONLY_PLAYER);
            return true;
        }

        Player p = (Player) cs;

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("list")) {
                p.sendMessage(Messages.LIST_CHANNEL);
                for (Channel a : plugin.getChatManager().channels) {
                    if (p.hasPermission(a.getPermission()))
                        p.sendMessage("- " + ChatColor.AQUA + ChatColor.BOLD + a.getName());
                }
            } else if (args[0].equalsIgnoreCase("join")) {
                if (args.length == 2) {
                    User u = plugin.getUser(p);
                    Channel channel = plugin.getChatManager().getChannel(args[1]);
                    if (channel == null) {
                        p.sendMessage(Messages.NO_CHANNEL);
                        return true;
                    }
                    if (u.getEnabledChannels().contains(channel)) {
                        cs.sendMessage(Messages.ALREADY_IN_CHANNEL);
                        return true;
                    }
                    if (!p.hasPermission(channel.getPermission())) {
                        cs.sendMessage(Messages.NO_PERM);
                        return true;
                    }

                    u.joinChannel(channel);
                    p.sendMessage(Messages.JOINED_CHANNEL.replace("%s", channel.getName()));
                } else p.sendMessage(Messages.HELP_CHANNEL);
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (args.length == 2) {
                    User u = plugin.getUser(p);
                    Channel channel = plugin.getChatManager().getChannel(args[1]);
                    if (channel == null) {
                        p.sendMessage(Messages.NO_CHANNEL);
                        return true;
                    }
                    if (!u.getEnabledChannels().contains(channel)) {
                        cs.sendMessage(Messages.NOT_IN_CHANNEL);
                        return true;
                    }

                    u.leaveChannel(channel);
                    p.sendMessage(Messages.LEFT_CHANNEL.replace("%s", channel.getName()));
                } else p.sendMessage(Messages.HELP_CHANNEL);
            } else if (args[0].equalsIgnoreCase("aktiv")) {
                if (args.length == 2) {
                    User u = plugin.getUser(p);
                    Channel channel = plugin.getChatManager().getChannel(args[1]);
                    if (channel == null) {
                        p.sendMessage(Messages.NO_CHANNEL);
                        return true;
                    }
                    if (!p.hasPermission(channel.getPermission())) {
                        cs.sendMessage(Messages.NO_PERM);
                        return true;
                    }

                    u.setActiveChannel(channel);
                    p.sendMessage(Messages.NOW_ACTIVE_CHANNEL.replace("%s", channel.getName()));

                } else p.sendMessage(Messages.HELP_CHANNEL);
            } else if (args[0].equalsIgnoreCase("toggle")) {
                if (args.length == 2) {
                    User u = plugin.getUser(p);
                    Channel channel = plugin.getChatManager().getChannel(args[1]);
                    if (channel == null) {
                        p.sendMessage(Messages.NO_CHANNEL);
                        return true;
                    }
                    if (!p.hasPermission(channel.getPermission())) {
                        cs.sendMessage(Messages.NO_PERM);
                        return true;
                    }

                    u.toggleChannel(channel);
                }
            } else p.sendMessage(Messages.HELP_CHANNEL);
        } else p.sendMessage(Messages.HELP_CHANNEL);

        return false;
    }
}
