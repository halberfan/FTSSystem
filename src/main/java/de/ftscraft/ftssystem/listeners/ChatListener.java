/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private FtsSystem plugin;

    public ChatListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        User u = plugin.getUser(event.getPlayer());
        event.setCancelled(true);
        if(u == null) {
            event.getPlayer().sendMessage("§cIrgendwas ist schief gelaufen. Probier mal zu reconnecten!");
            return;
        }

        plugin.getChatManager().chat(u, event.getMessage());

    }

}
