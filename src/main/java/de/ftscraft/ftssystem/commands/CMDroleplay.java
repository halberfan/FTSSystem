/*
 * Copyright (c) 2020.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDroleplay implements CommandExecutor {


    private final FtsSystem plugin;

    public CMDroleplay(FtsSystem plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("roleplay").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {

        if (!(commandSender instanceof Player p)) {
            return true;
        }

        if (p.hasPermission("ftssystem.roleplaymode")) {

            plugin.getScoreboardManager().switchToRoleplayMode(p);

        }

        return false;
    }
}
