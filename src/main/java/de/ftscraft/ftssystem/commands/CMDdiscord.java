/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CMDdiscord implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, String[] args) {
        return false;
    }

    private String help() {
        return "/discord verifizieren [Code]";
    }

}
