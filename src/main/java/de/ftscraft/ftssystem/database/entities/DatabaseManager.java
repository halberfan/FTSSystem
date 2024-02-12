package de.ftscraft.ftssystem.database.entities;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {

    private final Dao<PunishmentEntity, Integer> punishmentsDao;

    public DatabaseManager(String path) throws SQLException {
        ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:"+path);

        TableUtils.createTableIfNotExists(connectionSource, PunishmentEntity.class);
        punishmentsDao = DaoManager.createDao(connectionSource, PunishmentEntity.class);

    }

    public Dao<PunishmentEntity, Integer> getPunishmentsDao() {
        return punishmentsDao;
    }
}
