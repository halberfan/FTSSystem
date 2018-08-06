/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public interface FTSCommand extends CommandExecutor {

    boolean onCommand(CommandSender cs, Command cmd, String label, String[] args);

}
