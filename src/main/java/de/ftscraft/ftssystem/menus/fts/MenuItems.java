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
    private ItemStack oocChannelOff, oocChannelOn, oocChannelRP;
    private ItemStack globalChannelOff, globalChannelOn, globalChannelRP;
    private ItemStack factionChannelOff, factionChannelOn, factionChannelRP;

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
                .lores("§7Bitte beachte dass es bis zu 5 Sekunden dauern kann", "§7bis die Einstellung übernommen wird!", "§3")
                .name("§5Scoreboard: " + ChatColor.GREEN + "An")
                .make();

        scoreboardOff = new ItemBuilder(Material.OAK_SIGN)
                .amount(1)
                .lores("§7Bitte beachte dass es bis zu 5 Sekunden dauern kann", "§7bis die Einstellung übernommen wird!", "§3")
                .name("§5Scoreboard: " + ChatColor.RED + "Aus")
                .make();

        oocChannelOn = new ItemBuilder(Material.PLAYER_HEAD)
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThmNDc5MjM4NjFjMjc1MzExYWI2OWIwM2UyNzUxMjI0ZmYzOTRlNDRlM2IzYzhkYjUyMjVmZiJ9fX0")
                .amount(1)
                .lores("§7Wann möchtest du den OOC Channel sehen?", "§5")
                .name("§3OOC-Channel: " + ChatColor.GREEN + "Immer an")
                .make();

        oocChannelOff = new ItemBuilder(Material.PLAYER_HEAD)
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmFlY2VkZjViOGIwYWMzZjU2MTkyZmNjYjBhZGU0OGRjNWEwNDNlN2UwZWVhMmJjYzVmNTRhZDAyODFmNjdjOSJ9fX0")
                .amount(1)
                .lores("§7Wann möchtest du den OOC Channel sehen?", "§5")
                .name("§3OOC-Channel: " + ChatColor.DARK_RED + "Immer aus")
                .make();

        oocChannelRP = new ItemBuilder(Material.PLAYER_HEAD)
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNmNDZjY2FlMmJiMTI0M2M4M2Q2Njc2YjI0OGMyYjFkNDM0MmVlYTM3OTMxNWNhZWMwMjFiN2QxZDM1NTcifX19")
                .amount(1)
                .lores("§7Wann möchtest du den OOC Channel sehen?", "§5")
                .name("§3OOC-Channel: " + ChatColor.DARK_BLUE + "Nur im RP aus")
                .make();

        factionChannelOn = new ItemBuilder(Material.PLAYER_HEAD)
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2M0YzM5OWI1ZTc1MjJhMTdkMzVlYjUzZmU1ZWI3NjdhM2IzOGVhNmU1Y2QzM2I0NDM4MDIwZmM1YTg0OGEifX19")
                .amount(1)
                .lores("§7Wann möchtest du den Faction Channel sehen?", "§6")
                .name("§3Faction-Channel: " + ChatColor.GREEN + "Immer an")
                .make();

        factionChannelOff = new ItemBuilder(Material.PLAYER_HEAD)
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODhiYmMzNTIxM2M2NTEwOWZiZDhlOTA3NzFlNGM2ZGI3ODVhOWJlOGU0MDNkMjllZDhlZDJlM2RjZmIxMjgifX19")
                .amount(1)
                .lores("§7Wann möchtest du den Faction Channel sehen?", "§6")
                .name("§3Faction-Channel: " + ChatColor.DARK_RED + "Immer aus")
                .make();

        factionChannelRP = new ItemBuilder(Material.PLAYER_HEAD)
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y5ZmQyMWEyZTJmNTcyZTllNWJmMGI1NTJmMDljNDE2YzQ4NzZjYmVhMGQ0YWMzZWMxZTY5ZGVlYzU5YSJ9fX0")
                .amount(1)
                .lores("§7Wann möchtest du den Faction Channel sehen?", "§6")
                .name("§3Faction-Channel: " + ChatColor.DARK_BLUE + "Nur im RP aus")
                .make();

        globalChannelOn = new ItemBuilder(Material.PLAYER_HEAD)
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdjYThiOWM3MTY5YTc0M2Y4MmU4ZTlmZDY4ZjExYzBmMzEwNzBhNTNjOTI4NjM4ODRlNjczMTM3ZmJhZjEifX19")
                .amount(1)
                .lores("§7Wann möchtest du den Global Channel sehen?", "§7")
                .name("§3Global-Channel: " + ChatColor.GREEN + "Immer an")
                .make();

        globalChannelOff = new ItemBuilder(Material.PLAYER_HEAD)
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTg1MzYxMzc3MjE1ZTIxNGEzNWM5NmNiYWY5ZGI2NzIxMDVhZmFmYzk0OTRiYmY0YWU3MmFhNjczYzVmZTdjIn19fQ")
                .amount(1)
                .lores("§7Wann möchtest du den Global Channel sehen?", "§7")
                .name("§3Global-Channel: " + ChatColor.DARK_RED + "Immer aus")
                .make();

        globalChannelRP = new ItemBuilder(Material.PLAYER_HEAD)
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmM3NmI0ZTlmZDg3Yjc3YjhjOTVhOGQxODQ4NDE2OGNiZWI3OGZhYjVkNTI5ZDZiNmMxMzIyNDZjYzNjYmFjIn19fQ")
                .amount(1)
                .lores("§7Wann möchtest du den Global Channel sehen?", "§7")
                .name("§3Global-Channel: " + ChatColor.DARK_BLUE + "Nur im RP aus")
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

    public ItemStack getOocChannelOff() {
        return oocChannelOff;
    }

    public ItemStack getOocChannelOn() {
        return oocChannelOn;
    }

    public ItemStack getOocChannelRP() {
        return oocChannelRP;
    }

    public ItemStack getGlobalChannelOff() {
        return globalChannelOff;
    }

    public ItemStack getGlobalChannelOn() {
        return globalChannelOn;
    }

    public ItemStack getGlobalChannelRP() {
        return globalChannelRP;
    }

    public ItemStack getFactionChannelOff() {
        return factionChannelOff;
    }

    public ItemStack getFactionChannelOn() {
        return factionChannelOn;
    }

    public ItemStack getFactionChannelRP() {
        return factionChannelRP;
    }
}
