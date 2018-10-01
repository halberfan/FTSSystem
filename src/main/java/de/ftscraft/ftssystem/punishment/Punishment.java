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
    long getTime();
    int getID();
    void remove();

}
