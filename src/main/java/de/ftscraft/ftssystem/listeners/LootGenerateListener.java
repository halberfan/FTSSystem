package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class LootGenerateListener implements Listener {

    public LootGenerateListener(FtsSystem plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInvOpen(InventoryOpenEvent event) {
        for (ItemStack is : event.getInventory().getStorageContents()) {
            if (is == null || !is.hasItemMeta())
                continue;
            Utils.replaceEnchantments(is);
        }
    }

}
