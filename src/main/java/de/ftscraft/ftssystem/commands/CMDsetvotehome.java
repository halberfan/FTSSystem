package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDsetvotehome implements CommandExecutor {

    FtsSystem plugin;

    public CMDsetvotehome(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("setvotehome").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(cs instanceof Player player)) {
            return true;
        }

        if(args.length != 0) {
            return true;
        }

        User user = plugin.getUser(player);

        user.setVotehome(player.getLocation());

        player.sendMessage(Messages.PREFIX+"Du hast erfolgreich dein Votehome gesetzt! Wenn du zugriff auf dein Votehome hast, kannst du mit /votehome wieder hierhin reisen!");

        return false;
    }

}
