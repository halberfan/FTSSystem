/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.reisepunkte.TPPunkt;
import de.ftscraft.ftssystem.utils.FTSCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDreisepunkt implements FTSCommand {

    private FtsSystem plugin;

    public CMDreisepunkt(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("reisepunkt").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if(!(cs instanceof Player)) {
            cs.sendMessage(Messages.ONLY_PLAYER);
            return true;
        }

        Player p = (Player)cs;

        if(args.length >= 1) {

            if(args[0].equalsIgnoreCase("create")) {

                if(args.length >= 2) {

                    if(args[1].equalsIgnoreCase("punkt")) {

                        if(args.length == 3) {

                            String name = args[2];

                            TPPunkt tpPunkt = new TPPunkt(p.getLocation(), name);

                            plugin.getReisepunktManager().getTpPunkte().put(name, tpPunkt);

                            p.sendMessage("§cDer TP-Punkt wurde erfolgreich erstellt");

                        } else {

                            p.sendMessage("§eDas funktioniert so nicht. Probier: /reisepunkt create punkt <Name>");
                            return true;
                        }

                     } else if(args[1].equalsIgnoreCase("fahrzeug")) {



                    }

                }

            }

        }

        return false;
    }
}
