/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.configs;

public enum ConfigVal {

    WARTUNG("wartung"),
    MESSAGES("messages");

    private final String path;

    ConfigVal(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
