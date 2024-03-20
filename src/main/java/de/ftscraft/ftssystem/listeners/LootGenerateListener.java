package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.Variables;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;

public class LootGenerateListener implements Listener {

    public LootGenerateListener(FtsSystem plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLootGeneration(LootGenerateEvent event) {
        for (ItemStack is : event.getLoot()) {
            for (Enchantment enchantment : is.getEnchantments().keySet()) {
                if (Variables.FORBIDDEN_ENCHANTMENTS.contains(enchantment)) {
                    is.removeEnchantment(enchantment);
                    is.addEnchantment(Variables.REPLACEMENT_ENCHANTMENT, Variables.REPLACEMENT_ENCHANTMENT.getMaxLevel());
                }
            }
        }
    }

}
