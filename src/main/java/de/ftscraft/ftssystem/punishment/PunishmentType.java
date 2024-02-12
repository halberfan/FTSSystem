/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

public enum PunishmentType {

    NOTE, TEMP_WARN, WARN, TEMP_MUTE, TEMP_BAN, BAN;

    public static boolean isTemporary(PunishmentType type) {
        return type == TEMP_WARN || type == TEMP_MUTE || type == TEMP_BAN;
    }


}
