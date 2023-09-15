/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    public DeathListener(FtsSystem plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player bp = event.getEntity();


        for (Entity nearbyEntity : bp.getLocation().getNearbyEntities(80, 100, 80)) {
            if (nearbyEntity instanceof Player t) {
                t.sendMessage(event.deathMessage());
            }
        }

        event.deathMessage(Component.text(""));

    }


}
