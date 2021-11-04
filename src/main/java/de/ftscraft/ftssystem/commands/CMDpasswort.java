/*
 * Copyright (c) 2020.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CMDpasswort implements CommandExecutor {

    private final String password = "beere", password_blockreich = "glasflasche";
    private ArrayList<String> commands = new ArrayList<>(Arrays.asList("lp user %s promote spieler", "warp taufeld %s"));

    private FtsSystem plugin;

    public CMDpasswort(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("passwort").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player)) {
            return true;
        }

        Player p = (Player) commandSender;

        if(args.length == 1) {

            if(args[0].equalsIgnoreCase(password) || args[0].equalsIgnoreCase(password_blockreich)) {

                User user = plugin.getUser(p);

                boolean neuling = !user.isApproved();

                if(neuling) {

                    for (String s : commands) {

                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), s.replace("%s", p.getName()));

                    }

                    user.setApproved(true);

                    p.sendMessage("§cDu hast dich erfolgreich freigeschalten!");

                } else {
                    p.sendMessage("§cDu hast dich bereits freigeschalten!");
                }

            } else {

                p.sendMessage("§cDas war nicht das richtige Passwort! Bitte versuchen Sie es erneut");

            }

        } else
            p.sendMessage("§cBitte benutze den Befehl so: /passwort [Passwort]");

        return false;
    }

}
