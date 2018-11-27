/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.survivalminus.user.User;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class FTSScoreboard {

    private FtsSystem plugin;
    private ScoreboardManager scoreboardManager;

    public FTSScoreboard(FtsSystem plugin) {
        this.plugin = plugin;
        scoreboardManager = Bukkit.getScoreboardManager();
    }

    public void update() {
        for(Player a : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
            User u = plugin.getSurvival().getUser(a.getName());

            Objective objective;
            if(scoreboard.getObjective(a.getName()) == null)
                objective = scoreboard.registerNewObjective(a.getName(), "dummy");
            else {
                objective = scoreboard.getObjective(a.getName());
                objective.unregister();
                objective = scoreboard.registerNewObjective(a.getName(), "dummy");
            }
            objective.setDisplayName("§lGesundheit");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            Score s1 = objective.getScore("§6Kohlenhydrate: §c"+u.getKohlenhydrate());
            Score s2 = objective.getScore("§6Durst: §c"+u.getThirst());
            Score s3 = objective.getScore("§6Proteine: §c"+u.getProteine());
            Score s4 = objective.getScore("§6Vitamine: §c"+u.getVitamine());
            //Score s6 = objective.getScore("§6Krankheit: §c"+plugin.getDisease().getDisease(a));
            Score s7 = objective.getScore("§4--------");
            Score s8 = objective.getScore("§6Geld: §c"+plugin.getEcon().getBalance(a));
            Score s9 = objective.getScore("§6Ping: §c"+((CraftPlayer)a).getHandle().ping);
            s9.setScore(0);
            s8.setScore(1);
            s7.setScore(2);
            //s6.setScore(3);
            s4.setScore(5);
            s3.setScore(6);
            s2.setScore(7);
            s1.setScore(8);
            a.setScoreboard(scoreboard);
        }
    }

}
