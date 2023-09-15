/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import de.ftscraft.ftssystem.punishment.*;
import de.ftscraft.ftssystem.utils.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class InvClickListener implements Listener {

    private final FtsSystem plugin;

    private final ArrayList<OfflinePlayer> printed = new ArrayList<>();

    public InvClickListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getView().getTitle().startsWith("§cPunishment")) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();
            ItemMeta itemMeta = item.getItemMeta();

            ItemStack skull = event.getInventory().getItem(4);
            ItemMeta skullMeta = skull.getItemMeta();

            String target = skullMeta.getDisplayName().replace("§c", "");

            if (item.getType() == Material.BLACK_STAINED_GLASS_PANE) {
                return;
            }

            Player p = (Player) event.getWhoClicked();
            if (p.hasPermission("ftssystem.punish"))
                if (itemMeta.getDisplayName().equalsIgnoreCase("§4Ban")) {
                    PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.BAN, p, target);
                    prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                    p.closeInventory();
                    if (plugin.getPunishmentManager().isBanned(UUIDFetcher.getUUID(target))) {
                        p.sendMessage("§cDieser Spieler ist schon gebannt!");
                        return;
                    }
                    p.sendMessage("§cBitte schreibe den Grund");
                } else if (itemMeta.getDisplayName().equalsIgnoreCase("§4Tempban")) {
                    PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.TEMPBAN, p, target);
                    prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                    p.closeInventory();
                    if (plugin.getPunishmentManager().isBanned(UUIDFetcher.getUUID(target))) {
                        p.sendMessage("§cDieser Spieler ist schon gebannt!");
                        return;
                    }
                    p.sendMessage("§cBitte schreibe den Grund");
                } else if (itemMeta.getDisplayName().equalsIgnoreCase("§cTempmute")) {
                    PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.TEMPMUTE, p, target);
                    prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                    p.closeInventory();
                    if (plugin.getPunishmentManager().isMuted(UUIDFetcher.getUUID(target))) {
                        p.sendMessage("§cDieser Spieler ist schon gemutet!");
                        return;
                    }
                    p.sendMessage("§cBitte schreibe den Grund");
                } else if (itemMeta.getDisplayName().equalsIgnoreCase("§6Warn")) {
                    PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.WARN, p, target);
                    prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                    p.closeInventory();
                    p.sendMessage("§cBitte schreibe den Grund");
                } else if (itemMeta.getDisplayName().equalsIgnoreCase("§6Tempwarn")) {
                    PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.TEMPWARN, p, target);
                    prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                    p.closeInventory();
                    p.sendMessage("§cBitte schreibe den Grund");
                } else if (itemMeta.getDisplayName().equalsIgnoreCase("§7Notiz")) {
                    PunishmentBuilder prog = new PunishmentBuilder(plugin, PunishmentType.NOTE, p, target);
                    prog.setChatProgress(PunishmentManager.ChatProgress.REASON);
                    p.closeInventory();
                    p.sendMessage("§cBitte schreibe den Grund");
                } else if (itemMeta.getDisplayName().equalsIgnoreCase("§5Akte")) {
                    p.closeInventory();
                    p.openInventory(new PunishmentInventory(plugin, target).getInv(PunishmentInventory.PunishmentInvType.AKTE));
                }

        }

        if (event.getView().getTitle().startsWith("§2Akte")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null)
                return;
            ItemMeta meta = item.getItemMeta();
            if (!meta.getDisplayName().equalsIgnoreCase(" ")) {

                if (printed.contains(event.getWhoClicked())) {
                    if (!event.getWhoClicked().hasPermission("ftssystem.admin")) {
                        event.getWhoClicked().sendMessage("§cDu kannst das nächste mal diese Funktion benutzen nach einem Server-Neustart!");
                        return;
                    }
                }

                if (meta.getDisplayName().equalsIgnoreCase("§6Druck mir das aus!")) {

                    ItemStack firstPunishment = event.getInventory().getItem(0);
                    UUID p;

                    if (!firstPunishment.getItemMeta().getDisplayName().equalsIgnoreCase(" ")) {

                        int id = Integer.parseInt(firstPunishment.getItemMeta().getLore().get(3));
                        Punishment pun = plugin.getPunishmentManager().getPunishmentById(id);
                        p = pun.getPlayer();

                    } else {

                        event.getWhoClicked().sendMessage("§cDu hast noch keine Strafen!");
                        return;

                    }

                    String content = "[{\"tag\":\"p\",\"children\":[\"REPLACE\"]}]";
                    //content = "[{\"tag\":\"p\",\"children\":[\"Strafe+1:+Griefing+\\nStrafe+2:+Trolling\"]}]";

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < plugin.getPunishmentManager().getPlayers().get(p).size(); i++) {
                        Punishment pun = plugin.getPunishmentManager().getPlayers().get(p).get(i);
                        stringBuilder.append("Strafe ").append(i + 1).append(": ").append(pun.getReason()).append("%5Cn");
                        stringBuilder.append("  - Weitere Infos: ").append(pun.getMoreInformation()).append("%5Cn");
                        stringBuilder.append("  - Typ: ").append(pun.getType()).append("%5Cn");
                        if (pun instanceof Temporary) {
                            stringBuilder.append("  - Bis: ").append(((Temporary) pun).untilAsCalString()).append("%5Cn");
                        }
                        stringBuilder.append("  - Autor: ").append(pun.getAuthor()).append("%5Cn");
                        stringBuilder.append("  - Deaktiviert: ").append(!pun.isActive()).append("%5Cn %5Cn");
                    }

                    content = content.replace("REPLACE", stringBuilder.toString().replace(" ", "+"));

                    URL url = null;
                    try {
                        url = new URL("https://api.telegra.ph/createPage?access_token=6cf9217c73e4da3913dc2d9f878423ebd713ff7fd4d9ab6d087b16f48f9b&title=Strafen:+" + UUIDFetcher.getName(p) + "&content=" + content + "&author_name=FTS-System");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    Scanner sc = null;
                    try {
                        sc = new Scanner(url.openStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    StringBuffer sb = new StringBuffer();
                    while (sc.hasNext()) {
                        sb.append(sc.next());
                    }

                    String result = sb.toString();

                    JSONParser parser = new JSONParser();
                    try {
                        Object obj = parser.parse(result);
                        JSONObject jobj = ((JSONObject) obj);
                        JSONObject obj2 = (JSONObject) jobj.get("result");

                        event.getWhoClicked().sendMessage(Messages.PREFIX + "Hier ist dein Link: §c" + obj2.get("url"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    return;
                }


                int id = Integer.parseInt(meta.getLore().get(3));

                Punishment pun = plugin.getPunishmentManager().getPunishmentById(id);

                Player p = (Player) event.getWhoClicked();

                p.closeInventory();

                p.sendMessage("§5----------------");
                p.sendMessage("§cSpieler: §e" + UUIDFetcher.getName(pun.getPlayer()));
                p.sendMessage("§cAutor: §e" + pun.getAuthor());
                p.sendMessage("§cGrund: §e" + pun.getReason());
                p.sendMessage("§cTyp: §e" + pun.getType().toString());
                p.sendMessage("§cWeitere Informationen: §e" + pun.getMoreInformation());
                p.sendMessage("§cErstellt am: §e" + pun.createdOn());
                if (pun instanceof Temporary temp) {
                    p.sendMessage("§cBis: §e" + temp.untilAsCalString());
                }
                TextComponent rTC = new TextComponent("Deaktivieren");
                rTC.setColor(ChatColor.DARK_GREEN);
                rTC.setBold(true);
                rTC.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pu remove " + id));

                TextComponent bTC = new TextComponent("§5----");

                p.spigot().sendMessage(bTC, rTC, bTC);

            }

        }

        if (event.getView().getTitle().endsWith("Dein Menü")) {

            User u = plugin.getUser((Player) event.getWhoClicked());

            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null)
                return;

            ItemMeta meta = item.getItemMeta();

            if (meta.getDisplayName().equalsIgnoreCase("§cNoobschutz")) {
                if (u.hasNoobProtection()) {
                    u.setNoobProtection(false);
                    event.getWhoClicked().sendMessage(Messages.PREFIX + "Du hast deinen Noobschutz erfolgreich aufgegeben. Viel Spaß!");
                    event.getWhoClicked().closeInventory();
                }
            }

            if (!meta.hasLore())
                return;

            String id = meta.getLore().get(meta.getLore().size() - 1).replace("§", "");
            if (id.equalsIgnoreCase("1")) {
                u.setMsgSound(!u.isMsgSoundEnabled());
            } else if (id.equalsIgnoreCase("2")) {
                switch (u.getDisturbStatus()) {
                    case OFF -> u.setDisturbStatus(User.ChannelStatusSwitch.RP);
                    case RP -> u.setDisturbStatus(User.ChannelStatusSwitch.ON);
                    case ON -> u.setDisturbStatus(User.ChannelStatusSwitch.OFF);
                }
            } else if (id.equalsIgnoreCase("3")) {
                u.setScoreboardEnabled(!u.isScoreboardEnabled());
            } else if (id.equalsIgnoreCase("5")) {
                switch (u.getOocChannelStatus()) {
                    case OFF -> u.setOocChannelStatus(User.ChannelStatusSwitch.RP);
                    case RP -> u.setOocChannelStatus(User.ChannelStatusSwitch.ON);
                    case ON -> u.setOocChannelStatus(User.ChannelStatusSwitch.OFF);
                }
            } else if (id.equalsIgnoreCase("6")) {
                switch (u.getFactionChannelStatus()) {
                    case OFF -> u.setFactionChannelStatus(User.ChannelStatusSwitch.RP);
                    case RP -> u.setFactionChannelStatus(User.ChannelStatusSwitch.ON);
                    case ON -> u.setFactionChannelStatus(User.ChannelStatusSwitch.OFF);
                }
            } else if (id.equalsIgnoreCase("7")) {
                switch (u.getGlobalChannelStatus()) {
                    case OFF -> u.setGlobalChannelStatus(User.ChannelStatusSwitch.RP);
                    case RP -> u.setGlobalChannelStatus(User.ChannelStatusSwitch.ON);
                    case ON -> u.setGlobalChannelStatus(User.ChannelStatusSwitch.OFF);
                }
            }

            u.refreshMenu();

        }

    }

}
