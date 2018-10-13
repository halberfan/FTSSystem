/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

public enum PunishmentType {

    NOTE, TEMPWARN, WARN, TEMPMUTE, TEMPBAN, BAN;

    public static boolean isTemporary(PunishmentType type) {
        return type == TEMPWARN || type == TEMPMUTE || type == TEMPBAN;
    }

}
