package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDvotehome implements CommandExecutor {

    private FtsSystem plugin;

    public CMDvotehome(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("votehome").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(cs instanceof Player player)) {
            return true;
        }

        if (cs.hasPermission("essentials.sethome.multiple")) {
            cs.sendMessage(Messages.PREFIX + "Du hast breits Zugriff auf ein zweites Home via /sethome.");
            return true;
        }

        if (!cs.hasPermission("ftssystem.votehome")) {
            cs.sendMessage(Messages.PREFIX + "Anscheinend hast du derzeit keine Rechte dein Votehome zu nutzen! Du musst dafür aktiv voten.");
            return true;
        }

        if (args.length == 0) {

            User user = plugin.getUser(player);

            if (!user.hasVotehome()) {
                player.sendMessage(Messages.PREFIX + "Du hast derzeit kein Votehome gesetzt! Mach das mit /setvotehome");
                return true;
            }

            player.teleport(user.getVotehome());
            player.sendMessage(Messages.PREFIX + "Du wurdest zu deinem Votehome teleportiert!");

        }

        return false;
    }
}
