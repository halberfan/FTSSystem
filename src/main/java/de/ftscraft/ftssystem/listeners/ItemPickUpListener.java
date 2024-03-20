package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.Variables;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemPickUpListener implements Listener {

    public ItemPickUpListener(FtsSystem plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        ItemStack is = event.getItem().getItemStack();
        for (Enchantment enchantment : is.getEnchantments().keySet()) {
            if (Variables.FORBIDDEN_ENCHANTMENTS.contains(enchantment)) {
                is.removeEnchantment(enchantment);
                is.addEnchantment(Variables.REPLACEMENT_ENCHANTMENT, Variables.REPLACEMENT_ENCHANTMENT.getMaxLevel());
            }
        }
    }

}
