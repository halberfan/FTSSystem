/*
 * Copyright (c) 2019.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import com.massivecraft.factions.*;
import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class FactionListener implements Listener {

    private FtsSystem plugin;

    public FactionListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if(event.getBlock().getType() != Material.ARMOR_STAND) {
            return;
        }

        Location loc = event.getBlock().getLocation();

        FLocation fLocation = new FLocation(loc);

        Faction faction = Board.getInstance().getFactionAt(fLocation);

        if(!faction.getOnlinePlayers().contains(event.getPlayer()) && !faction.isWilderness()) {
            event.getPlayer().sendMessage("§cDu darfst nicht Armorstands in anderen Factions griefen!");
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void ArmorStandManipulateListener(PlayerArmorStandManipulateEvent event) {

        Location loc = event.getRightClicked().getLocation();

        FLocation fLocation = new FLocation(loc);

        Faction faction = Board.getInstance().getFactionAt(fLocation);

        if(!faction.getOnlinePlayers().contains(event.getPlayer()) && !faction.isWilderness()) {
            event.getPlayer().sendMessage("§cDu darfst nicht Armorstands in anderen Factions griefen!");
            event.setCancelled(true);
        }


    }


}
