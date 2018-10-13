/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.reisepunkte;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Fahrzeug {

    private String name;
    private String departure, destination;
    private int id;
    public final int SECONDS;
    private int costs;
    private int seconds;

    private Location loc;

    private FtsSystem plugin;
    private ArrayList<Player> player;

    public Fahrzeug(String name, String departure, String destination, int seconds, Location loc, int costs, FtsSystem plugin) {
        this.name = name;
        this.departure = departure;
        this.destination = destination;
        this.plugin = plugin;
        this.seconds = seconds;
        this.loc = loc;
        this.costs = costs;
        this.player = new ArrayList<>();
        this.SECONDS = seconds;
    }

    public String getName() {
        return name;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public int getId() {
        return id;
    }

    public int getSeconds() {
        return seconds;
    }

    public Location getLoc() {
        return loc;
    }

    public ArrayList<Player> getPlayer() {
        return player;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
