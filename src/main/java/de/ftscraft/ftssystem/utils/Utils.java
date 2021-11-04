/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Utils {

    public static String convertToTime(long millis) {
        millis = millis - System.currentTimeMillis();
        long seconds = millis / 1000;
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
            response = new URL(url).openStream();

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();

            title = responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>"));

        } catch (Exception ignored) {

        }
        return title;
    }


}
