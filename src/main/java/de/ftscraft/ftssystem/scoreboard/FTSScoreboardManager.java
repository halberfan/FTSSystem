/*
 * Copyright (c) 2020.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.scoreboard;

import de.ftscraft.ftsengine.utils.Ausweis;
import de.ftscraft.ftsengine.utils.Gender;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.survivalminus.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class FTSScoreboardManager {

    private List<Player> playerInRpMode = new ArrayList<>();

    private List<Player> blinkingPlayers = new ArrayList<>();

    private List<Player> sneakingPlayers = new ArrayList<>();

    private List<Player> afkPlayers = new ArrayList<>();

    private FtsSystem plugin;

    public FTSScoreboardManager(FtsSystem plugin) {
        this.plugin = plugin;
    }

    public void sendToAllScoreboard() {

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            sendScoreboardToPlayer(onlinePlayer, true);
        }

    }

    public void sendScoreboardToPlayer(Player p, boolean all) {

        ScoreboardManager sm = Bukkit.getScoreboardManager();

        setPlayerPrefix(p);

        Scoreboard s = sm.getNewScoreboard();

        Thread thread = new Thread(() -> {

            Team rpTeam = s.registerNewTeam("100rpmode");
            if (!sneakingPlayers.contains(p))
                rpTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);

            User u = plugin.getSurvival().getUser(p.getName());

            if (plugin.getUser(p).isScoreboardEnabled()) {

                Objective objective;
                if (s.getObjective(p.getName()) == null)
                    objective = s.registerNewObjective(p.getName(), "dummy");
                else {
                    objective = s.getObjective(p.getName());
                    objective.unregister();
                    objective = s.registerNewObjective(p.getName(), "dummy");
                }
                objective.setDisplayName("§lGesundheit");
                if (u.getKohlenhydrate() <= 5 || u.getProteine() <= 5 || u.getThirst() <= 5 || u.getVitamine() <= 5) {
                    if (blinkingPlayers.contains(p)) {
                        blinkingPlayers.remove(p);
                        objective.setDisplayName("§4§lGesundheit");
                    } else blinkingPlayers.add(p);
                } else {
                    blinkingPlayers.remove(p);
                }
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                Score s1 = objective.getScore("§6Kohlenhydrate: §c" + u.getKohlenhydrate());
                Score s2 = objective.getScore("§6Durst: §c" + u.getThirst());
                Score s3 = objective.getScore("§6Proteine: §c" + u.getProteine());
                Score s4 = objective.getScore("§6Vitamine: §c" + u.getVitamine());
                if (plugin.getPest() != null) {
                    Score s6;
                    String infection = plugin.getPest().getInfectionManager().getUser(p).getDiseaseName();
                    s6 = objective.getScore("§6Krankheit: §c" + infection);
                    Score s7 = objective.getScore("§6Fortschritt: §c" + plugin.getPest().getInfectionManager().getUser(p).getSicknessLevel());
                    s6.setScore(3);
                    s7.setScore(2);
                }
                Score s8 = objective.getScore("§4--------");

                Score s9 = objective.getScore("§6Taler: §c" + round(plugin.getEcon().getBalance(p), 0));
                s9.setScore(1);
                s8.setScore(4);
                s4.setScore(5);
                s3.setScore(6);
                s2.setScore(7);
                s1.setScore(8);
            }

            ArrayList<Player> isRanked = new ArrayList<>();

            for (Player player : playerInRpMode) {
                if (player != null) {
                    rpTeam.addEntry(player.getName());
                    isRanked.add(player);
                }
            }

            if (all) {
                for (TeamPrefixs value : TeamPrefixs.values()) {
                    Team team = s.registerNewTeam(value.getTeamName());
                    Team teamf = s.registerNewTeam(value.getTeamName() + "F");

                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (isRanked.contains(onlinePlayer))
                            continue;
                        if (onlinePlayer.hasPermission(value.getPermission())) {
                            Ausweis ausweis = plugin.getEngine().getAusweis(onlinePlayer);
                            if (ausweis == null || ausweis.getGender() == null || ausweis.getGender() == Gender.MALE || ausweis.getGender() == Gender.DIVERS)
                                team.addEntry(onlinePlayer.getName());
                            else if (ausweis.getGender() == Gender.FEMALE)
                                teamf.addEntry(onlinePlayer.getName());
                            isRanked.add(onlinePlayer);
                        }
                    }

                }
            }


            p.setScoreboard(s);

        });

        thread.start();

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

    public void changeAfkStatus(Player p, boolean afk) {

        if (afk) {
            if (!afkPlayers.contains(p))
                afkPlayers.add(p);
        } else {
            if (afkPlayers.contains(p)) {
                afkPlayers.remove(p);
            }
        }

        setPlayerPrefix(p);

    }

    public TeamPrefixs setPlayerPrefix(Player p) {
        Ausweis ausweis = plugin.getEngine().getAusweis(p);
        Gender gender;
        if (ausweis != null)
            gender = ausweis.getGender();
        else gender = Gender.MALE;

        for (TeamPrefixs a : TeamPrefixs.values()) {
            if (p.hasPermission(a.getPermission())) {
                if (!playerInRpMode.contains(p))
                    p.setPlayerListName(a.getPrefix(gender) + " §7| §r" + p.getName());
                else
                    p.setPlayerListName(a.getPrefix(gender) + " §7| §r" + p.getName() + " §7[RP]");
                if (afkPlayers.contains(p)) {
                    p.setPlayerListName(p.getPlayerListName().replaceAll("§.", "§7§m"));
                }
                if (p.hasPermission("group.comhelfer")) {
                    appendCommunityHelferTag(p);
                }
                return a;
            }
        }
        TeamPrefixs a = TeamPrefixs.NEULING;
        if (!playerInRpMode.contains(p))
            p.setPlayerListName(a.getPrefix(gender) + " §7| §r" + p.getName());
        else
            p.setPlayerListName(a.getPrefix(gender) + " §7| §r" + p.getName() + " §7[RP]");

        if (p.hasPermission("group.comhelfer")) {
            appendCommunityHelferTag(p);
        }

        return a;
    }

    public boolean isInRoleplayMode(Player p) {

        return playerInRpMode.contains(p);

    }

    public void toggleSneakingMode(Player p) {
        if (!sneakingPlayers.contains(p))
            sneakingPlayers.add(p);
        else sneakingPlayers.remove(p);

        sendScoreboardToPlayer(p, false);
    }

    public void appendCommunityHelferTag(Player p) {
        if (plugin.getPerms().playerInGroup(p, "comhelfer")) {
            p.playerListName(p.playerListName().append(Component.text(" [C]", Style.style(TextColor.color(252, 152, 3)))));
        }
    }

}
