/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

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


}
