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
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private FtsSystem plugin;

    public DeathListener(FtsSystem plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {

        if (event.getEntity().getKiller() == null)
            return;

        Player ap = event.getEntity().getKiller();

        Player bp = event.getEntity();

        User a = plugin.getUser(ap);
        User b = plugin.getUser(bp);

        if (!a.getFights().containsKey(bp))
        {
            ap.sendMessage("§cDu bist nun nicht mehr im Kampf");
        }
        if (!b.getFights().containsKey(ap))
        {
            bp.sendMessage("§cDu bist nun nicht mehr im Kampf");
        }

        a.getFights().remove(bp);
        b.getFights().remove(ap);

    }


}
