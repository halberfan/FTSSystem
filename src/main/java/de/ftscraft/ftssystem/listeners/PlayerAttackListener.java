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
                    return;
                }

                // Disable Fighting System
                /*
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
                */
            }
        }
    }


    @EventHandler
    public void onTarget(EntityTargetEvent event) {

        if (event.getTarget() instanceof Player) {

            Player p = (Player) event.getTarget();
            User user = plugin.getUser(p);

            if (user.hasNoobProtection()) {

                if (event.getReason() != EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY) {
                    event.setCancelled(true);
                }

            }

        }

    }

}
