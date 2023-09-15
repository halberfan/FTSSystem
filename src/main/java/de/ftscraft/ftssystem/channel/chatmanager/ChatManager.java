/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.channel.chatmanager;

import de.ftscraft.ftssystem.channel.Channel;
import de.ftscraft.ftssystem.channel.ChannelType;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ChatManager {

    public final List<Channel> channels = new ArrayList<>();

    final FtsSystem plugin;

    public ChatManager(FtsSystem plugin) {
        this.plugin = plugin;
        loadChannels();
    }

    public abstract void chat(User u, String msg, Channel channel);

    public abstract void chat(User u, String msg);

    public Channel getChannel(String a) {
        for (Channel channel : channels) {
            if (channel.getName().equalsIgnoreCase(a)) {
                return channel;
            }
            if (channel.getPrefix().equalsIgnoreCase(a)) {
                return channel;
            }
        }
        return null;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    void loadChannels() {
        File file = new File(plugin.getDataFolder() + "//channels.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            cfg.set("channel.global.prefix", "G");
            cfg.set("channel.global.default", false);
            cfg.set("channel.global.permission", "ftssystem.chat.global");
            cfg.set("channel.global.range", -1);
            cfg.set("channel.global.format", "§8[%cp]§r[%pr§7:§r%fa§r] %na: %msg");

            cfg.set("channel.local.prefix", "L");
            cfg.set("channel.local.default", true);
            cfg.set("channel.local.permission", "ftssystem.chat.local");
            cfg.set("channel.local.range", 40);
            cfg.set("channel.local.format", "[%pr§7:§r%fa§r] %na: %msg");

            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            for (String a : cfg.getConfigurationSection("channel").getKeys(false)) {
                String prefix = cfg.getString("channel." + a + ".prefix");
                boolean defaultChannel = cfg.getBoolean("channel." + a + ".default");
                String permission = cfg.getString("channel." + a + ".permission");
                int range = cfg.getInt("channel." + a + ".range");
                String format = cfg.getString("channel." + a + ".format");
                ChannelType channelType = null;
                if (cfg.contains("channel." + a + ".type"))
                    channelType = ChannelType.valueOf(cfg.getString("channel." + a + ".type").toUpperCase());
                Channel c = new Channel(plugin, a, prefix, format, defaultChannel, permission, range, channelType);
                channels.add(c);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
