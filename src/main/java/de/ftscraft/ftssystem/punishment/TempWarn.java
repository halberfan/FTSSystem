/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import de.ftscraft.ftssystem.utils.Utils;

import java.util.UUID;

public class TempWarn implements Punishment, Temporary {

    private String reason;
    private String author;
    private long time;
    private long until;
    private UUID player;
    private int ID;

    public TempWarn(String reason, String author, long time, long until, UUID player, int id) {
        this.reason = reason;
        this.author = author;
        this.time = time;
        this.until = until;
        this.player = player;
        this.ID = id;
    }

    @Override
    public PunishmentType getType() {
        return PunishmentType.TEMPWARN;
    }

    @Override
    public UUID getPlayer() {
        return player;
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
    public int getID() {
        return ID;
    }
}
