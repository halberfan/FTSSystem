package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;

public class FurnaceBurnListener implements Listener {

    public FurnaceBurnListener(FtsSystem plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBurn(FurnaceBurnEvent event) {

        if (event.getFuel().getType() == Material.LAVA_BUCKET) {
            event.setCancelled(true);
        } else if (event.getFuel().getType() == Material.CHARCOAL) {
            event.setCancelled(false);
            event.setBurnTime(30 * 20);
        } else if(event.getFuel().getType() == Material.COAL) {
            event.setCancelled(false);
            event.setBurnTime(80 * 20);
        } else if(event.getFuel().getType() == Material.DRIED_KELP_BLOCK) {
            event.setCancelled(false);
            event.setBurnTime(200 * 20);
        }

    }


}
