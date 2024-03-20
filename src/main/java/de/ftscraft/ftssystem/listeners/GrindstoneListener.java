package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftsutils.items.ItemReader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class GrindstoneListener implements Listener {

    public GrindstoneListener(FtsSystem plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGrindstonePrepare(PrepareGrindstoneEvent event) {
        ItemStack[] items = {event.getInventory().getLowerItem(), event.getInventory().getUpperItem()};
        for (ItemStack item : items) {
            if (item == null)
                continue;
            if (ItemReader.getSign(item) != null || ItemReader.getPDC(item, "glow", PersistentDataType.BOOLEAN) != null) {
                event.setResult(null);
            }
        }
    }

}
