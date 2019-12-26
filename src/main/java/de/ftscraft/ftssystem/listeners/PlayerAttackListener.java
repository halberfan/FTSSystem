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
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Objects;

public class PlayerAttackListener implements Listener {

    private FtsSystem plugin;

    public PlayerAttackListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {

            Player ap = (Player) event.getDamager();

            if (event.getEntity() instanceof Player) {

                Player bp = (Player) event.getEntity();

                User a = plugin.getUser(ap);
                User b = plugin.getUser(bp);

                if (!a.getFights().containsKey(bp)) {
                    ap.sendMessage("§cDu bist nun im Kampf");
                }
                if (b != null) {
                    if (b.getFights() != null)
                        if (!b.getFights().containsKey(ap)) {
                            bp.sendMessage("§cDu bist nun im Kampf");
                        }
                }

                a.getFights().put(bp, 20);
                Objects.requireNonNull(b).getFights().put(ap, 20);

            }
        }
    }

}
