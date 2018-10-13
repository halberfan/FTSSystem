/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.reisepunkte;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class ReisepunktManager {

    private ArrayList<Fahrzeug> fahrzeuge;
    private HashMap<String, TPPunkt> tpPunkte;

    private FtsSystem plugin;

    public ReisepunktManager(FtsSystem plugin) {
        this.plugin = plugin;
        this.fahrzeuge = new ArrayList<>();
    }

    private void updatePlayerInNear() {

        for(Fahrzeug a : fahrzeuge) {

            a.getPlayer().clear();

            for(Entity b : a.getLoc().getWorld().getNearbyEntities(a.getLoc(), 3, 3, 3)) {
                if(b instanceof Player) {

                    a.getPlayer().add((Player) b);

                }
            }

        }

    }

    public void update() {
        updatePlayerInNear();

        for(Fahrzeug a : fahrzeuge) {
            if(a.getSeconds() == 0) {
                ArrayList<Player> p = (ArrayList<Player>) a.getPlayer().clone();
                for(Player b : p) {
                    b.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 3));
                }

                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    for(Player b : p) {
                        b.teleport(tpPunkte.get(a.getDestination()).getLoc());
                        b.sendTitle("§cDu kommst in "+tpPunkte.get(a.getDestination()).getName()+" an", "", 3, 40, 3);
                    }
                }, 20 * 2);

            }
            a.setSeconds(a.getSeconds() - 1);
        }

    }

    public ArrayList<Fahrzeug> getFahrzeuge() {
        return fahrzeuge;
    }

    public HashMap<String, TPPunkt> getTpPunkte() {
        return tpPunkte;
    }
}
