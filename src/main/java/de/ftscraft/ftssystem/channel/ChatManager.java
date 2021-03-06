/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.channel;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.perms.Relation;
import de.ftscraft.ftsengine.utils.Ausweis;
import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    private FPlayers fPlayers;

    public ChatManager(FtsSystem plugin) {
        this.plugin = plugin;
        this.fPlayers = FPlayers.getInstance();
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
            Faction f = fPlayers.getByPlayer(u.getPlayer()).getFaction();

            for (FPlayer b : f.getFPlayers()) {
                if (b.isOnline())
                    if (b.getPlayer() != null)
                        if (plugin.getUser(b.getPlayer()).getEnabledChannels().contains(a))
                            b.getPlayer().sendMessage(c);

            }
        } else if (a.getType() == ChannelType.FACTION_ALLY) {
            Faction f = fPlayers.getByPlayer(u.getPlayer()).getFaction();

            for (FPlayer b : f.getFPlayers()) {
                if (b.getPlayer() != null)
                    if (plugin.getUser(b.getPlayer()).getEnabledChannels().contains(a))
                        b.getPlayer().sendMessage(c);
            }

            for (Player i : Bukkit.getOnlinePlayers()) {
                FPlayer mPlayer = fPlayers.getByPlayer(i);
                if (mPlayer.getFaction().getRelationTo(f) == Relation.ALLY || mPlayer.getFaction().getRelationTo(f) == Relation.TRUCE) {
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


            Faction f = fPlayers.getByPlayer(u.getPlayer()).getFaction();

            for (FPlayer b : f.getFPlayers()) {
                if (b.isOnline())
                    if (b.getPlayer() != null) {
                        if ((plugin.getUser(b.getPlayer()).getEnabledChannels().contains(channel))) {
                            b.getPlayer().sendMessage(c);
                        }
                    }
            }

        } else if (channel.getType() == ChannelType.FACTION_ALLY) {

            Faction f = fPlayers.getByPlayer(u.getPlayer()).getFaction();

            for (FPlayer b : f.getFPlayers()) {
                if (b.getPlayer() != null)
                    b.getPlayer().sendMessage(c);
            }

            for (Player i : Bukkit.getOnlinePlayers()) {
                FPlayer mPlayer = fPlayers.getByPlayer(i);
                if (mPlayer.getFaction().getRelationTo(f) == Relation.ALLY || mPlayer.getFaction().getRelationTo(f) == Relation.TRUCE) {
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
            faction = (fPlayers.getByPlayer(u.getPlayer()).getFaction().getTag());

        f = f.replace("%fa", faction);
        f = f.replace("%pr", prefix);
        //Wenn der Spieler im RP Modus ist, wird der eigentliche Name mit dem Namen ausgetauscht der im Ausweis angegeben ist, wenn ein Ausweis vorhanden ist
        if (plugin.getScoreboardManager().isInRoleplayMode(u.getPlayer())) {
            Ausweis ausweis = plugin.getEngine().getAusweis(u.getPlayer());
            //Wenn der Ausweis nicht existiert, die Variable mit dem normalen Spielernamen ergenzen
            if (ausweis == null || c.getName().equalsIgnoreCase("Global") || c.getName().equalsIgnoreCase("OOC")) {
                f = f.replace("%na", name);
            } else {
                f = f.replace("%na", ChatColor.GREEN + ausweis.getFirstName() + " " + ausweis.getLastName() + ChatColor.RESET);
            }
        } else
            f = f.replace("%na", name);
        f = f.replace("%ch", channelName);
        f = f.replace("%cp", c.getPrefix());
        f = f.replace("&", "§");

        f = f.replace("%msg", (u.getPlayer().hasPermission("ftssystem.chat.color") ? ChatColor.translateAlternateColorCodes('&', msg) : msg));
        f = f.replace("((", "§7((");
        f = f.replace("))", "§7))§r");

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
