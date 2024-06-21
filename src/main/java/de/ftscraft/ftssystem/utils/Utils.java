/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import de.ftscraft.ftssystem.main.FtsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Utils {

    public final static Map<EnchantmentWithLevel, EnchantmentWithLevel> enchantmentReplacements;

    static {
        enchantmentReplacements = new HashMap<>();
        enchantmentReplacements.put(new EnchantmentWithLevel(Enchantment.LOOT_BONUS_BLOCKS, 1), new EnchantmentWithLevel(Enchantment.DIG_SPEED, 2));
        enchantmentReplacements.put(new EnchantmentWithLevel(Enchantment.LOOT_BONUS_BLOCKS, 2), new EnchantmentWithLevel(Enchantment.DIG_SPEED, 3));
        enchantmentReplacements.put(new EnchantmentWithLevel(Enchantment.LOOT_BONUS_BLOCKS, 3), new EnchantmentWithLevel(Enchantment.DIG_SPEED, 4));
        enchantmentReplacements.put(new EnchantmentWithLevel(Enchantment.MENDING, 1), new EnchantmentWithLevel(Enchantment.DURABILITY, 3));
        enchantmentReplacements.put(new EnchantmentWithLevel(Enchantment.ARROW_INFINITE, 1), new EnchantmentWithLevel(Enchantment.ARROW_DAMAGE, 3));
        enchantmentReplacements.put(new EnchantmentWithLevel(Enchantment.RIPTIDE, 1), new EnchantmentWithLevel(Enchantment.IMPALING, 1));
    }

    public static class EnchantmentWithLevel {
        public final Enchantment enchantment;
        public final int level;
        public EnchantmentWithLevel(Enchantment enchantment, int level) {
            this.enchantment = enchantment;
            this.level = level;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EnchantmentWithLevel that = (EnchantmentWithLevel) o;
            return level == that.level && Objects.equals(enchantment, that.enchantment);
        }

        @Override
        public int hashCode() {
            return Objects.hash(enchantment, level);
        }
    }

    public static String convertToTime(long millis) {
        millis = millis - System.currentTimeMillis();
        long seconds = millis / 1000;
        seconds += 1;
        long minutes = 0;
        long hours = 0;
        long days = 0;
        long weeks = 0;

        while (seconds >= 60) {
            minutes++;
            seconds -= 60;
        }
        while (minutes >= 60) {
            hours++;
            minutes -= 60;
        }
        while (hours >= 24) {
            days++;
            hours -= 24;
        }
        while (days >= 7) {
            weeks++;
            days -= 7;
        }

        return weeks + " Wochen " + days + " Tage " + hours + " Stunden " + minutes + " Minuten " + seconds + " Sekunden";
    }

    public static String[] splitToNumbers(String str) {
        return str.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
    }

    public static long calculateUntil(String unit) {
        String[] u = Utils.splitToNumbers(unit);
        //Check if its 2 size big for 1 Number + 1 Unit
        if (u.length != 2) {
            return -1;
        }
        //Init Until value
        long until = 0;

        for (int i = 0; i < u.length; i++) {
            //If i == 0 -> Its the Number
            if (i == 0) {
                try {
                    until = Integer.parseInt(u[i]);
                } catch (NumberFormatException ex) {
                    return -1;
                }
                //If i == 1 -> Its the Unit
            } else {
                //Check if Unit exists
                if (TimeUnits.getTimeUnitByUnit(u[i]) == null) {
                    return -1;
                }
                //Calculate until
                //Get Millis from TimeUnit
                long time = Objects.requireNonNull(TimeUnits.getTimeUnitByUnit(u[i])).getMillis();
                //Get Millis from TimeUnit * how many of these
                until = until * time;
                //Get final Millis from Current Millis + the Millis of duration
                until = until + System.currentTimeMillis();
            }
        }
        return until;
    }

    public static void sendMessageToAllPlayers(String message) {

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(message);
        }

    }

    public static void sendMessageToAllExceptDisturb(String message, FtsSystem plugin) {

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (plugin.getUser(onlinePlayer).isDisturbable()) {
                onlinePlayer.sendMessage(message);
            }
        }

    }

    public static String getTitleFromWebsite(String url) {
        InputStream response = null;
        String title = "Website: ";
        try {
            URL urlc = new URL(url);
            response = urlc.openStream();

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();

            title = responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>"));

        } catch (Exception ignored) {

        }
        return title;
    }

    public static void runConsoleCommand(String command, FtsSystem plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(plugin.getServer().getConsoleSender(), command);
            }
        }.runTask(plugin);
    }

    public static void msg(Player p, String miniMessage) {
        p.sendMessage(MiniMessage.miniMessage().deserialize(miniMessage));
    }

    public static Component msg(String miniMessage) {
        return MiniMessage.miniMessage().deserialize(miniMessage);
    }

    /**
     * Replace enchantments from ItemStack based on replacement map
     * @param itemStack Replacement gets applied on this ItemStack
     */
    public static void replaceEnchantments(ItemStack itemStack) {
        itemStack.getEnchantments().forEach((enchantment, level) -> {
            EnchantmentWithLevel enchantmentWithLevel = new EnchantmentWithLevel(enchantment, level);
            EnchantmentWithLevel replace = enchantmentReplacements.get((enchantmentWithLevel));
            if (replace != null) {
                itemStack.removeEnchantment(enchantment);
                itemStack.addEnchantment(replace.enchantment, replace.level);
            }
        });
    }


}
