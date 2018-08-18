/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Runner implements Runnable {

    private FtsSystem plugin;

    private int time_to_message = Varriables.AUTO_MESSAGE_COOLDOWN;
    private int lastmessage = 0;

    public Runner(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20, 20);
    }

    @Override
    public void run() {

        time_to_message--;

        if(time_to_message == 0) {

            time_to_message = Varriables.AUTO_MESSAGE_COOLDOWN;
            lastmessage++;
            String msg = plugin.getConfigManager().getAutoMessages().get(lastmessage - 1);
            for(Player a : Bukkit.getOnlinePlayers()) {
                a.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
            if(plugin.getConfigManager().getAutoMessages().size() == lastmessage)
                lastmessage = 0;

        }


    }

}
