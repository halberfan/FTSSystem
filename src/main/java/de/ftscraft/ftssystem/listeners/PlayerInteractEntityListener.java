package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractEntityListener implements Listener {

    public PlayerInteractEntityListener(FtsSystem instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity cow = event.getRightClicked();
        if (cow.getType() == EntityType.MUSHROOM_COW) {
            boolean turnIntoCow = false;
            if (event.getHand() == EquipmentSlot.HAND && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BOWL) {
                turnIntoCow = true;
            } else if(event.getHand() == EquipmentSlot.OFF_HAND && event.getPlayer().getInventory().getItemInOffHand().getType() == Material.BOWL) {
                turnIntoCow = true;
            }
            if (turnIntoCow) {
                Location location = cow.getLocation();
                cow.remove();
                location.getWorld().spawn(location, Cow.class);
            }
        }
    }

}
