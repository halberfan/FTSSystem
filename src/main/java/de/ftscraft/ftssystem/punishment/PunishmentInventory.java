/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.UUIDFetcher;
import de.ftscraft.ftsutils.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

public class PunishmentInventory {

    private final FtsSystem plugin;
    private final String player;
    private final Inventory main;
    private Inventory akte;

    public PunishmentInventory(FtsSystem plugin, String player) {
        this.plugin = plugin;
        this.player = player;
        main = Bukkit.createInventory(null, 9 * 3, "§cPunishment-Menü: " + player);
        akte = Bukkit.createInventory(null, 9 * 6, "§2Akte: " + player);
    }

    private void loadMain() {

        //Platzhalter
        ItemStack blackglass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta mblackglass = blackglass.getItemMeta();
        mblackglass.setDisplayName(" ");
        blackglass.setItemMeta(mblackglass);

        //Skull
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta mskull = (SkullMeta) skull.getItemMeta();
        mskull.setOwningPlayer(Bukkit.getOfflinePlayer(player));
        mskull.setDisplayName("§c" + player);
        skull.setItemMeta(mskull);

        //Note
        ItemStack note = new ItemStack(Material.PAPER, 1);
        ItemMeta mnote = note.getItemMeta();
        mnote.setDisplayName("§7Notiz");
        note.setItemMeta(mnote);

        //Tempwarn
        ItemStack tempwarn = new ItemStack(Material.LIGHT_GRAY_DYE, 1);
        ItemMeta mtempwarn = tempwarn.getItemMeta();
        mtempwarn.setDisplayName("§6Tempwarn");
        tempwarn.setItemMeta(mtempwarn);

        //Warn
        ItemStack warn = new ItemStack(Material.GRAY_DYE, 1);
        ItemMeta mwarn = warn.getItemMeta();
        mwarn.setDisplayName("§6Warn");
        warn.setItemMeta(mwarn);

        //Mute
        ItemStack mute = new ItemStack(Material.GREEN_DYE, 1);
        ItemMeta mmute = mute.getItemMeta();
        mmute.setDisplayName("§cTempmute");
        mute.setItemMeta(mmute);

        //Tempban
        ItemStack tempban = new ItemStack(Material.ORANGE_DYE, 1);
        ItemMeta mtempban = tempban.getItemMeta();
        mtempban.setDisplayName("§4Tempban");
        tempban.setItemMeta(mtempban);

        //Ban
        ItemStack ban = new ItemStack(Material.RED_DYE, 1);
        ItemMeta mban = ban.getItemMeta();
        mban.setDisplayName("§4Ban");
        ban.setItemMeta(mban);

        //Akte
        ItemStack akte = new ItemStack(Material.BOOK, 1);
        ItemMeta makte = akte.getItemMeta();
        makte.setDisplayName("§5Akte");
        akte.setItemMeta(makte);

        main.setItem(4, skull);
        main.setItem(18, note);
        main.setItem(19, tempwarn);
        main.setItem(20, warn);
        main.setItem(21, mute);
        main.setItem(22, tempban);
        main.setItem(23, ban);
        main.setItem(26, akte);

        for (int i = 0; i < main.getSize(); i++) {
            if (main.getItem(i) == null)
                main.setItem(i, blackglass);
        }

    }

