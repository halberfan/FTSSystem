/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.channel;

import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.integration.Econ;
import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    /**
     * Variables:
     * %fa - Faction Name
     * %pr - Prefix
     * %na - Name
     * %cp - Channel Prefix
     * %ch - Channel Name
     * %msg - Message
     **/

    private FtsSystem plugin;
    public List<Channel> channels = new ArrayList<>();

    public ChatManager(FtsSystem plugin) {
        this.plugin = plugin;
        //channels = new ArrayList<>();
        loadChannels();
    }

    public void chat(User u, String msg) {
        Channel a = u.getActiveChannel();
        if (a == null) {
            u.getPlayer().sendMessage(Messages.CHOOSE_CHANNEL);
            return;
        }
        if(u.getActiveChannel() == null) {
            u.getPlayer().sendMessage(Messages.NO_ACTIVE_CHANNEL);
        }
        String c = format(u, a, msg);
        for (User b : plugin.getUser().values()) {
            if (b.getEnabledChannels().contains(a)) {
                if (a.getRange() != -1) {
                    if (b.getPlayer().getLocation().distance(u.getPlayer().getLocation()) < a.getRange()) {
                        b.getPlayer().sendMessage(c);
                    }
                } else
                    b.getPlayer().sendMessage(c);
            }
        }

    }

    public void chat(User u, String msg, Channel channel) {
        Channel a = channel;
        if (a == null) {
            u.getPlayer().sendMessage(Messages.CHOOSE_CHANNEL);
            return;
        }
        String c = format(u, a, msg);
        for (User b : plugin.getUser().values()) {
            if (b.getEnabledChannels().contains(a)) {
                if (a.getRange() != -1) {
                    if (b.getPlayer().getLocation().distance(u.getPlayer().getLocation()) < a.getRange()) {
                        b.getPlayer().sendMessage(c);
                    }
                } else
                    b.getPlayer().sendMessage(c);
            }
        }

    }

    public String format(User u, Channel c, String msg) {
        String f = c.getFormat();
        String faction = "";
        String prefix = plugin.getChat().getPlayerPrefix(u.getPlayer());
        String name = u.getPlayer().getName();
        String channelName = c.getName();
        if (plugin.factionHooked)
            faction = (MPlayer.get(u.getPlayer()).getFaction().getName());

        f = f.replace("%fa", faction);
        f = f.replace("%pr", prefix);
        f = f.replace("%na", name);
        f = f.replace("%ch", channelName);
        f = f.replace("%cp", c.getPrefix());
        f = f.replace("&", "§");

        f = f.replace("%msg", msg);

        return f;
    }

    public Channel getChannel(String a) {
        for (Channel channel : channels) {
            if (channel.getName().equalsIgnoreCase(a)) {
                return channel;
            }
            if(channel.getPrefix().equalsIgnoreCase(a)) {
                return channel;
            }
        }
        return null;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    private void loadChannels() {
        File file = new File(plugin.getDataFolder() + "//channels.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        try {
            for (String a : cfg.getConfigurationSection("channel").getKeys(false)) {
                String name = a;
                String prefix = cfg.getString("channel." + a + ".prefix");
                boolean defaultChannel = cfg.getBoolean("channel." + a + ".default");
                String permission = cfg.getString("channel." + a + ".permission");
                int range = cfg.getInt("channel." + a + ".range");
                String format = cfg.getString("channel." + a + ".format");
                Channel c = new Channel(plugin, name, prefix, format, defaultChannel, permission, range);
                channels.add(c);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
