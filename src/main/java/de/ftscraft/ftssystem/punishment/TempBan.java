/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import de.ftscraft.ftssystem.utils.Utils;

import java.util.Calendar;
import java.util.UUID;

public class TempBan implements Punishment, Temporary {

    private final UUID player;
    private final String reason;
    private final String author;
    private final String moreInfo;
    private final long time;
    private final long until;
    private final int ID;
    private boolean active;

    TempBan(UUID player, String reason, String author, long time, long until, String moreInfo, int id, boolean active) {
        this.player = player;
        this.reason = reason;
        this.author = author;
        this.time = time;
        this.until = until;
        this.moreInfo = moreInfo;
        this.ID = id;
        this.active = active;
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

    @Override
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
    public String untilAsCalString() {

        Calendar cal = new Calendar.Builder().setInstant(until).build();
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
