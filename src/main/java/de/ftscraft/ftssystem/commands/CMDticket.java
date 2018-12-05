/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDticket implements CommandExecutor {

    private FtsSystem plugin;

    public CMDticket(FtsSystem plugin)
    {
        this.plugin = plugin;
        plugin.getCommand("ticket").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args)
    {
        if(!(cs instanceof Player)) {
            cs.sendMessage("§cDieser CMD ist nur für Spieler");
            return true;
        }

        Player p = (Player)cs;

        if(args.length >= 1) {

            String sub = args[0];

            if(sub.equalsIgnoreCase("")) {

            }

        }

        return false;
    }
}
