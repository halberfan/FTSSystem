/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.database.entities.PunishmentEntity;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.TimeUnits;
import de.ftscraft.ftssystem.utils.UUIDFetcher;
import de.ftscraft.ftssystem.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;

public class PunishmentManager {

    private final FtsSystem plugin;

    private final HashMap<UUID, List<Punishment>> players = new HashMap<>();
    private final HashMap<Player, PunishmentBuilder> builders;

    public PunishmentManager(FtsSystem plugin) {
        this.plugin = plugin;
        builders = new HashMap<>();
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
                        if (((TemporaryPunishment) punishment).untilInMillis() < System.currentTimeMillis()) {
                            continue;
                        }
                    }

                    numberOfPunishments++;
                }

            }

        }

        if (!isBanned(player)) {

            if (numberOfPunishments == 3) {

                addTempBan("System: 3 Strafen erhalten", UUID.fromString("291af7c7-2114-45bb-a97a-d3b4077392e8"), UUIDFetcher.getName(player), "Der Spieler hat 3 Strafen erhalten und wurde deshalb bestraft.", "2d");

            }

            if (numberOfPunishments > 6) {

                addBan("System: 6 Strafen (oder mehr) erhalten", UUID.fromString("291af7c7-2114-45bb-a97a-d3b4077392e8"), UUIDFetcher.getName(player), "Der Spieler hat 6 (oder mehr) Strafen erhalten und wurde deshalb bestraft.");

            }
        }

    }

    public void loadPunishmentFromData(UUID player, PunishmentType type, String reason, UUID author, String moreInfo, long time, long until, int id, boolean active) {
        Punishment pu;
        switch (type) {
            case WARN -> pu = new Warn(reason, author, time, player, moreInfo, id, active);
            case TEMP_WARN -> pu = new TempWarn(reason, author, time, until, player, moreInfo, id, active);
            case NOTE -> pu = new Note(reason, author, time, player, moreInfo, id, active);
            case TEMP_BAN -> pu = new TempBan(reason, author, time, until, player, moreInfo, id, active);
            case TEMP_MUTE -> pu = new TempMute(reason, author, time, until, player, moreInfo, id, active);
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

    public boolean addWarn(String reason, UUID author, String playerName, String moreInfo) {
        UUID uuid = UUIDFetcher.getUUID(playerName);
        if (uuid == null) return false;

        PunishmentEntity punishmentEntity = new PunishmentEntity(
                reason,
                author,
                System.currentTimeMillis(),
                uuid.toString(),
                moreInfo,
                true,
                -1,
                PunishmentType.WARN.toString()
        );
        Punishment punishment = saveNewPunishmentToDatabase(punishmentEntity);
        addPunishmentToPlayer(uuid, punishment);

        Player op = Bukkit.getPlayer(playerName);
        if (op != null) {
            op.sendMessage(Messages.PREFIX + "Du hast einen Warn von §c" + punishment.getAuthorName() + " §7wegen §c" + punishment.getReason() + "§7 erhalten. Dieser Warn ist Permanent");
        } else {
            Utils.runConsoleCommand( "mail send " + playerName + " Du hast einen Warn von §c" + punishment.getAuthorName() + " §7wegen §c" + punishment.getReason() + "§7 erhalten. Dieser Warn ist Permanent", plugin);
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.hasPermission("ftssystem.punish")) {
                all.sendMessage(Messages.PREFIX + playerName + " hat von " + punishment.getAuthorName() + " einen Permanenten Warn erhalten wegen: " + punishment.getReason());
            }
        }

        checkForAutoBan(uuid);

        return true;
    }

    public boolean addTempwarn(String reason, UUID author, String playerName, String moreInfo, String unit) {
        //Get UUID from Player
        UUID uuid = UUIDFetcher.getUUID(playerName);

        long until = Utils.calculateUntil(unit);
        if (until == -1)
            return false;

        PunishmentEntity punishmentEntity = new PunishmentEntity(
                reason,
                author,
                System.currentTimeMillis(),
                uuid.toString(),
                moreInfo,
                true,
                until,
                PunishmentType.TEMP_WARN.toString()
        );
        TemporaryPunishment punishment = (TemporaryPunishment) saveNewPunishmentToDatabase(punishmentEntity);
        addPunishmentToPlayer(uuid, punishment);

        Player op = Bukkit.getPlayer(playerName);
        if (op != null) {
            op.sendMessage(Messages.PREFIX + "Du hast ein Warn von §c" + punishment.getAuthorName() + " §7wegen §c" +
                    punishment.getReason() + "§7 erhalten. Dieser Warn verschwindet in" + punishment.untilAsString());
        } else {
            Utils.runConsoleCommand("mail send " + playerName +
                    "Du hast ein Warn von §c" + punishment.getAuthorName() + " §7wegen §c" + punishment.getReason() +
                    "§7 erhalten. Dieser Warn verschwindet in" + punishment.untilAsString(), plugin);
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.hasPermission("ftssystem.punish")) {
                all.sendMessage(Messages.PREFIX + playerName + " hat von " + punishment.getAuthorName() +
                        " einen Temp Warn erhalten wegen: " + punishment.getReason());
            }
        }


        checkForAutoBan(uuid);

        return true;
    }

    public boolean addNote(String reason, UUID author, String playerName, String moreInfo) {
        UUID uuid = UUIDFetcher.getUUID(playerName);
        PunishmentEntity punishmentEntity = new PunishmentEntity(
                reason,
                author,
                System.currentTimeMillis(),
                uuid.toString(),
                moreInfo,
                true,
                -1,
                PunishmentType.NOTE.toString()
        );
        addPunishmentToPlayer(uuid, saveNewPunishmentToDatabase(punishmentEntity));

        return true;
    }

    public boolean addTempBan(String reason, UUID author, String playerName, String moreInfo, String unit) {
        //Get UUID from Player
        UUID uuid = UUIDFetcher.getUUID(playerName);

        //Get Current Millis
        long current = System.currentTimeMillis();

        long until = Utils.calculateUntil(unit);
        if (until == -1)
            return false;

        PunishmentEntity punishmentEntity = new PunishmentEntity(
                reason,
                author,
                System.currentTimeMillis(),
                uuid.toString(),
                moreInfo,
                true,
                until,
                PunishmentType.TEMP_BAN.toString()
        );
        TemporaryPunishment punishment = (TemporaryPunishment) saveNewPunishmentToDatabase(punishmentEntity);
        addPunishmentToPlayer(uuid, punishment);

        Player op = Bukkit.getPlayer(playerName);
        if (op != null) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> op.kickPlayer("§4Du wurdest gebannt! \n" + "§eGebannt von: §b" + punishment.getAuthorName() + "\n" + "§eBis: " + punishment.untilAsString() + "\n" + "§eGrund: §b" + reason + "\n" + " \n" + "§6Du kannst ein Entbannungsbeitrag im Forum schreiben"), 4);
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.hasPermission("ftssystem.punish")) {
                all.sendMessage(FtsSystem.PREFIX + "§c" + playerName + " §7wurde für " + punishment.untilAsString() + " von §c" + punishment.getAuthorName() + " §7wegen §c" + reason + " §7gebannt");
            }
        }

        checkForAutoBan(uuid);
        return true;
    }

    public boolean addBan(String reason, UUID author, String playerName, String moreInfo) {
        UUID uuid = UUIDFetcher.getUUID(playerName);
        if (uuid == null) return false;

        PunishmentEntity punishmentEntity = new PunishmentEntity(
                reason,
                author,
                System.currentTimeMillis(),
                uuid.toString(),
                moreInfo,
                true,
                -1,
                PunishmentType.BAN.toString()
        );
        Punishment punishment = saveNewPunishmentToDatabase(punishmentEntity);
        addPunishmentToPlayer(uuid, punishment);

        Player op = Bukkit.getPlayer(playerName);
        if (op != null) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> op.kickPlayer("§4Du wurdest gebannt! \n" + "§eGebannt von: §b" + punishment.getAuthorName() + "\n" + "§eBis: §bPERMANENT \n" + "§eGrund: §b" + reason + "\n" + " \n" + "§6Du kannst ein Entbannungsbeitrag im Forum schreiben"), 4);
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.hasPermission("ftssystem.punish")) {
                all.sendMessage(FtsSystem.PREFIX + "§c" + playerName + " §7wurde permanent von §c" + author + " §7wegen §c" + reason + " §7gebannt");
            }
        }

        addPunishmentToPlayer(uuid, punishment);

        return true;
    }

    public boolean addTempMute(String reason, UUID author, String playerName, String moreInfo, String unit) {
        //Get UUID from Player
        UUID uuid = UUIDFetcher.getUUID(playerName);

        long until = Utils.calculateUntil(unit);
        if (until == -1)
            return false;
        PunishmentEntity punishmentEntity = new PunishmentEntity(
                reason,
                author,
                System.currentTimeMillis(),
                uuid.toString(),
                moreInfo,
                true,
                until,
                PunishmentType.TEMP_MUTE.toString()
        );
        TemporaryPunishment punishment = (TemporaryPunishment) saveNewPunishmentToDatabase(punishmentEntity);
        addPunishmentToPlayer(uuid, punishment);

        checkForAutoBan(uuid);
        return true;
    }

    public HashMap<UUID, List<Punishment>> getPlayers() {
        return players;
    }

    public Punishment isBanned(Player player) {
        if (!isLoaded(player.getUniqueId()))
            loadPlayer(player.getUniqueId());
        if (players.get(player.getUniqueId()) == null) return null;
        for (Punishment a : players.get(player.getUniqueId())) {
            if (a.getType() == PunishmentType.BAN) {
                if (a.isActive()) return a;
            } else if (a.getType() == PunishmentType.TEMP_BAN) {
                if (a.isActive()) if (((TemporaryPunishment) a).untilInMillis() > System.currentTimeMillis()) return a;
            }
        }
        return null;
    }

    public boolean isBanned(UUID player) {
        if (!isLoaded(player))
            loadPlayer(player);
        loadPlayer(player);
        if (players.get(player) == null) return false;
        for (Punishment a : players.get(player)) {
            if (a.getType() == PunishmentType.BAN) {
                if (a.isActive()) return true;
            } else if (a.getType() == PunishmentType.TEMP_BAN) {
                if (a.isActive())
                    if (((TemporaryPunishment) a).untilInMillis() > System.currentTimeMillis()) return true;
            }
        }
        return false;
    }

    public Punishment isMuted(Player player) {
        if (!isLoaded(player.getUniqueId()))
            loadPlayer(player.getUniqueId());
        if (players.get(player.getUniqueId()) == null) return null;
        for (Punishment a : players.get(player.getUniqueId())) {
            if (a.getType() == PunishmentType.TEMP_MUTE) {
                if (a.isActive()) if (((TemporaryPunishment) a).untilInMillis() > System.currentTimeMillis()) return a;
            }
        }
        return null;
    }

    public boolean isMuted(UUID player) {
        if (!isLoaded(player))
            loadPlayer(player);
        if (players.get(player) == null) return false;
        for (Punishment a : players.get(player)) {
            if (a.getType() == PunishmentType.TEMP_MUTE) {
                if (a.isActive())
                    if (((TemporaryPunishment) a).untilInMillis() > System.currentTimeMillis()) return true;
            }
        }
        return false;
    }

    private boolean isLoaded(UUID uuid) {
        return players.containsKey(uuid);
    }

    public void loadPlayer(UUID uuid) {
        try {
            QueryBuilder<PunishmentEntity, Integer> queryBuilder = plugin.getDatabaseManager().getPunishmentsDao().queryBuilder();
            List<PunishmentEntity> punishmentEntities = plugin.getDatabaseManager().getPunishmentsDao().query(queryBuilder.where().eq("player", uuid.toString()).prepare());
            for (PunishmentEntity punishmentEntity : punishmentEntities) {
                addPunishmentToPlayer(uuid, punishmentEntity.turnIntoPunishment());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Punishment saveNewPunishmentToDatabase(PunishmentEntity punishmentEntity) {
        try {
            plugin.getDatabaseManager().getPunishmentsDao().createOrUpdate(punishmentEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return punishmentEntity.turnIntoPunishment();
    }

    public boolean deletePunishment(int punishmentId) {
        try {
            PunishmentEntity entity = plugin.getDatabaseManager().getPunishmentsDao().queryForId(punishmentId);
            plugin.getDatabaseManager().getPunishmentsDao().delete(entity);
            Punishment delete = null;
            List<Punishment> punishments = players.get(UUID.fromString(entity.getPlayer()));
            for (Punishment punishment : punishments) {
                if (punishment.getID() == punishmentId)
                    delete = punishment;
            }
            if (delete != null) {
                punishments.remove(delete);
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Punishment getPunishmentById(int id) {

        for (List<Punishment> list : players.values()) {
            for (Punishment pu : list) {
                if (pu.getID() == id) return pu;
            }
        }

        return null;

    }


    public void savePunishment(Punishment punishment) {
        PunishmentEntity punishmentEntity = new PunishmentEntity();
        punishmentEntity.setType(punishment.getType().toString());
        punishmentEntity.setReason(punishment.getReason());
        punishmentEntity.setAuthor(punishment.getAuthor().toString());
        punishmentEntity.setMoreInfo(punishment.getMoreInfo());
        punishmentEntity.setTime(punishment.getTime());
        punishmentEntity.setActive(punishment.isActive());
        punishmentEntity.setID(punishment.getID());
        punishmentEntity.setPlayer(punishment.getPlayer().toString());
        if (PunishmentType.isTemporary(punishment.getType())) {
            punishmentEntity.setUntil(((TemporaryPunishment) punishment).getUntil());
        }
        try {
            plugin.getDatabaseManager().getPunishmentsDao().createOrUpdate(punishmentEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public enum ChatProgress {
        REASON, MOREINFO, TIME, PROOF
    }

    public HashMap<Player, PunishmentBuilder> getBuilders() {
        return builders;
    }
}
