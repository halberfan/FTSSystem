/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDfts implements CommandExecutor {

    private final FtsSystem plugin;

    public CMDfts(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("fts").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!(cs instanceof Player p)) {
            cs.sendMessage("§cDer Command ist nur für Spieler");
            return true;
        }

        User u = plugin.getUser(p);

        u.openMenu();

        return true;
    }
}
