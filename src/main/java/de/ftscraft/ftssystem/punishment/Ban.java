/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import java.util.UUID;

public class Ban extends Punishment {

    public Ban(String reason, UUID author, long time, UUID player, String moreInfo, int id, boolean active) {
        super(reason, author, time, player, moreInfo, id, active);
    }

    @Override
    public PunishmentType getType() {
        return PunishmentType.BAN;
    }

}
