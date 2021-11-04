/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.configs;

public enum ConfigVal {

    LATEST_PUNISH_ID("latestPunishID"),
    WARTUNG("wartung");

    private String path;

    ConfigVal(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
