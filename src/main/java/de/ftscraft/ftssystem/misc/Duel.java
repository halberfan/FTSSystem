/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.misc;

import de.ftscraft.ftssystem.main.User;

public class Duel {

    private final User challenger;
    private int challengerHealth;
    private final User opponent;
    private int opponentHealth;

    private final DuelStatus status;

    private final int timer;

    public Duel(User challenger, User opponent) {
        this.challenger = challenger;
        this.opponent = opponent;
        this.status = DuelStatus.REQUEST;
        this.timer = -1;
    }


    public enum DuelStatus {

        REQUEST, PREPARATION, FIGHT, END

    }


}
