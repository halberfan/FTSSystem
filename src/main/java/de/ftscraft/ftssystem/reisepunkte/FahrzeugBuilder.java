/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.reisepunkte;

import de.ftscraft.ftssystem.main.FtsSystem;

public class FahrzeugBuilder {

    private String name;
    private String departure, destination;
    private int id;
    private int costs;
    private int seconds;

    private FtsSystem plugin;

    public FahrzeugBuilder(FtsSystem plugin) {
        this.plugin = plugin;
    }

}
