/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.misc;

import de.ftscraft.ftssystem.main.User;

public class Duel {

    private User challenger;
    private int challengerHealth;
    private User opponent;
    private int opponentHealth;

    private DuelStatus status;

    private int timer;

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
