/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.channel.Channel;
import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.rmi.MarshalException;

public class CommandListener implements Listener {

    private FtsSystem plugin;

    public CommandListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String[] command = event.getMessage().replace("/", "").split(" ");
        String cmd = command[0];

        if(command.length == 1) {
            Channel c = plugin.getChatManager().getChannel(cmd);
            if(c == null)
                return;
            event.setCancelled(true);
            plugin.getUser(event.getPlayer()).setActiveChannel(c);
            event.getPlayer().sendMessage(Messages.NOW_ACTIVE_CHANNEL.replace("%s", c.getName()));
        } else {
            Channel c = plugin.getChatManager().getChannel(cmd);
            if(c == null)
                return;
            event.setCancelled(true);
            if(!plugin.getUser(event.getPlayer()).getEnabledChannels().contains(c)) {
                plugin.getUser(event.getPlayer()).joinChannel(c);
                return;
            }
            String msg = "";
            for(int i = 1; i < command.length; i++) {
                msg = msg + " " + command[i];
            }
            msg = msg.replaceFirst(" ", "");
            plugin.getChatManager().chat(plugin.getUser(event.getPlayer()), msg, c);
        }

    }

}
