/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.reisepunkte;

import org.bukkit.Location;

public class TPPunkt {

    private Location loc;
    private String name;

    public TPPunkt(Location loc, String name) {
        this.loc = loc;
        this.name = name;
    }

    public Location getLoc() {
        return loc;
    }

    public String getName() {
        return name;
    }

}
