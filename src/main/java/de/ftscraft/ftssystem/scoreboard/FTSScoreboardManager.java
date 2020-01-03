/*
 * Copyright (c) 2020.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.scoreboard;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.survivalminus.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import javax.print.attribute.standard.MediaSize;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FTSScoreboardManager {

    /*
    TODO: Team für /roleplay, Teams für Tab, Scoreboard für Gesundheit und Geld und Krankheit, Sidebar ausschaltbar machen
     */

    private List<Player> playerInRpMode = new ArrayList<>();

    private FtsSystem plugin;

    public FTSScoreboardManager(FtsSystem plugin) {
        this.plugin = plugin;
    }

    public void sendToAllScoreboard() {

        ScoreboardManager sm = Bukkit.getScoreboardManager();

        for (Player p : Bukkit.getOnlinePlayers()) {

            Scoreboard s = sm.getNewScoreboard();

            Team rpTeam = s.registerNewTeam("100rpmode");
            rpTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);

            User u = plugin.getSurvival().getUser(p.getName());

            Objective objective;
            if (s.getObjective(p.getName()) == null)
                objective = s.registerNewObjective(p.getName(), "dummy");
            else {
                objective = s.getObjective(p.getName());
                objective.unregister();
                objective = s.registerNewObjective(p.getName(), "dummy");
            }
            objective.setDisplayName("§lGesundheit");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            Score s1 = objective.getScore("§6Kohlenhydrate: §c" + u.getKohlenhydrate());
            Score s2 = objective.getScore("§6Durst: §c" + u.getThirst());
            Score s3 = objective.getScore("§6Proteine: §c" + u.getProteine());
            Score s4 = objective.getScore("§6Vitamine: §c" + u.getVitamine());
            //Score s6 = objective.getScore("§6Krankheit: §c"+plugin.getDisease().getDisease(a));
            Score s7 = objective.getScore("§4--------");

            Score s8 = objective.getScore("§6Geld: §c" + round(plugin.getEcon().getBalance(p), 0));
            s8.setScore(1);
            s7.setScore(2);
            //s6.setScore(3);
            s4.setScore(5);
            s3.setScore(6);
            s2.setScore(7);
            s1.setScore(8);

            ArrayList<Player> isRanked = new ArrayList<>();

            for (TeamPrefixs value : TeamPrefixs.values()) {
                Team team = s.registerNewTeam(value.getTeamName());

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (isRanked.contains(onlinePlayer))
                        continue;
                    if (onlinePlayer.hasPermission(value.getPermission())) {
                        team.addEntry(onlinePlayer.getName());
                        isRanked.add(onlinePlayer);
                    }
                }

            }

            for (Player player : playerInRpMode) {
                if (player != null) {
                    rpTeam.addEntry(player.getName());
                }
            }


            p.setScoreboard(s);

        }

    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void switchToRoleplayMode(Player p) {
        if (!playerInRpMode.contains(p)) {
            playerInRpMode.add(p);
            p.sendMessage("§7Du bist nun im Rollenspiel-Modus!");
        } else {
            playerInRpMode.remove(p);
            p.sendMessage("§7Du bist nun nicht mehr im Rollenspiel-Modus!");
        }

    }


}
