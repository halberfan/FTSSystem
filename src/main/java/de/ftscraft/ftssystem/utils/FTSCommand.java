/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface FTSCommand extends CommandExecutor {

    boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, String[] args);

}
