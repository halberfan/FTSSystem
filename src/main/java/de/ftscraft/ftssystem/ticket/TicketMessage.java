/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.ticket;

public class TicketMessage {

    private String uuid;
    private String message;
    private long creation;

    public TicketMessage(String uuid, String message, long creation)
    {
        this.uuid = uuid;
        this.message = message;
        this.creation = creation;
    }
}
