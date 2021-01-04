/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.menus.fts.FTSMenuInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDfts implements CommandExecutor {

    private FtsSystem plugin;

    public CMDfts(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("fts").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if(!(cs instanceof Player)) {
            cs.sendMessage("§cDer Command ist nur für Spieler");
            return true;
        }

        Player p = (Player) cs;

        p.openInventory((new FTSMenuInventory(p, plugin)).getInventory());

        return true;
    }
}
