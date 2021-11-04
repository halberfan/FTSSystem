/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDbroadcast implements CommandExecutor {

    private FtsSystem plugin;

    public CMDbroadcast(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("broadcast").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if(!cs.hasPermission("ftssystem.broadcast")) {
            cs.sendMessage(Messages.PREFIX+"Dafür hast du keine Rechte. Du kannst sie aber in Lohengrin für kurze Zeit auch kaufen");
            return true;
        }

        if (args.length >= 1) {

            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < args.length; i++) {
                if (i == args.length - 1) {
                    builder.append(args[i]);
                } else
                    builder.append(args[i] + " ");
            }

            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                onlinePlayer.sendMessage("[§cEin Bote berichtet§r] §a" + ChatColor.translateAlternateColorCodes('&', builder.toString()));
            }

        }

        return false;
    }
}