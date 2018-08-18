/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class FTSScoreboard {

    private FtsSystem plugin;
    private ScoreboardManager scoreboardManager;

    public FTSScoreboard(FtsSystem plugin) {
        this.plugin = plugin;
        scoreboardManager = Bukkit.getScoreboardManager();
    }

    public void load() {
        Scoreboard scoreboard = scoreboardManager.getMainScoreboard();
        Objective objective = scoreboard.registerNewObjective("", "");
    }

}
