/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.channel;

import de.ftscraft.ftssystem.main.FtsSystem;

public class Channel {

    private final FtsSystem plugin;

    private final String name;
    private final String prefix;
    private final String format;

    private final boolean defaultChannel;

    private final String permission;
    private final int range;

    private final ChannelType type;

    public Channel(FtsSystem plugin, String name, String prefix, String format, boolean defaultChannel, String permission, int range, ChannelType type) {
        this.plugin = plugin;
        this.name = name;
        this.prefix = prefix;
        this.defaultChannel = defaultChannel;
        this.permission = permission;
        this.format = format;
        this.range = range;
        this.type = type;
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

    public ChannelType getType() {
        return type;
    }
}
