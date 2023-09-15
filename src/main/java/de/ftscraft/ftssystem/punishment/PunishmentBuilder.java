/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.TimeUnits;
import de.ftscraft.ftssystem.utils.UUIDFetcher;
import de.ftscraft.ftssystem.utils.Utils;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class PunishmentBuilder {

    private PunishmentType type;
    private String player;
    private String author;
    private String moreInfo;
    private String reason;

    private String until;

    private boolean proofed = false;

    private PunishmentManager.ChatProgress chatProgress;

    private final FtsSystem plugin;
    private final Player creator;

    public PunishmentBuilder(FtsSystem plugin, PunishmentType type, Player player, String target) {
        this.type = type;
        this.plugin = plugin;
        this.creator = player;
        this.author = player.getName();
        this.player = target;
        plugin.getPunishmentManager().getBuilders().put(player, this);
    }


    public PunishmentType getType() {
        return type;
    }

    public void setType(PunishmentType type) {
        this.type = type;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String untilString) {
        this.until = untilString;
    }

    public long getUntilInMillis() {

        //Get Current Millis
        long current = System.currentTimeMillis();

        String[] u = Utils.splitToNumbers(this.until);
        //Check if its 2 size big for 1 Number + 1 Unit
        if (u.length != 2) {
            return 0;
        }
        //Init Until value
        long until = 0;

        for (int i = 0; i < u.length; i++) {
            //If i == 0 -> Its the Number
            if (i == 0) {
                try {
                    until = Integer.parseInt(u[i]);
                } catch (NumberFormatException ex) {
                    return 0;
                }
                //If i == 1 -> Its the Unit
            } else {
                //Check if Unit exists
                if (TimeUnits.getTimeUnitByUnit(u[i]) == null) {
                    return 0;
                }
                //Calculate until
                //Get Millis from TimeUnit
                long time = Objects.requireNonNull(TimeUnits.getTimeUnitByUnit(u[i])).getMillis();
                //Get Millis from TimeUnit * how many of these
                until = until * time;
                //Get final Millis from Current Millis + the Millis of duration
                until = until + current;
            }
        }
        return until;
    }

    public PunishmentManager.ChatProgress getChatProgress() {
        return chatProgress;
    }

    public void setChatProgress(PunishmentManager.ChatProgress chatProgress) {
        this.chatProgress = chatProgress;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void build() {
        if (!proofed) {
            creator.sendMessage(Messages.PREFIX + "Irgendwas ist schief gelaufen. (Wahrscheinlich bei der: Bestätigung)");
            return;
        }
        UUID target = UUIDFetcher.getUUID(player);
        switch (type) {
            case NOTE -> plugin.getPunishmentManager().addNote(reason, author, player, moreInfo);
            case TEMPWARN -> plugin.getPunishmentManager().addTempwarn(reason, author, player, moreInfo, until);
            case WARN -> plugin.getPunishmentManager().addWarn(reason, author, player, moreInfo);
            case TEMPMUTE -> plugin.getPunishmentManager().addTempMute(reason, author, player, moreInfo, until);
            case TEMPBAN -> plugin.getPunishmentManager().addTempBan(reason, author, player, moreInfo, until);
            case BAN -> plugin.getPunishmentManager().addBan(reason, author, player, moreInfo);
            default -> creator.sendMessage("§cIrgendwas ist schief gelaufen. Überprüfe nochmal deine Daten");
        }
    }

    public void abort() {
        plugin.getPunishmentManager().getBuilders().remove(creator);
    }

    public void setProofed(boolean proofed) {
        this.proofed = proofed;
    }
}
