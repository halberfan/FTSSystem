/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CMDwartung implements CommandExecutor {

    private FtsSystem plugin;

    public CMDwartung(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("wartung").setExecutor(this);
    }

    int taskId = -1;
    int seconds = 30;

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (cs.hasPermission("ftssystem.wartung.start")) {

            if (args.length == 0) {
                cs.sendMessage(Messages.PREFIX + "Bist du sicher? Wenn ja, schreibt /wartung confirm");
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("confirm")) {

                //wenn server noch nicht in wartung
                if (!plugin.isInWartung()) {

                    if (taskId != -1) {
                        return true;
                    }

                    taskId = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                        Bukkit.broadcastMessage("§cEs wird gleich eine Wartungsarbeit geben. Ihr werdet nach Ablauf des Timers gekickt! §b" + seconds);
                        seconds--;
                        if (seconds == 0) {
                            startWartung();
                            seconds = 30;
                            cancelTask();
                        }
                    }, 20, 20).getTaskId();

                } else {

                    endWartung();

                    World w = null;

                }
            }

        } else cs.sendMessage(Messages.NO_PERM);


        return false;
    }

    private void startWartung() {
        plugin.setWartung(true);

        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("ftssystem.wartung.bypass")) {
                onlinePlayer.kickPlayer("§cWir gehen in Wartungsarbeiten! \n §bDu wirst wahrscheinlich im Forum oder im Discord mehr Infos erhalten");
            }
        }

    }

    private void endWartung() {
        plugin.setWartung(false);

        Bukkit.broadcastMessage("§bDie Wartungsarbeiten wurden beendet!");
    }

    private void cancelTask() {
        Bukkit.getScheduler().cancelTask(taskId);
        this.taskId = -1;
    }

}
