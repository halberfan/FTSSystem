/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UUIDFetcher {

    private static Map<String, UUID> uuidCache = new HashMap<>();
    private static Map<UUID, String> nameCache = new HashMap<>();

    private String name;
    private UUID id;

    public static String getName(UUID uuid) {

        if(nameCache.containsKey(uuid))
            return nameCache.get(uuid);

        OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);

        String name = op.getName();

        nameCache.put(uuid, name);

        return name;

    }

    public static UUID getUUID(String name) {

        if(uuidCache.containsKey(name))
            return uuidCache.get(name);

        OfflinePlayer op = Bukkit.getOfflinePlayer(name);

        UUID uuid = op.getUniqueId();

        uuidCache.put(name, uuid);

        return uuid;

    }




}