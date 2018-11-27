/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Runner implements Runnable {

    private FtsSystem plugin;

    private int time_to_message = Varriables.AUTO_MESSAGE_COOLDOWN;
    private int lastmessage = 0;

    private int time_to_update = de.ftscraft.ftssystem.utils.Varriables.SCOREBOARD_UPDATE_COOLDOWN;

    public Runner(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20, 20);
    }

    @Override
    public void run() {

        time_to_message--;
        time_to_update--;

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
        if(time_to_update == 0) {
            plugin.getFtsScoreboard().update();
            time_to_update = Varriables.SCOREBOARD_UPDATE_COOLDOWN;
        }

        plugin.getReisepunktManager().update();

        for(User all : plugin.getUser().values()) {

            //Combat

            if(!all.getFights().isEmpty())
            {

                for(Player fights : all.getFights().keySet()) {
                    if(all.getFights().get(fights) == 0) {
                        all.getFights().remove(fights);
                        if(all.getFights().isEmpty()) {
                            all.getPlayer().sendMessage("§cDu bist nicht mehr im Kampf");
                        }
                        continue;
                    }
                    all.getFights().put(fights, all.getFights().get(fights)-1);
                }

            }

        }


    }

}
