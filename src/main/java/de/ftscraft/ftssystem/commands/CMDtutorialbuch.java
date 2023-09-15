/*
 * Copyright (c) 2019.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDtutorialbuch implements CommandExecutor {


    private final FtsSystem plugin;

    private String bookCommand;

    public CMDtutorialbuch(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("tutorialbook").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if (!(cs instanceof Player p)) {

            cs.sendMessage("§cDieser Befehl ist nur für SPieler");
            return true;
        }

        if (!(cs.hasPermission("ftssystem.book"))) {

            cs.sendMessage("§cDafür hast du keine Rechte");
            return true;

        }


        String name = p.getName();

        String bookCMD;

        if (plugin.isBlockreich()) {
            bookCMD = plugin.getFileManager().getBookBlockreichCMD().replace("<player>", name);
        } else {
            bookCMD = plugin.getFileManager().getBookCMD().replace("<player>", name);
        }

        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), bookCMD);

        p.sendMessage("§cDu hast das Buch erhalten");

        return false;
    }
}
