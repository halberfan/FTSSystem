/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.poll;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static de.ftscraft.ftssystem.utils.Utils.sendMessageToAllExceptDisturb;
import static de.ftscraft.ftssystem.utils.Utils.sendMessageToAllPlayers;

import java.util.ArrayList;
import java.util.HashMap;

public class Umfrage {

    private String frage;
    private ArrayList<String> antwortmoglichkeiten;
    private HashMap<String, Integer> antworten;
    private ArrayList<Player> teilnehmer;
    private boolean started = false;
    private FtsSystem plugin;

    public Umfrage(String frage, FtsSystem plugin) {
        this.frage = frage;
        this.plugin = plugin;
        antworten = new HashMap<>();
        teilnehmer = new ArrayList<>();
        antwortmoglichkeiten = new ArrayList<>();
    }

    public void addAntwort(String antwort) {
        antworten.put(antwort, 0);
        antwortmoglichkeiten.add(antwort);
    }

    public void addVote(Player p, int antwort) {
        if (teilnehmer.contains(p)) {
            p.sendMessage(Messages.PREFIX + "Du hast schon gevotet!");
            return;
        }
        teilnehmer.add(p);
        String a = antwortmoglichkeiten.get(antwort);
        antworten.put(a, antworten.get(a) + 1);
        p.sendMessage("§cDu hast erfolgreich für §7" + a + " §cgestimmt");
    }

    public void start() {
        started = true;
        sendPollMessage(false);
    }

    public void sendPollMessage(boolean resend) {

        for (Player all : Bukkit.getOnlinePlayers()) {

            if (plugin.getUser(all).isDisturbable()) {

                if (!resend) {
                    all.sendMessage(Messages.PREFIX + "Es wurde eine Umfrage gestartet:");
                } else
                    all.sendMessage(Messages.PREFIX + "Es läuft derzeit eine Umfrage");
                all.sendMessage("§7Frage: §c" + frage);
                for (int i1 = 0; i1 < antwortmoglichkeiten.size(); i1++) {
                    String i = antwortmoglichkeiten.get(i1);
                    TextComponent a = new TextComponent(i);
                    a.setColor(ChatColor.BLUE);
                    a.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/umfrage vote " + i1));

                    all.spigot().sendMessage(a);
                    if (i1 == antwortmoglichkeiten.size() - 1) {
                        if (teilnehmer.contains(all)) {
                            all.sendMessage("§c(Keine Sorge, du hast schon abgestimmt)");
                        } else {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                        }
                    }
                }

            }

        }


    }

    public void end() {
        started = false;
        sendMessageToAllExceptDisturb(Messages.PREFIX + "Die Umfrage wurde beendet!", plugin);
        sendResultMessage();
    }

    private void sendResultMessage() {
        int teilnehmerzahl = teilnehmer.size();
        sendMessageToAllExceptDisturb(Messages.PREFIX + "Es haben " + teilnehmerzahl + " Leute abgestimmt", plugin);
        for (String a : antwortmoglichkeiten) {
            int w = antworten.get(a);
            String p = ((double) w / (double) teilnehmerzahl * (double) 100) + "%";
            Bukkit.broadcastMessage(Messages.PREFIX + "Es haben §c" + w + "(" + p + ") §7für §c" + a + " §7gestimmt!");
        }
    }

    public boolean isStarted() {
        return started;
    }

    public ArrayList<Player> getTeilnehmer() {
        return teilnehmer;
    }

    public void sendToPlayer(Player player) {
        player.sendMessage(Messages.PREFIX + "Es läuft derzeit eine Umfrage");
        player.sendMessage("§7Frage: §c" + frage);
        for (int i1 = 0; i1 < antwortmoglichkeiten.size(); i1++) {
            String i = antwortmoglichkeiten.get(i1);
            TextComponent a = new TextComponent(i);
            a.setColor(ChatColor.BLUE);
            a.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/umfrage vote " + i1));
            player.spigot().sendMessage(a);
            if (i1 == antwortmoglichkeiten.size() - 1) {
                if (teilnehmer.contains(player)) {
                    player.sendMessage("§c(Keine Sorge, du hast schon abgestimmt)");
                } else {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                }
            }
        }
    }
}
