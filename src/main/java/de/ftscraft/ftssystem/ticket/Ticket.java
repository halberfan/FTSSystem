/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.ticket;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ticket {

    private String title;
    private String description;
    private UUID creator;
    private Location location;
    private List<TicketMessage> ticketMessages;
    private FtsSystem plugin;

    public Ticket(String title, String description, Player creator, FtsSystem plugin)
    {
        this.title = title;
        this.description = description;
        this.ticketMessages = new ArrayList<>();
        this.creator = creator.getUniqueId();
        this.location = creator.getLocation();
        this.plugin = plugin;
    }

    public void addTicketMessage(TicketMessage ticketMessage) {
        ticketMessages.add(ticketMessage);
    }




}
