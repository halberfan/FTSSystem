/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.main;

import de.ftscraft.ftssystem.channel.Channel;
import de.ftscraft.ftssystem.configs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private FtsSystem plugin;
    private Player player;

    private boolean scoreboardEnabled = true;

    private boolean approved = false;

    private Channel activeChannel;
    private List<Channel> enabledChannels;

    private boolean turnedServerMessagesOn;

    private HashMap<Player, Integer> fights;

    public User(FtsSystem plugin, Player p) {
        this.player = p;
        this.plugin = plugin;
        enabledChannels = new ArrayList<>();
        fights = new HashMap<>();
        plugin.getUser().put(p.getName(), this);
        getData();
    }

    public Channel getActiveChannel() {
        return activeChannel;
    }

    public List<Channel> getEnabledChannels() {
        return enabledChannels;
    }

    public Player getPlayer() {
        return player;
    }

    public void save() {

        File file = new File(plugin.getDataFolder() + "//user//"+player.getUniqueId().toString()+".yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        //Chat
        List<String> chNames = new ArrayList<>();

        for(Channel a : enabledChannels) {
            chNames.add(a.getName());
        }

        cfg.set("channels", chNames.toArray());
        cfg.set("activeChannel", activeChannel.getName());
        cfg.set("scoreboardOn", isScoreboardEnabled());
        cfg.set("approved", approved);
        cfg.set("turnedServerMessagesOn", turnedServerMessagesOn);

        //Punishment


        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getData()
    {
        File file = new File(plugin.getDataFolder() + "//user//" + player.getUniqueId().toString() + ".yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {

            player.performCommand("tutorialbook");

            for (Player a : Bukkit.getOnlinePlayers()) {
                a.sendMessage("§cDer Spieler §e" + player.getName() + " §cist das 1. mal hier. Sagt Hallo!");
            }



        }
        List channelList = cfg.getList("channels");
        if (channelList != null) {
            String[] channels = (String[]) channelList.toArray(new String[channelList.size()]);
            for (String a : channels) {
                Channel b = plugin.getChatManager().getChannel(a);
                if (b != null) {
                    enabledChannels.add(b);
                }
            }
        }
        if (enabledChannels.isEmpty()) {
            for (Channel a : plugin.getChatManager().getChannels()) {
                if (player.hasPermission(a.getPermission())) {
                    enabledChannels.add(a);
                }
            }
        }

        this.activeChannel = plugin.getChatManager().getChannel(cfg.getString("activeChannel"));
        if (this.activeChannel == null)
            this.activeChannel = plugin.getChatManager().getChannel("Local");

        this.scoreboardEnabled = !cfg.contains("scoreboardOn") || cfg.getBoolean("scoreboardOn");
        this.approved = cfg.contains("approved") && cfg.getBoolean("approved");
        if(cfg.contains("turnedServerMessagesOn")) {
            this.turnedServerMessagesOn = cfg.getBoolean("turnedServerMessagesOn");
        } else this.turnedServerMessagesOn = true;
    }

    public void joinChannel(Channel channel) {
        enabledChannels.add(channel);
        if(activeChannel == null)
            activeChannel = channel;
    }

    public void leaveChannel(Channel channel) {
        enabledChannels.remove(channel);
        if(activeChannel == channel)
            activeChannel = null;
    }

    public void setActiveChannel(Channel activeChannel) {
        this.activeChannel = activeChannel;
        if(!enabledChannels.contains(activeChannel))
            enabledChannels.add(activeChannel);
    }

    public void toggleChannel(Channel channel) {
        if(enabledChannels.contains(channel)) {
            leaveChannel(channel);
            player.sendMessage(Messages.LEFT_CHANNEL.replace("%s", channel.getName()));
        } else {
            setActiveChannel(channel);
            player.sendMessage(Messages.NOW_ACTIVE_CHANNEL.replace("%s", channel.getName()));
        }
    }

    public boolean isScoreboardEnabled() {
        return scoreboardEnabled;
    }

    public void setScoreboardEnabled(boolean scoreboardEnabled) {
        this.scoreboardEnabled = scoreboardEnabled;
    }

    public HashMap<Player, Integer> getFights() {
        return fights;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean b) {
        approved = b;
    }

    public boolean turnedServerMessagesOn() {
        return turnedServerMessagesOn;
    }

    public void setTurnedServerMessagesOn(boolean turnedServerMessagesOn) {
        this.turnedServerMessagesOn = turnedServerMessagesOn;
    }
}
