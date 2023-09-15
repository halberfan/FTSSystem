/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.punishment.Punishment;
import de.ftscraft.ftssystem.punishment.PunishmentInventory;
import de.ftscraft.ftssystem.utils.FTSCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDpu implements FTSCommand {

    private final FtsSystem plugin;

    public CMDpu(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("pu").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if(!(cs instanceof Player p)) {
            cs.sendMessage(Messages.ONLY_PLAYER);
            return true;
        }

        if(!p.hasPermission("ftssystem.punish")) {
            p.sendMessage(Messages.NO_PERM);
            return true;
        }

        switch (args.length) {
            case 1:
                OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                if (t == null) {
                    p.sendMessage(Messages.PLAYER_NOT_FOUND);
                    return true;
                }

                p.openInventory(new PunishmentInventory(plugin, t.getName()).getInv(PunishmentInventory.PunishmentInvType.MAIN));

                break;
            case 2:
                if (args[0].equalsIgnoreCase("remove")) {

                    p.sendMessage("§cBist du sicher? Das kann nicht rückgängig gemacht werden! Wenn ja, klicke: ");
                    TextComponent tc = new TextComponent("Hier");
                    tc.setColor(ChatColor.DARK_RED);
                    tc.setBold(true);
                    tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pu remove " + args[1] + " confirm"));
                    p.spigot().sendMessage(tc);
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("remove") && args[2].equalsIgnoreCase("confirm")) {

                    Integer id = Integer.valueOf(args[1]);

                    Punishment pu = plugin.getPunishmentManager().getPunishmentById(id);

                    if (pu == null) {
                        p.sendMessage("§cIrgendwas ist schief gelaufen. Probier es nochmal");
                        return true;
                    }

                    pu.setActive(false);
                    plugin.getPunishmentManager().savePlayer(pu.getPlayer());

                    p.sendMessage("§cOkay. Die Strafe wurde Deaktiviert");


                }
                break;
        }

        return false;
    }
}
