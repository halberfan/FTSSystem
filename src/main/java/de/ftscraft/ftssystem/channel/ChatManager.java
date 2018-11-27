/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.channel;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
        if (u.getActiveChannel() == null) {
            u.getPlayer().sendMessage(Messages.NO_ACTIVE_CHANNEL);
        }
        String c = format(u, a, msg);
        if (a.getType() == ChannelType.NORMAL || a.getType() == null) {
            boolean anyoneRecived = false;
            for (User b : plugin.getUser().values()) {
                if (b.getEnabledChannels().contains(a)) {
                    if (a.getRange() != -1) {
                        if (b.getPlayer().getWorld().getName().equalsIgnoreCase(u.getPlayer().getWorld().getName())) {
                            if (b.getPlayer().getLocation().distance(u.getPlayer().getLocation()) <= a.getRange()) {
                                if (b != u)
                                    anyoneRecived = true;
                                b.getPlayer().sendMessage(c);
                            }
                        }
                    } else {
                        b.getPlayer().sendMessage(c);
                        if (b != u)
                            anyoneRecived = true;
                    }
                }
            }

            if (!anyoneRecived) {
                u.getPlayer().sendMessage("§cNiemand hat deine Nachricht gelesen. Schreibe ein ! vor deine Nachricht um in den Globalchat zu schreiben");
            }

        } else if (a.getType() == ChannelType.FACTION_F) {
            Faction f = MPlayer.get(u.getPlayer()).getFaction();

            for (MPlayer b : f.getMPlayers()) {
                if (b.isOnline())
                    b.getPlayer().sendMessage(c);
            }
        } else if (a.getType() == ChannelType.FACTION_ALLY) {
            Faction f = MPlayer.get(u.getPlayer()).getFaction();

            for (MPlayer b : f.getMPlayers()) {
                if (b.getPlayer() != null)
                    b.getPlayer().sendMessage(c);
            }

            for (Player i : Bukkit.getOnlinePlayers()) {
                MPlayer mPlayer = MPlayer.get(i);
                if (mPlayer.getFaction().getRelationTo(f) == Rel.ALLY || mPlayer.getFaction().getRelationTo(f) == Rel.TRUCE) {
                    i.sendMessage(c);
                }
            }

        }

    }

    public void chat(User u, String msg, Channel channel) {
        if (channel == null) {
            u.getPlayer().sendMessage(Messages.CHOOSE_CHANNEL);
            return;
        }
        if (!u.getEnabledChannels().contains(channel)) {
            try {
                u.joinChannel(channel);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }

        String c = format(u, channel, msg);

        if (channel.getType() == ChannelType.NORMAL || channel.getType() == null) {

            boolean anyoneRecived = false;
            for (User b : plugin.getUser().values()) {
                if (b.getEnabledChannels().contains(channel)) {
                    if (channel.getRange() != -1) {
                        if (b.getPlayer().getWorld().getName().equalsIgnoreCase(u.getPlayer().getWorld().getName())) {
                            if (b.getPlayer().getLocation().distance(u.getPlayer().getLocation()) <= channel.getRange()) {
                                if (b != u)
                                    anyoneRecived = true;
                                b.getPlayer().sendMessage(c);
                            }
                        }
                    } else {
                        b.getPlayer().sendMessage(c);
                        if (b != u)
                            anyoneRecived = true;
                    }
                }
            }

            

            if (!anyoneRecived) {
                u.getPlayer().sendMessage("§cNiemand hat deine Nachricht gelesen. Schreibe ein ! vor deine Nachricht um in den Globalchat zu schreiben");
            }

        } else if (channel.getType() == ChannelType.FACTION_F) {


            Faction f = MPlayer.get(u.getPlayer()).getFaction();

            for (MPlayer b : f.getMPlayers()) {
                if (b.isOnline())
                    b.getPlayer().sendMessage(c);
            }

        } else if (channel.getType() == ChannelType.FACTION_ALLY) {

            Faction f = MPlayer.get(u.getPlayer()).getFaction();

            for (MPlayer b : f.getMPlayers()) {
                if (b.getPlayer() != null)
                    b.getPlayer().sendMessage(c);
            }

            for (Player i : Bukkit.getOnlinePlayers()) {
                MPlayer mPlayer = MPlayer.get(i);
                if (mPlayer.getFaction().getRelationTo(f) == Rel.ALLY || mPlayer.getFaction().getRelationTo(f) == Rel.TRUCE) {
                    i.sendMessage(c);
                }
            }

        }
    }

    private String format(User u, Channel c, String msg) {
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
        f = f.replace("((", "§7((");

        return f;
    }

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

    private void loadChannels() {
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
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

}
