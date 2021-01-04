/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.menus.fts;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FTSMenuInventory {

    private Player player;
    private User user;

    private FtsSystem plugin;

    private Inventory inv;

    public FTSMenuInventory(Player p, FtsSystem plugin) {
        this.player = p;
        this.plugin = plugin;

        this.user = plugin.getUser(p);
        this.inv = init();
    }

    private Inventory init() {

        Inventory inv = Bukkit.createInventory(null, 9 * 5, "§6" + player.getName() + ": Dein Menü");

        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        if (user.hasNoobProtection()) {
            ItemStack noobschutz = new ItemStack(Material.WOODEN_SWORD, 1);
            ItemMeta noobschutzMeta = noobschutz.getItemMeta();
            noobschutzMeta.setDisplayName("§cNoobschutz");
            noobschutzMeta.setLore(Arrays.asList("§cDerzeit: §2An", "§7Klicke, um ihn auszuschalten. Danach kannst du ihn aber nicht wieder aktivieren!"));
            noobschutzMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            noobschutz.setItemMeta(noobschutzMeta);
            inv.setItem(22, noobschutz);
        }

        for (int i = 0; i < 9 * 5; i++) {
            if(inv.getItem(i) == null) {
                inv.setItem(i, filler.clone());
            }
        }

        return inv;

    }

    public Inventory getInventory() {
        return init();
    }
}
