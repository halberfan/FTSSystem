/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.ticket;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Bukkit;
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
    private int id;
    private List<TicketMessage> ticketMessages;
    private List<TicketRequest> ticketRequests;

    private UUID taker;

    private FtsSystem plugin;

    public Ticket(String title, String description, Player creator, FtsSystem plugin)
    {
        this.title = title;
        this.description = description;
        this.ticketMessages = new ArrayList<>();
        this.ticketRequests = new ArrayList<>();
        this.creator = creator.getUniqueId();
        this.location = creator.getLocation();
        this.plugin = plugin;
    }

    public void addTicketMessage(TicketMessage ticketMessage)
    {
        ticketMessages.add(ticketMessage);
    }

    public void take(Player p)
    {
        this.taker = p.getUniqueId();
    }

    public TicketRequestReturnType request(TicketRequestType type)
    {

        Player p = Bukkit.getPlayer(taker);

        if (p == null)
            return TicketRequestReturnType.PLAYER_NOT_FOUND;

        if (!ticketRequests.isEmpty()) {
            for (TicketRequest request : ticketRequests) {
                if (request.getType() == type) {
                    if (request.isRequested())
                        return TicketRequestReturnType.ALREADY_DONE;
                    else request.setRequested(true);
                    return TicketRequestReturnType.DONE;
                }
            }
        }

        TicketRequest ticketRequest = new TicketRequest(type);
        ticketRequest.setRequested(true);

        ticketRequests.add(ticketRequest);

        return TicketRequestReturnType.DONE;

    }

    public TicketRequestReturnType accept(TicketRequestType type)
    {

        Player p = Bukkit.getPlayer(creator);

        if(p == null)
            return TicketRequestReturnType.PLAYER_NOT_FOUND;

        if(!ticketRequests.isEmpty()) {
            for (TicketRequest request : ticketRequests) {
                if(request.getType() == type) {
                    if(request.isAccepted())
                        return TicketRequestReturnType.ALREADY_DONE;
                    else request.setAccepted(true);
                    return TicketRequestReturnType.DONE;
                }
            }
        }

        return TicketRequestReturnType.NOT_REQUESTED;
    }

    

    public class TicketRequest {
        private TicketRequestType type;
        private boolean requested, accepted;

        public TicketRequest(TicketRequestType type)
        {
            this.type = type;
        }

        public void setAccepted(boolean accepted)
        {
            this.accepted = accepted;
        }

        public void setRequested(boolean requested)
        {
            this.requested = requested;
        }

        public boolean isAccepted()
        {
            return accepted;
        }

        public boolean isRequested()
        {
            return requested;
        }

        public TicketRequestType getType()
        {
            return type;
        }
    }

    public enum TicketRequestType {
        FLY, TELEPORT
    }

    public enum TicketRequestReturnType {
        PLAYER_NOT_FOUND, ALREADY_DONE, DONE, NOT_REQUESTED
    }


}
