/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.channel.chatmanager;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.perms.Relation;
import de.ftscraft.ftsengine.utils.Ausweis;
import static de.ftscraft.ftsengine.utils.Ausweis.Gender;
import de.ftscraft.ftssystem.channel.Channel;
import de.ftscraft.ftssystem.channel.ChannelType;
import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import de.ftscraft.ftssystem.scoreboard.TeamPrefixs;
import de.ftscraft.ftssystem.utils.Utils;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FTSChatManager extends ChatManager {

    /**
     * Variables:
     * %fa - Faction Name
     * %pr - Prefix
     * %na - Name
     * %cp - Channel Prefix
     * %ch - Channel Name
     * %msg - Message
     **/

    public List<Channel> channels = new ArrayList<>();

    private final FPlayers fPlayers;

    public FTSChatManager(FtsSystem plugin) {
        super(plugin);
        this.fPlayers = FPlayers.getInstance();
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

        chat(u, msg, a);

    }

    private BaseComponent[] buildComponent(String text, String user) {

        ComponentBuilder componentBuilder = new ComponentBuilder();
        String code = "";
        for (String s : text.split(" ")) {
            if (s.startsWith("§")) {
                code = s.substring(1, 2);
            }
            if (s.length() > 4 && (s.startsWith("https://") || s.startsWith("http://") || s.substring(2).startsWith("https://") || s.substring(2).startsWith("http://"))) {
                if (s.substring(2).startsWith("https://") || s.substring(2).startsWith("http://")) {
                    s = s.substring(2);
                }
                TextComponent textComponent = new TextComponent("§b[LINK]§r");
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, s));
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Utils.getTitleFromWebsite(s) + "\n" + "§7" + s)));
                componentBuilder.append(textComponent);
                componentBuilder.append(" ").event((HoverEvent) null).event((ClickEvent) null);
            } else if (s.startsWith("§r§a")) {
                TextComponent textComponent = new TextComponent(s.replace("_", " "));
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(user)));
                componentBuilder.append(textComponent);
                componentBuilder.append(" ").event((HoverEvent) null);
            } else {
                if (!code.isEmpty())
                    componentBuilder.append(new TextComponent("§" + code + s + " "));
                else componentBuilder.append(s + " ");
            }

            if (s.contains("§"))
                code = String.valueOf(s.charAt(s.lastIndexOf("§") + 1));

        }
        return componentBuilder.create();
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

        String formatted = format(u, channel, msg);

        ComponentBuilder componentBuilder = new ComponentBuilder(formatted);
        componentBuilder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(u.getPlayer().getName()).create()));
        BaseComponent[] c = buildComponent(formatted, u.getPlayer().getName());

        if (channel.getType() == ChannelType.NORMAL || channel.getType() == null) {

            boolean anyoneRecived = false;
            for (User b : plugin.getUser().values()) {
                if (b.getEnabledChannels().contains(channel)) {
                    switch (channel.getPrefix().toLowerCase()) {
                        case "g" -> {
                            if (b.getGlobalChannelStatus() == User.ChannelStatusSwitch.OFF) {
                                continue;
                            } else if (b.getGlobalChannelStatus() == User.ChannelStatusSwitch.RP && plugin.getScoreboardManager().isInRoleplayMode(b.getPlayer())) {
                                continue;
                            }
                        }
                        case "ooc" -> {
                            if (b.getOocChannelStatus() == User.ChannelStatusSwitch.OFF) {
                                continue;
                            } else if (b.getOocChannelStatus() == User.ChannelStatusSwitch.RP && plugin.getScoreboardManager().isInRoleplayMode(b.getPlayer())) {
                                continue;
                            }
                        }
                    }
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

            Faction f = fPlayers.getByPlayer(u.getPlayer()).getFaction();

            for (FPlayer b : f.getFPlayers()) {
                if (b.isOnline())
                    if (b.getPlayer() != null) {
                        if ((plugin.getUser(b.getPlayer()).getEnabledChannels().contains(channel))) {
                            User t = plugin.getUser(b.getPlayer());
                            if (t.getFactionChannelStatus() == User.ChannelStatusSwitch.OFF) {
                                continue;
                            } else if (t.getFactionChannelStatus() == User.ChannelStatusSwitch.RP && plugin.getScoreboardManager().isInRoleplayMode(b.getPlayer())) {
                                continue;
                            }
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

        FtsSystem.getPluginLogger().info("[Chat] " + u.getPlayer().getName() + " [" + channel.getPrefix() + "] " + msg);

    }

    private String format(User u, Channel c, String msg) {
        String f = c.getFormat();
        String faction = "";

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
        if (plugin.factionHooked)
            faction = (fPlayers.getByPlayer(u.getPlayer()).getFaction().getTag());

        f = f.replace("%fa", faction);
        f = f.replace("%pr", prefix);
        f = f.replace("%ch", channelName);
        f = f.replace("%cp", c.getPrefix());
        f = f.replace("&", "§");
        //Wenn der Spieler im RP Modus ist, wird der eigentliche Name mit dem Namen ausgetauscht der im Ausweis angegeben ist, wenn ein Ausweis vorhanden ist
        if (plugin.getScoreboardManager().isInRoleplayMode(u.getPlayer())) {
            //Wenn der Ausweis nicht existiert, die Variable mit dem normalen Spielernamen ergenzen
            if (ausweis == null || c.getName().equalsIgnoreCase("Global") || c.getName().equalsIgnoreCase("OOC")) {
                f = f.replace("%na", name);
            } else {
                f = f.replace("%na", ChatColor.GREEN + ausweis.getFirstName().replace(" ", "_") + "_" + ausweis.getLastName().replace(" ", "_") + ChatColor.RESET);
                if (u.getPlayer().hasPermission("ftssystem.chat.color")) {
                    f = f.replace("&", "§");
                }
            }
        } else
            f = f.replace("%na", name);

        f = f.replace("%msg", (u.getPlayer().hasPermission("ftssystem.chat.color") ? ChatColor.translateAlternateColorCodes('&', msg) : msg));
        f = f.replace("((", "§7((");
        f = f.replace("))", "§7))§r");

        return f;
    }


}
