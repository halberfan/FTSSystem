package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.Variables;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

import java.util.Map;

public class EnchantListener implements Listener {

    public EnchantListener(FtsSystem plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEnchantPrepare(PrepareItemEnchantEvent event) {
        for (EnchantmentOffer offer : event.getOffers()) {
            if (offer == null)
                continue;
            Enchantment enchantment = offer.getEnchantment();
            if (Variables.FORBIDDEN_ENCHANTMENTS.contains(enchantment)) {
                offer.setEnchantment(Variables.REPLACEMENT_ENCHANTMENT);
                offer.setEnchantmentLevel(Variables.REPLACEMENT_ENCHANTMENT.getMaxLevel());
            }
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        Map<Enchantment, Integer> enchantsToAdd = event.getEnchantsToAdd();
        for (Enchantment enchantment : enchantsToAdd.keySet()) {
            if (Variables.FORBIDDEN_ENCHANTMENTS.contains(enchantment)) {
                enchantsToAdd.remove(enchantment);
                enchantsToAdd.put(Variables.REPLACEMENT_ENCHANTMENT, Variables.REPLACEMENT_ENCHANTMENT.getMaxLevel());
            }
        }
    }

}
