/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.channel;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;

public class Channel {

    private FtsSystem plugin;

    private String name;
    private String prefix;
    private String format;

    private boolean defaultChannel;

    private String permission;
    private int range;

    public Channel(FtsSystem plugin, String name, String prefix, String format, boolean defaultChannel, String permission, int range) {
        this.plugin = plugin;
        this.name = name;
        this.prefix = prefix;
        this.defaultChannel = defaultChannel;
        this.permission = permission;
        this.format = format;
        this.range = range;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getFormat() {
        return format;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isDefaultChannel() {
        return defaultChannel;
    }

    public int getRange() {
        return range;
    }
}
