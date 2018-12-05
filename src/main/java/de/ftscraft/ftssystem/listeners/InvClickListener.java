/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.punishment.*;
import de.ftscraft.ftssystem.utils.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InvClickListener implements Listener {

    private FtsSystem plugin;

    public InvClickListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if(event.getInventory().getName().startsWith("§cPunishment")) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();
            ItemMeta itemMeta = item.getItemMeta();

            ItemStack skull = event.getInventory().getItem(4);
            ItemMeta skullMeta = skull.getItemMeta();

            String target = skullMeta.getDisplayName().replace("§c", "");

            if(item.getType() == Material.BLACK_STAINED_GLASS_PANE) {
                return;
            }

            Player p = (Player) event.getWhoClicked();
            if(p.hasPermission("ftssystem.punish"))
            if(itemMeta.getDisplayName().equalsIgnoreCase("§4Ban")) {
                PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.BAN, p, target);
                prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                p.closeInventory();
                if(plugin.getPunishmentManager().isBanned(UUIDFetcher.getUUID(target))) {
                    p.sendMessage("§cDieser Spieler ist schon gebannt!");
                    return;
                }
                p.sendMessage("§cBitte schreibe den Grund");
            } else if(itemMeta.getDisplayName().equalsIgnoreCase("§4Tempban")) {
                PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.TEMPBAN, p, target);
                prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                p.closeInventory();
                if(plugin.getPunishmentManager().isBanned(UUIDFetcher.getUUID(target))) {
                    p.sendMessage("§cDieser Spieler ist schon gebannt!");
                    return;
                }
                p.sendMessage("§cBitte schreibe den Grund");
            } else if(itemMeta.getDisplayName().equalsIgnoreCase("§cTempmute")) {
                PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.TEMPMUTE, p, target);
                prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                p.closeInventory();
                if(plugin.getPunishmentManager().isMuted(UUIDFetcher.getUUID(target))) {
                    p.sendMessage("§cDieser Spieler ist schon gemutet!");
                    return;
                }
                p.sendMessage("§cBitte schreibe den Grund");
            } else if(itemMeta.getDisplayName().equalsIgnoreCase("§6Warn")) {
                PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.WARN, p, target);
                prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                p.closeInventory();
                p.sendMessage("§cBitte schreibe den Grund");
            } else if(itemMeta.getDisplayName().equalsIgnoreCase("§6Tempwarn")) {
                PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.TEMPWARN, p, target);
                prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                p.closeInventory();
                p.sendMessage("§cBitte schreibe den Grund");
            } else if(itemMeta.getDisplayName().equalsIgnoreCase("§7Notiz")) {
                PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.NOTE, p, target);
                prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                p.closeInventory();
                p.sendMessage("§cBitte schreibe den Grund");
            } else if(itemMeta.getDisplayName().equalsIgnoreCase("§5Akte")) {
                p.closeInventory();
                p.openInventory(new PunishmentInventory(plugin, target).getInv(PunishmentInventory.PunishmentInvType.AKTE));
            }

        }

        if(event.getInventory().getName().startsWith("§2Akte")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null)
                return;
            ItemMeta meta = item.getItemMeta();
            if(!meta.getDisplayName().equalsIgnoreCase(" ")) {


                int id = Integer.valueOf(meta.getLore().get(3));

                Punishment pun = plugin.getPunishmentManager().getPunishmentById(id);

                Player p = (Player) event.getWhoClicked();

                p.closeInventory();

                p.sendMessage("§5----------------");
                p.sendMessage("§cSpieler: §e"+ UUIDFetcher.getName(pun.getPlayer()));
                p.sendMessage("§cAutor: §e"+ pun.getAuthor());
                p.sendMessage("§cGrund: §e"+pun.getReason());
                p.sendMessage("§cTyp: §e"+pun.getType().toString());
                p.sendMessage("§cWeitere Informationen: §e"+pun.getMoreInformation());
                p.sendMessage("§cErstellt am: §e"+pun.createdOn());
                if(pun instanceof Temporary) {
                    Temporary temp = (Temporary) pun;
                    p.sendMessage("§cBis: §e"+temp.untilAsCalString());
                }
                TextComponent rTC = new TextComponent("Deaktivieren");
                rTC.setColor(ChatColor.DARK_GREEN);
                rTC.setBold(true);
                rTC.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pu remove "+id));

                TextComponent bTC = new TextComponent("§5----");

                p.spigot().sendMessage(bTC,rTC,bTC);

            }

        }

    }

}
