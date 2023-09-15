/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.channel.chatmanager;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import de.ftscraft.ftsengine.utils.Ausweis;
import de.ftscraft.ftsengine.utils.Gender;
import de.ftscraft.ftssystem.channel.Channel;
import de.ftscraft.ftssystem.channel.ChannelType;
import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import de.ftscraft.ftssystem.scoreboard.TeamPrefixs;
import org.bukkit.ChatColor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ReichChatManager extends ChatManager {

    final TownyAPI api;

    public ReichChatManager(FtsSystem plugin) {
        super(plugin);
        api = TownyAPI.getInstance();
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
            Resident resident = api.getResident(u.getPlayer().getUniqueId());

            if (resident.hasTown()) {

                try {
                    for (Resident townResidents : resident.getTown().getResidents()) {
                        if (townResidents.getPlayer() != null) {
                            if (townResidents != resident) {
                                if (plugin.getUser(townResidents.getPlayer()).getEnabledChannels().contains(a))
                                    townResidents.getPlayer().sendMessage(c);
                            }
                        }
                    }
                } catch (NotRegisteredException e) {
                    e.printStackTrace();
                }
            }

        } else if (a.getType() == ChannelType.FACTION_ALLY) {

            Resident resident = api.getResident(u.getPlayer().getUniqueId());

            if (resident.hasTown()) {
                try {
                    for (Resident nationResident : resident.getTown().getNation().getResidents()) {
                        if (nationResident.getPlayer() != null) {
                            if (nationResident != resident) {
                                if (plugin.getUser(nationResident.getPlayer()).getEnabledChannels().contains(a))
                                    nationResident.getPlayer().sendMessage(c);
                            }
                        }
                    }
                } catch (NotRegisteredException e) {
                    e.printStackTrace();
                }

            }


            Logger.getLogger("Minecraft").log(Level.INFO, "[Chat] " + u.getPlayer().getName() + " [" + a.getPrefix() + "] " + msg);
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
            } catch (Exception ex) {
                ex.printStackTrace();
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


            if (!anyoneRecived && !u.getPlayer().hasPermission("ftssystem.chat.noinfo")) {
                u.getPlayer().sendMessage("§cNiemand hat deine Nachricht gelesen. Schreibe ein ! vor deine Nachricht um in den Globalchat zu schreiben");
            }

        } else if (channel.getType() == ChannelType.FACTION_F) {

            Resident resident = api.getResident(u.getPlayer().getUniqueId());

            if (resident.hasTown()) {

                try {
                    for (Resident townResidents : resident.getTown().getResidents()) {
                        if (townResidents.getPlayer() != null) {
                            if (townResidents != resident) {
                                if (plugin.getUser(townResidents.getPlayer()).getEnabledChannels().contains(channel))
                                    townResidents.getPlayer().sendMessage(c);
                            }
                        }
                    }
                } catch (NotRegisteredException e) {
                    e.printStackTrace();
                }
            }

        } else if (channel.getType() == ChannelType.FACTION_ALLY) {

            Resident resident = api.getResident(u.getPlayer().getUniqueId());

            if (resident.hasTown()) {
                try {
                    for (Resident nationResident : resident.getTown().getNation().getResidents()) {
                        if (nationResident.getPlayer() != null) {
                            if (nationResident != resident) {
                                if (plugin.getUser(nationResident.getPlayer()).getEnabledChannels().contains(channel))
                                    nationResident.getPlayer().sendMessage(c);
                            }
                        }
                    }
                } catch (NotRegisteredException e) {
                    e.printStackTrace();
                }

            }


            Logger.getLogger("Minecraft").log(Level.INFO, "[Chat] " + u.getPlayer().getName() + " [" + channel.getPrefix() + "] " + msg);

        }
    }

    private String format(User u, Channel c, String msg) {

        String f = c.getFormat();
        String faction = "§2Wildnis";

        Ausweis ausweis = plugin.getEngine().getAusweis(u.getPlayer());
        Gender gender;
        if (ausweis == null)
            gender = Gender.MALE;
        else
            gender = ausweis.getGender();
        if (gender == null)
            gender = Gender.MALE;

        String prefix = TeamPrefixs.getPrefix(u.getPlayer(), gender);
        String name = u.getPlayer().getName();
        String channelName = c.getName();

        if (api.getResident(u.getPlayer().getUniqueId()).hasTown()) {

            try {
                faction = api.getResident(u.getPlayer().getUniqueId()).getTown().getName();
            } catch (NotRegisteredException e) {
                e.printStackTrace();
            }

        }

        f = f.replace("%fa", faction);
        f = f.replace("%pr", prefix);
        //Wenn der Spieler im RP Modus ist, wird der eigentliche Name mit dem Namen ausgetauscht der im Ausweis angegeben ist, wenn ein Ausweis vorhanden ist
        if (plugin.getScoreboardManager().isInRoleplayMode(u.getPlayer())) {
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

}
