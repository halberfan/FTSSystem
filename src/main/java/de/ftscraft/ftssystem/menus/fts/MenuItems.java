/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.menus.fts;

import de.ftscraft.ftssystem.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class MenuItems {

    private ItemStack messageSoundOn, messageSoundOff;
    private ItemStack doNotDisturbOn, doNotDisturbOff, doNotDisturbOnRP;
    private ItemStack scoreboardOn, scoreboardOff;

    public MenuItems() {
        init();
    }

    private void init() {

        messageSoundOn = new ItemBuilder(Material.PAPER)
                .amount(1)
                .name("§5MSG-Sound: " + ChatColor.GREEN + "An")
                .shiver(true)
                .lores("§7Du bekommst derzeit einen Sound wenn du eine MSG erhälst", "§7Klicke, um dies zu ändern!", "§1")
                .make();
        messageSoundOff = new ItemBuilder(Material.PAPER)
                .amount(1)
                .name("§5MSG-Sound: " + ChatColor.RED + "Aus")
                .shiver(false)
                .lores("§7Derzeit bekommst du keinen Sound wenn du eine MSG erhälst", "§7Klicke, um dies zu ändern!", "§1")
                .make();

        doNotDisturbOff = new ItemBuilder(Material.RED_DYE)
                .amount(1)
                .name("§3Nicht-Stören: " + ChatColor.RED + "Immer Aus")
                .lores("§7Diese Funktion lässt folgendes für dich ausblenden: ", "§7-Umfragen", "§7-Broadcasts", "§7-Marktschreier", "§2")
                .make();

        doNotDisturbOn = new ItemBuilder(Material.GREEN_DYE)
                .amount(1)
                .name("§3Nicht-Stören: " + ChatColor.GREEN + "Immer An")
                .lores("§7Diese Funktion lässt folgendes für dich ausblenden: ", "§7-Umfragen", "§7-Broadcasts", "§7-Marktschreier", "§2")
                .make();

        doNotDisturbOnRP = new ItemBuilder(Material.BLUE_DYE)
                .amount(1)
                .name("§3Nicht-Stören: " + ChatColor.DARK_BLUE + "Nur im RP an")
                .lores("§7Diese Funktion lässt folgendes für dich ausblenden: ", "§7-Umfragen", "§7-Broadcasts", "§7-Marktschreier", "§2")
                .make();

        scoreboardOn = new ItemBuilder(Material.OAK_SIGN)
                .amount(1)
                .enchantment(Enchantment.DURABILITY, 1)
                .lores("§7Bitte beachte dass es bis zu 5 Sekunden dauern kann", "§7bis die Einstellung übernommen wird!","§3")
                .name("§5Scoreboard: " + ChatColor.GREEN + "An")
                .make();

        scoreboardOff = new ItemBuilder(Material.OAK_SIGN)
                .amount(1)
                .lores("§7Bitte beachte dass es bis zu 5 Sekunden dauern kann", "§7bis die Einstellung übernommen wird!","§3")
                .name("§5Scoreboard: " + ChatColor.RED + "Aus")
                .make();

    }

    public ItemStack getMessageSoundOff() {
        return messageSoundOff;
    }

    public ItemStack getMessageSoundOn() {
        return messageSoundOn;
    }

    public ItemStack getDoNotDisturbOn() {
        return doNotDisturbOn;
    }

    public ItemStack getDoNotDisturbOff() {
        return doNotDisturbOff;
    }

    public ItemStack getDoNotDisturbOnRP() {
        return doNotDisturbOnRP;
    }

    public ItemStack getScoreboardOn() {
        return scoreboardOn;
    }

    public ItemStack getScoreboardOff() {
        return scoreboardOff;
    }
}
