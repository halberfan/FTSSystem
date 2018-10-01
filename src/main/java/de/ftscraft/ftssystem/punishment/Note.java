/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import java.util.UUID;

public class Note implements Punishment {

    private String reason;
    private String author;
    private long time;
    private UUID player;
    private int ID;

    public Note(String reason, String author, long time, UUID player, int id) {
        this.reason = reason;
        this.author = author;
        this.time = time;
        this.player = player;
        this.ID = id;
    }

    @Override
    public PunishmentType getType() {
        return PunishmentType.NOTE;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public void remove() {

    }

    @Override
    public UUID getPlayer() {
        return player;
    }

    @Override
    public int getID() {
        return ID;
    }

}
