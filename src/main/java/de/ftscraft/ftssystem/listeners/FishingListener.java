package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.Variables;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishingListener implements Listener {

    public FishingListener(FtsSystem plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onFishing(PlayerFishEvent event) {
        if (event.getCaught() == null) {
            return;
        }
        if (event.getCaught() instanceof Item item) {
            ItemStack is = item.getItemStack();
            for (Enchantment enchantment : is.getEnchantments().keySet()) {
                if (Variables.FORBIDDEN_ENCHANTMENTS.contains(enchantment)) {
                    is.removeEnchantment(enchantment);
                    is.addEnchantment(Variables.REPLACEMENT_ENCHANTMENT, Variables.REPLACEMENT_ENCHANTMENT.getMaxLevel());
                }
            }
        }
    }

}
