/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

public interface Temporary extends Punishment {

    long untilInMillis();
    String untilAsString();
    String untilAsCalString();

}
