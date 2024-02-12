/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import de.ftscraft.ftssystem.utils.Utils;

import java.util.Calendar;
import java.util.UUID;

public class TempMute extends TemporaryPunishment {

    public TempMute(String reason, UUID author, long time, long until, UUID player, String moreInfo, int id, boolean active) {
        super(reason, author, time, until, player, moreInfo, id, active);
    }

    @Override
    public PunishmentType getType() {
        return PunishmentType.TEMP_MUTE;
    }

}
