/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.Arrays;

public class Variables {

    public static final int AUTO_MESSAGE_COOLDOWN = 60 * 20, SCOREBOARD_UPDATE_COOLDOWN = 5;

    public final static ArrayList<Enchantment> FORBIDDEN_ENCHANTMENTS = new ArrayList<>(Arrays.asList(Enchantment.CHANNELING, Enchantment.MENDING, Enchantment.ARROW_INFINITE));
    public final static Enchantment REPLACEMENT_ENCHANTMENT = Enchantment.DURABILITY;

}
