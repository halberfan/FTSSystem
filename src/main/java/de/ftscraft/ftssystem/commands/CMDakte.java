/*
 * Copyright (c) 2019.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.punishment.PunishmentInventory;
import de.ftscraft.ftssystem.utils.FTSCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDakte implements FTSCommand {

    private FtsSystem plugin;

    public CMDakte(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("akte").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if (!(cs instanceof Player)) {
            cs.sendMessage(Messages.ONLY_PLAYER);
            return true;
        }

        Player p = (Player) cs;

        if (args.length == 0) {

            p.openInventory(new PunishmentInventory(plugin, cs.getName()).getInv(PunishmentInventory.PunishmentInvType.AKTE));

        }

        return false;
    }


}