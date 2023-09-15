/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UUIDFetcher {

    private static final Map<String, UUID> uuidCache = new HashMap<>();
    private static final Map<UUID, String> nameCache = new HashMap<>();

    private String name;
    private UUID id;

    public static String getName(UUID uuid) {

        if (nameCache.containsKey(uuid))
            return nameCache.get(uuid);

        OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);

        String name = op.getName();

        nameCache.put(uuid, name);

        return name;

    }

    public static UUID getUUID(String name) {

        if (uuidCache.containsKey(name))
            return uuidCache.get(name);

        OfflinePlayer op = Bukkit.getOfflinePlayer(name);

        UUID uuid = op.getUniqueId();

        uuidCache.put(name, uuid);

        return uuid;

    }


}