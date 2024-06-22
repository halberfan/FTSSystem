/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static de.ftscraft.ftssystem.utils.Utils.msg;

public class CMDdiscord implements CommandExecutor {

    private final ArrayList<Integer> codes = new ArrayList<>();
    private final FtsSystem plugin;

    public CMDdiscord(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("discord").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(cs instanceof Player p)) {
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(msg("<red>Du kannst unserem Discord joinen mit dem Link: %s. Wenn du dich verifizieren möchtest kannst du /discord verifizieren eingeben."));
        } else if(args.length == 1 && args[0].equalsIgnoreCase("verifizieren")) {
            int code = generateCode();
            p.sendMessage(msg(String.format("<red>Sende dem FTS-Bot den Befehl /verify %d. Dieser Code ist für 5 Minuten gültig.", generateCode())));
            codes.add(code);
            Bukkit.getScheduler().runTaskLater(plugin, () -> codes.remove(code), 20 * 60 * 5);
            String town = null;
            try {
                Resident resident = TownyAPI.getInstance().getResident(p);
                if (resident.hasTown())
                    town = TownyAPI.getInstance().getResident(p).getTown().getName();
            } catch (NotRegisteredException ignore) {}
            plugin.getDiscordHook().sendVerification(p.getUniqueId(), String.valueOf(code), town);
        }
        return false;
    }

    private int generateCode() {
        int code = (int) (Math.random() * 9999);
        while (codes.contains(code)) {
            code = (int) (Math.random() * 9999);
        }
        return code;
    }

}
