/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import de.ftscraft.ftssystem.utils.FTSCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDtogglesidebar implements FTSCommand {

    private FtsSystem plugin;

    public CMDtogglesidebar(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("togglesidebar").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(!(cs instanceof Player)) {
            cs.sendMessage(Messages.ONLY_PLAYER);
            return true;
        }

        Player p = (Player)cs;

        plugin.getUser(p).setScoreboardEnabled(!plugin.getUser(p).isScoreboardEnabled());

        p.sendMessage("§cDie Einstellung wurde übernommen");

        return false;
    }
}
