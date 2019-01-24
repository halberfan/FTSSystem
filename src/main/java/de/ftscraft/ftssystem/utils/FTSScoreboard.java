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

import java.util.ArrayList;

public class FTSScoreboard {

    private FtsSystem plugin;
    private ScoreboardManager scoreboardManager;
    public ArrayList<Player> roleplayMode;

    public FTSScoreboard(FtsSystem plugin) {
        this.plugin = plugin;
        this.roleplayMode = new ArrayList<>();
        scoreboardManager = Bukkit.getScoreboardManager();
    }

    public void update() {
        for(Player a : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
            setupScoreboardTeams(scoreboard);
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

    public void setupScoreboardTeams(Scoreboard sb) {
        for(Team t : sb.getTeams()) {
            t.unregister();
        }
        Team team;
        if (sb.getTeam("roleplay_modus") == null)
            team = sb.registerNewTeam("roleplay_modus");
        else team = sb.getTeam("roleplay_modus");
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        for (Player player : roleplayMode) {
            team.addEntry(player.getName());
        }
        try
        {
            sb.registerNewTeam("0000Admin");
            sb.registerNewTeam("0002Moderator");
            sb.registerNewTeam("0003Helfer");
            sb.registerNewTeam("0004Walkure");
            sb.registerNewTeam("0005Einherjer");
            sb.registerNewTeam("0006Architekt");
            sb.registerNewTeam("0007Ehrenburger");
            sb.registerNewTeam("0008Rauber");
            sb.registerNewTeam("0009Richter");
            sb.registerNewTeam("0010Mejster");
            sb.registerNewTeam("0011Konig");
            sb.registerNewTeam("0012Herzog");
            sb.registerNewTeam("0013Furst");
            sb.registerNewTeam("0014Graf");
            sb.registerNewTeam("0015Burgherr");
            sb.registerNewTeam("0016Ritter");
            sb.registerNewTeam("0017Intendant");
            sb.registerNewTeam("0018Kurator");
            sb.registerNewTeam("0019Kaufmann");
            sb.registerNewTeam("0020Gildenherr");
            sb.registerNewTeam("0021Stadtherr");
            sb.registerNewTeam("0022BMeister");
            sb.registerNewTeam("0023Siedler");
            sb.registerNewTeam("0024Vogt");
            sb.registerNewTeam("0025Herold");
            sb.registerNewTeam("0026Knappe");
            sb.registerNewTeam("0027SchauSpieler");
            sb.registerNewTeam("0028Musiker");
            sb.registerNewTeam("0029Schreiber");
            sb.registerNewTeam("0030Seefahrer");
            sb.registerNewTeam("0031Hafenmeister");
            sb.registerNewTeam("0032Handler");
            sb.registerNewTeam("0033Burger");
            sb.registerNewTeam("0034Reisender");
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }



}
