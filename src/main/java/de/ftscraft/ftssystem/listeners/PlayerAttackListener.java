/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class PlayerAttackListener implements Listener {

    private final FtsSystem plugin;

    public PlayerAttackListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player ap) {

            if (event.getEntity() instanceof Player bp) {

                if (bp.hasMetadata("NPC")) {
                    return;
                }

                User a = plugin.getUser(ap);
                User b = plugin.getUser(bp);

                if (a.hasNoobProtection()) {
                    ap.sendMessage(Messages.PREFIX + "Du hast noch Noobschutz! Um zu kämpfen, musst du ihn mit /fts deaktivieren.");
                    event.setCancelled(true);
                    return;
                }

                if (b.hasNoobProtection()) {
                    ap.sendMessage(Messages.PREFIX + "Dieser Spieler hat noch Noobschutz!");
                    event.setCancelled(true);
                }

                // Disable Fighting System
            }
        }
    }


    @EventHandler
    public void onTarget(EntityTargetEvent event) {

        if (event.getTarget() instanceof Player p) {

            User user = plugin.getUser(p);

            if (user.hasNoobProtection()) {

                if (event.getReason() != EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY) {
                    event.setCancelled(true);
                }

            }

        }

    }

}
