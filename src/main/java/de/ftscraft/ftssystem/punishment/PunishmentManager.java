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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PunishmentManager {

    private final FtsSystem plugin;
    private int latestID;

    private final HashMap<UUID, List<Punishment>> players = new HashMap<>();
    private final HashMap<Player, PunishmentBuilder> builders;
    private final PunishmentIO punishmentIO;

    public PunishmentManager(FtsSystem plugin) {
        this.plugin = plugin;
        builders = new HashMap<>();
        punishmentIO = new PunishmentIO(plugin, this);
        this.latestID = plugin.getConfigManager().getLatestPunID();
    }

    private void addPunishmentToPlayer(UUID player, Punishment pu) {
        if (players.get(player) == null) {
            players.put(player, new ArrayList<>());
            players.get(player).add(pu);
        } else {

            players.get(player).add(pu);

        }

    }

    public void checkForAutoBan(UUID player) {

        int numberOfPunishments = 0;
        for (Punishment punishment : players.get(player)) {
            if (punishment.getType() != PunishmentType.NOTE) {

                if (punishment.isActive()) {

                    if (punishment instanceof TempWarn) {
                        if (((Temporary) punishment).untilInMillis() < System.currentTimeMillis()) {
                            continue;
                        }
                    }

                    numberOfPunishments++;
                }

            }

        }

        if (!isBanned(player)) {

            if (numberOfPunishments == 3) {

                addTempBan("System: 3 Strafen erhalten",
                        "System",
                        UUIDFetcher.getName(player),
                        "Der Spieler hat 3 Strafen erhalten und wurde deshalb bestraft.",
                        "2d");

            }

            if (numberOfPunishments > 6) {

                addBan("System: 6 Strafen (oder mehr) erhalten",
                        "System",
                        UUIDFetcher.getName(player),
                        "Der Spieler hat 6 (oder mehr) Strafen erhalten und wurde deshalb bestraft.");

            }
        }

    }

    public void loadPunishmentFromData(UUID player, PunishmentType type, String reason, String author, String moreInfo, long time, long until, int id, boolean active) {
        Punishment pu;
        switch (type) {
            case WARN -> pu = new Warn(reason, author, time, player, moreInfo, id, active);
            case TEMPWARN -> pu = new TempWarn(reason, author, time, until, player, moreInfo, id, active);
            case NOTE -> pu = new Note(reason, author, time, player, moreInfo, id, active);
            case TEMPBAN -> pu = new TempBan(player, reason, author, time, until, moreInfo, id, active);
            case TEMPMUTE -> pu = new TempMute(reason, author, time, until, player, moreInfo, id, active);
            case BAN -> pu = new Ban(reason, author, time, player, moreInfo, id, active);
            default -> {
                return;
            }
        }
        addPunishmentToPlayer(player, pu);
    }

    public void clearData(UUID player) {
        players.remove(player);
    }

    public boolean addWarn(String reason, String author, String playerName, String moreInfo) {
        UUID uuid = UUIDFetcher.getUUID(playerName);
        if (uuid == null)
            return false;

        Warn warn = new Warn(reason, author, System.currentTimeMillis(), uuid, moreInfo, latestID, true);
        latestID++;
        addPunishmentToPlayer(uuid, warn);
        punishmentIO.savePlayerData(uuid);

        Player op = Bukkit.getPlayer(playerName);
        if (op != null) {
            op.sendMessage(Messages.PREFIX + "Du hast ein Warn von §c" + warn.getAuthor() + " §7wegen §c" + warn.getReason() + "§7 erhalten. Dieser Warn ist Permanent");
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + playerName + "Du hast ein Warn von §c" + warn.getAuthor() + " §7wegen §c" + warn.getReason() + "§7 erhalten. Dieser Warn ist Permanent");
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.hasPermission("ftssystem.punish")) {
                all.sendMessage(Messages.PREFIX + playerName + " hat von " + warn.getAuthor() + " einen Permanenten Warn erhalten wegen: " + warn.getReason());
            }
        }

        checkForAutoBan(uuid);

        return true;
    }

    public boolean addTempwarn(String reason, String author, String playerName, String moreInfo, String unit) {
        //Get UUID from Player
        UUID uuid = UUIDFetcher.getUUID(playerName);

        //Get Current Millis
        long current = System.currentTimeMillis();

        String[] u = Utils.splitToNumbers(unit);
        //Check if its 2 size big for 1 Number + 1 Unit
        if (u.length != 2) {
            return false;
        }
        //Init Until value
        long until = 0;

        for (int i = 0; i < u.length; i++) {
            //If i == 0 -> Its the Number
            if (i == 0) {
                try {
                    until = Integer.parseInt(u[i]);
                } catch (NumberFormatException ex) {
                    return false;
                }
                //If i == 1 -> Its the Unit
            } else {
                //Check if Unit exists
                if (TimeUnits.getTimeUnitByUnit(u[i]) == null) {
                    return false;
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
        TempWarn tempWarn = new TempWarn(reason, author, current, until, uuid, moreInfo, latestID, true);
        latestID++;
        addPunishmentToPlayer(uuid, tempWarn);
        punishmentIO.savePlayerData(uuid);

        Player op = Bukkit.getPlayer(playerName);
        if (op != null) {
            op.sendMessage(Messages.PREFIX + "Du hast ein Warn von §c" + tempWarn.getAuthor() + " §7wegen §c" + tempWarn.getReason() + "§7 erhalten. Dieser Warn verschwindet in" + tempWarn.untilAsString());
        } else {

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + playerName + "Du hast ein Warn von §c" + tempWarn.getAuthor() + " §7wegen §c" + tempWarn.getReason() + "§7 erhalten. Dieser Warn verschwindet in" + tempWarn.untilAsString());
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.hasPermission("ftssystem.punish")) {
                all.sendMessage(Messages.PREFIX + playerName + " hat von " + tempWarn.getAuthor() + " einen Temp Warn erhalten wegen: " + tempWarn.getReason());
            }
        }


        checkForAutoBan(uuid);

        return true;
    }

    public boolean addNote(String reason, String author, String playerName, String moreInfo) {
        UUID uuid = UUIDFetcher.getUUID(playerName);
        if (uuid == null)
            return false;
        Note note = new Note(reason, author, System.currentTimeMillis(), uuid, moreInfo, latestID, true);
        latestID++;
        addPunishmentToPlayer(uuid, note);
        punishmentIO.savePlayerData(uuid);

        return true;
    }

    public boolean addTempBan(String reason, String author, String playerName, String moreInfo, String unit) {
        //Get UUID from Player
        UUID uuid = UUIDFetcher.getUUID(playerName);

        //Get Current Millis
        long current = System.currentTimeMillis();

        String[] u = Utils.splitToNumbers(unit);
        //Check if its 2 size big for 1 Number + 1 Unit
        assert u != null;
        if (u.length != 2) {
            return false;
        }
        //Init Until value
        long until = 0;

        for (int i = 0; i < u.length; i++) {
            //If i == 0 -> Its the Number
            if (i == 0) {
                try {
                    until = Integer.parseInt(u[i]);
                } catch (NumberFormatException ex) {
                    return false;
                }
                //If i == 1 -> Its the Unit
            } else {
                //Check if Unit exists
                if (TimeUnits.getTimeUnitByUnit(u[i]) == null) {
                    return false;
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
        TempBan tempBan = new TempBan(uuid, reason, author, current, until, moreInfo, latestID, true);
        latestID++;
        Player op = Bukkit.getPlayer(playerName);
        if (op != null) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> op.kickPlayer(
                    "§4Du wurdest gebannt! \n" +
                    "§eGebannt von: §b" + author + "\n" +
                    "§eBis: " + tempBan.untilAsString() + "\n" +
                    "§eGrund: §b" + reason + "\n" +
                    " \n" +
                    "§6Du kannst ein Entbannungsbeitrag im Forum schreiben"), 4);
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.hasPermission("ftssystem.punish")) {
                all.sendMessage(FtsSystem.PREFIX + "§c" + playerName + " §7wurde für " + tempBan.untilAsString() + " von §c" + author + " §7wegen §c" + reason + " §7gebannt");
            }
        }

        addPunishmentToPlayer(uuid, tempBan);
        punishmentIO.savePlayerData(uuid);

        checkForAutoBan(uuid);
        return true;
    }

    public boolean addBan(String reason, String author, String playerName, String moreInfo) {
        UUID uuid = UUIDFetcher.getUUID(playerName);
        if (uuid == null)
            return false;
        Ban ban = new Ban(reason, author, System.currentTimeMillis(), uuid, moreInfo, latestID, true);
        latestID++;
        Player op = Bukkit.getPlayer(playerName);
        if (op != null) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> op.kickPlayer(
                    "§4Du wurdest gebannt! \n" +
                    "§eGebannt von: §b" + author + "\n" +
                    "§eBis: §bPERMANENT \n" +
                    "§eGrund: §b" + reason + "\n" +
                    " \n" +
                    "§6Du kannst ein Entbannungsbeitrag im Forum schreiben"), 4);
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.hasPermission("ftssystem.punish")) {
                all.sendMessage(FtsSystem.PREFIX + "§c" + playerName + " §7wurde permanent von §c" + author + " §7wegen §c" + reason + " §7gebannt");
            }
        }

        addPunishmentToPlayer(uuid, ban);

        punishmentIO.savePlayerData(uuid);

        return true;
    }

    public boolean addTempMute(String reason, String author, String playerName, String moreInfo, String unit) {
        //Get UUID from Player
        UUID uuid = UUIDFetcher.getUUID(playerName);

        //Get Current Millis
        long current = System.currentTimeMillis();

        String[] u = Utils.splitToNumbers(unit);
        //Check if its 2 size big for 1 Number + 1 Unit
        assert u != null;
        if (u.length != 2) {
            return false;
        }
        //Init Until value
        long until = 0;

        for (int i = 0; i < u.length; i++) {
            //If i == 0 -> Its the Number
            if (i == 0) {
                try {
                    until = Integer.parseInt(u[i]);
                } catch (NumberFormatException ex) {
                    return false;
                }
                //If i == 1 -> Its the Unit
            } else {
                //Check if Unit exists
                if (TimeUnits.getTimeUnitByUnit(u[i]) == null) {
                    return false;
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
        TempMute tempMute = new TempMute(reason, author, current, until, uuid, moreInfo, latestID, true);
        latestID++;
        addPunishmentToPlayer(uuid, tempMute);
        punishmentIO.savePlayerData(uuid);

        checkForAutoBan(uuid);
        return true;
    }

    public HashMap<UUID, List<Punishment>> getPlayers() {
        return players;
    }

    public Punishment isBanned(Player player) {
        clearData(player.getUniqueId());
        loadPlayer(player.getUniqueId());
        if (players.get(player.getUniqueId()) == null)
            return null;
        for (Punishment a : players.get(player.getUniqueId())) {
            if (a.getType() == PunishmentType.BAN) {
                if (a.isActive())
                    return a;
            } else if (a.getType() == PunishmentType.TEMPBAN) {
                if (a.isActive())
                    if (((Temporary) a).untilInMillis() > System.currentTimeMillis())
                        return a;
            }
        }
        return null;
    }

    public boolean isBanned(UUID player) {
        clearData(player);
        loadPlayer(player);
        if (players.get(player) == null)
            return false;
        for (Punishment a : players.get(player)) {
            if (a.getType() == PunishmentType.BAN) {
                if (a.isActive())
                    return true;
            } else if (a.getType() == PunishmentType.TEMPBAN) {
                if (a.isActive())
                    if (((Temporary) a).untilInMillis() > System.currentTimeMillis())
                        return true;
            }
        }
        return false;
    }

    public Punishment isMuted(Player player) {
        clearData(player.getUniqueId());
        loadPlayer(player.getUniqueId());
        if (players.get(player.getUniqueId()) == null)
            return null;
        for (Punishment a : players.get(player.getUniqueId())) {
            if (a.getType() == PunishmentType.TEMPMUTE) {
                if (a.isActive())
                    if (((Temporary) a).untilInMillis() > System.currentTimeMillis())
                        return a;
            }
        }
        return null;
    }

    public boolean isMuted(UUID player) {
        clearData(player);
        loadPlayer(player);
        if (players.get(player) == null)
            return false;
        for (Punishment a : players.get(player)) {
            if (a.getType() == PunishmentType.TEMPMUTE) {
                if (a.isActive())
                    if (((Temporary) a).untilInMillis() > System.currentTimeMillis())
                        return true;
            }
        }
        return false;
    }

    private boolean isLoaded(UUID uuid) {
        return players.containsKey(uuid);
    }

    public void loadPlayer(UUID uuid) {
        punishmentIO.loadPlayerData(uuid);
    }

    public int getLatestID() {
        return latestID;
    }

    public Punishment getPunishmentById(int id) {

        for (List<Punishment> list : players.values()) {
            for (Punishment pu : list) {
                if (pu.getID() == id)
                    return pu;
            }
        }

        return null;

    }


    public void savePlayer(UUID player) {
        punishmentIO.savePlayerData(player);
    }


    public enum ChatProgress {
        REASON, MOREINFO, TIME, PROOF
    }

    public HashMap<Player, PunishmentBuilder> getBuilders() {
        return builders;
    }
}
