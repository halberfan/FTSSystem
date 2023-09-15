/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import java.util.Calendar;
import java.util.UUID;

public class Note implements Punishment {

    private final String reason;
    private final String author;
    private final String moreInfo;
    private final long time;
    private final UUID player;
    private final int ID;
    private boolean active;

    Note(String reason, String author, long time, UUID player, String moreInfo, int id, boolean active) {
        this.reason = reason;
        this.author = author;
        this.time = time;
        this.player = player;
        this.ID = id;
        this.moreInfo = moreInfo;
        this.active = active;
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

    public String getMoreInformation() {
        return moreInfo;
    }

    @Override
    public String createdOn() {

        Calendar cal = new Calendar.Builder().setInstant(time).build();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);

        return day + "." + (month + 1) + " " + year + " - " + hour + ":" + min;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
