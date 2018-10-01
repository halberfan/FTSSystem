/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import de.ftscraft.ftssystem.utils.Utils;

import java.util.UUID;

public class TempBan implements Punishment, Temporary {

    private UUID player;
    private String reason;
    private String author;
    private long time;
    private long until;
    private int ID;

    public TempBan(UUID player, String reason, String author, long time, long until, int id) {
        this.player = player;
        this.reason = reason;
        this.author = author;
        this.time = time;
        this.until = until;
        this.ID = id;
    }

    @Override
    public PunishmentType getType() {
        return PunishmentType.TEMPBAN;
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
    public long untilInMillis() {
        return until;
    }

    @Override
    public String untilAsString() {
        return Utils.convertToTime(until);
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
