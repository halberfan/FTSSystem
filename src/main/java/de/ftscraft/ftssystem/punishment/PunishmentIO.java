/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.punishment;

import de.ftscraft.ftssystem.database.entities.PunishmentEntity;
import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class PunishmentIO {

    private final File folder;

    private final FtsSystem plugin;
    private final PunishmentManager punishmentManager;

    PunishmentIO(FtsSystem plugin, PunishmentManager manager) {
        this.plugin = plugin;
        this.punishmentManager = manager;
        this.folder = new File(plugin.getDataFolder() + "//puns//");
    }

    public void loadPlayerData(UUID player) {
        File file = new File(folder + "//" + player.toString() + ".yml");

        if (!file.exists()) {
            return;
        }

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        for (String key : cfg.getConfigurationSection("punishment").getKeys(false)) {
            PunishmentType type = PunishmentType.valueOf(cfg.getString("punishment." + key + ".type"));
            String reason = cfg.getString("punishment." + key + ".reason");
            String author = cfg.getString("punishment." + key + ".author");
            String moreInfo = cfg.getString("punishment." + key + ".moreInfo");
            long time = cfg.getLong("punishment." + key + ".creation");
            boolean active = cfg.getBoolean("punishment." + key + ".active");
            long until = 0;
            if (cfg.contains("punishment." + key + ".until")) {
                until = cfg.getLong("punishment." + key + ".until");
            }
            int id;
            try {
                id = Integer.parseInt(key);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                return;
            }
            punishmentManager.loadPunishmentFromData(player, type, reason, UUID.fromString(author), moreInfo, time, until, id, active);
        }

    }

    public void savePlayerData(UUID player) {
        //TODO
        /*File file = new File(folder + "//" + player.toString() + ".yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        for (Punishment a : punishmentManager.getPlayers().get(player)) {
            cfg.set("punishment." + a.getID() + ".type", a.getType().toString());
            cfg.set("punishment." + a.getID() + ".reason", a.getReason());
            cfg.set("punishment." + a.getID() + ".author", a.getAuthor());
            cfg.set("punishment." + a.getID() + ".moreInfo", a.getMoreInformation());
            cfg.set("punishment." + a.getID() + ".creation", a.getTime());
            cfg.set("punishment." + a.getID() + ".active", a.isActive());
            if (a instanceof TemporaryPunishment) {
                cfg.set("punishment." + a.getID() + ".until", ((TemporaryPunishment) a).untilInMillis());
            }
        }

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        for (Punishment punishment : punishmentManager.getPlayers().get(player)) {
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

    }

}
