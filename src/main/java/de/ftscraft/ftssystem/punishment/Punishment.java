/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import java.util.UUID;

public interface Punishment {

    PunishmentType getType();
    UUID getPlayer();
    String getReason();
    String getAuthor();
    String getMoreInformation();
    long getTime();
    int getID();
    String createdOn();
    boolean isActive();
    void setActive(boolean active);
    void remove();

}