    private void loadAkte() {
        akte = Bukkit.createInventory(null, 9 * 6, "§2Akte: " + player);
        PunishmentManager puMa = plugin.getPunishmentManager();
        //Skull
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta mskull = (SkullMeta) skull.getItemMeta();
        mskull.setOwningPlayer(Bukkit.getOfflinePlayer(player));
        mskull.setDisplayName("§c" + player);
        skull.setItemMeta(mskull);

        //Platzhalter
        ItemStack blackglass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta mblackglass = blackglass.getItemMeta();
        mblackglass.setDisplayName(" ");
        blackglass.setItemMeta(mblackglass);

        SortedMap<Integer, ItemStack> map = new TreeMap<>();
        UUID uuid = UUIDFetcher.getUUID(player);
        puMa.clearData(uuid);
        puMa.loadPlayer(uuid);
        if (puMa.getPlayers().get(uuid) == null) {
            for (int i = 0; i < akte.getSize(); i++) {
                if (akte.getItem(i) == null) {
                    akte.setItem(i, blackglass);
                }
            }
            return;
        }

        for (Punishment a : puMa.getPlayers().get(uuid)) {

            switch (a.getType()) {
                case NOTE -> {
                    //Note
                    ItemStack note = new ItemStack(Material.PAPER, 1);
                    ItemMeta mnote = note.getItemMeta();
                    mnote.setLore(Arrays.asList("§cErstellt von: " + a.getAuthorName(),
                            "§cAm: " + a.createdOn(),
                            "§cGrund: " + a.getReason(),
                            String.valueOf(a.getID())));
                    mnote.setDisplayName("§7Notiz");
                    note.setItemMeta(mnote);
                    int id = a.getID();
                    if (!akte.contains(note))
                        map.put(id, note);
                }
                case TEMP_WARN -> {
                    //Tempwarn
                    TemporaryPunishment tempPun = (TemporaryPunishment) a;
                    ItemStack tempwarn = new ItemStack(Material.LIGHT_GRAY_DYE, 1);
                    ItemMeta mtempwarn = tempwarn.getItemMeta();
                    mtempwarn.setDisplayName("§6Tempwarn");
                    mtempwarn.setLore(Arrays.asList("§cErstellt von: " + a.getAuthorName(),
                            "§cAm: " + a.createdOn(), "§cGrund: " + a.getReason(),
                            String.valueOf(a.getID()),
                            "§eDeaktiviert: " + (!a.isActive() ? "Ja" : "Nein"),
                            "§eAbgelaufen: " + (tempPun.getUntil() < System.currentTimeMillis() ? "Ja" : "Nein")));
                    tempwarn.setItemMeta(mtempwarn);
                    int id = a.getID();
                    if (!akte.contains(tempwarn))
                        map.put(id, tempwarn);
                }
                case WARN -> {
                    //Warn
                    ItemStack warn = new ItemStack(Material.GRAY_DYE, 1);
                    ItemMeta mwarn = warn.getItemMeta();
                    mwarn.setDisplayName("§6Warn");
                    mwarn.setLore(Arrays.asList("§cErstellt von: " + a.getAuthorName(),
                            "§cAm: " + a.createdOn(),
                            "§cGrund: " + a.getReason(),
                            String.valueOf(a.getID()),
                            "§eDeaktiviert: " + (!a.isActive() ? "Ja" : "Nein")));
                    warn.setItemMeta(mwarn);
                    int id = a.getID();
                    if (!akte.contains(warn))
                        map.put(id, warn);
                }
                case TEMP_MUTE -> {
                    //Tempmute
                    TemporaryPunishment tempPun = (TemporaryPunishment) a;
                    ItemStack mute = new ItemStack(Material.GREEN_DYE, 1);
                    ItemMeta mmute = mute.getItemMeta();
                    mmute.setDisplayName("§cTempmute");
                    mmute.setLore(Arrays.asList("§cErstellt von: " + a.getAuthorName(),
                            "§cAm: " + a.createdOn(),
                            "§cGrund: " + a.getReason(),
                            String.valueOf(a.getID()),
                            "§eDeaktiviert: " + (!a.isActive() ? "Ja" : "Nein"),
                            "§eAbgelaufen: " + (tempPun.getUntil() < System.currentTimeMillis() ? "Ja" : "Nein")));
                    mute.setItemMeta(mmute);
                    int id = a.getID();
                    if (!akte.contains(mute))
                        map.put(id, mute);
                }
                case TEMP_BAN -> {
                    //Tempban
                    TemporaryPunishment tempPun = (TemporaryPunishment) a;
                    ItemStack tempban = new ItemStack(Material.ORANGE_DYE, 1);
                    ItemMeta mtempban = tempban.getItemMeta();
                    mtempban.setDisplayName("§4Tempban");
                    mtempban.setLore(Arrays.asList("§cErstellt von: " + a.getAuthorName(),
                            "§cAm: " + a.createdOn(),
                            "§cGrund: " + a.getReason(),
                            String.valueOf(a.getID()),
                            "§eDeaktiviert: " + (!a.isActive() ? "Ja" : "Nein"),
                            "§eAbgelaufen: " + (tempPun.getUntil() < System.currentTimeMillis() ? "Ja" : "Nein")));
                    tempban.setItemMeta(mtempban);
                    int id = a.getID();
                    if (!akte.contains(tempban))
                        map.put(id, tempban);
                }
                case BAN -> {
                    //Ban
                    ItemStack ban = new ItemStack(Material.RED_DYE, 1);
                    ItemMeta mban = ban.getItemMeta();
                    mban.setDisplayName("§4Ban");
                    mban.setLore(Arrays.asList("§cErstellt von: " + a.getAuthorName(),
                            "§cAm: " + a.createdOn(),
                            "§cGrund: " + a.getReason(),
                            String.valueOf(a.getID()),
                            "§eDeaktiviert: " + (!a.isActive() ? "Ja" : "Nein")));
                    ban.setItemMeta(mban);
                    int id = a.getID();
                    if (!akte.contains(ban))
                        map.put(id, ban);
                }
            }

        }

        for (ItemStack all : map.values()) {
            akte.addItem(all);
        }


        ItemStack printToTelegraph = new ItemBuilder(Material.DROPPER).name("§6Druck mir das aus!").build();
        akte.setItem(akte.getSize() - 1, printToTelegraph);

        for (int i = 0; i < akte.getSize(); i++) {
            if (akte.getItem(i) == null) {
                akte.setItem(i, blackglass);
            }
        }

    }

    public Inventory getInv(PunishmentInvType inv) {
        if (inv == PunishmentInvType.MAIN) {
            loadMain();
            return main;
        } else if (inv == PunishmentInvType.AKTE) {
            loadAkte();
            return akte;
        }
        return null;
    }

    public enum PunishmentInvType {
        MAIN, AKTE
    }

}
