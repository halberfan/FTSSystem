/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftsengine.utils.Ausweis;
import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import de.ftscraft.ftssystem.punishment.PunishmentBuilder;
import de.ftscraft.ftssystem.punishment.PunishmentManager;
import de.ftscraft.ftssystem.punishment.PunishmentType;
import de.ftscraft.ftssystem.punishment.Temporary;
import de.ftscraft.ftssystem.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatListener implements Listener {

    private FtsSystem plugin;

    public ChatListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event) {

        if (plugin.getPunishmentManager().getBuilders().get(event.getPlayer()) != null) {
            event.setCancelled(true);
            PunishmentBuilder prog = plugin.getPunishmentManager().getBuilders().get(event.getPlayer());
            switch (prog.getChatProgress()) {
                case REASON:
                    prog.setReason(event.getMessage());
                    event.getPlayer().sendMessage(Messages.PREFIX + "Okay. Bitte schreibe jetzt weitere Infos (zB Beweise, wer es Bemerkt hat). Schreibe wenn es nicht in den Chat passt ein + am Ende. Dann kannst du mit der nächsten Nachricht weiterschreiben");
                    prog.setChatProgress(PunishmentManager.ChatProgress.MOREINFO);
                    break;
                case MOREINFO:

                    if (event.getMessage().endsWith("+")) {
                        if (prog.getMoreInfo() == null)
                            prog.setMoreInfo("");
                        prog.setMoreInfo(prog.getMoreInfo() + " " + event.getMessage().substring(0, event.getMessage().length() - 1));
                        return;
                    } else {
                        if (prog.getMoreInfo() == null)
                            //Wenn es noch keine Nachricht davor gab, setzt er es zu der Nachricht die gerade kam
                            prog.setMoreInfo(event.getMessage());
                        else
                            //Sonst das was schon da ist mit nem Leerzeichen und der Nachricht die gerade kam verbinden
                            prog.setMoreInfo(prog.getMoreInfo() + " " + event.getMessage());

                        if (PunishmentType.isTemporary(prog.getType())) {
                            event.getPlayer().sendMessage(Messages.PREFIX + "Okay. Bitte schreibe jetzt wie lange es andauern soll. zB(3d; 3w)");
                            prog.setChatProgress(PunishmentManager.ChatProgress.TIME);
                            return;
                        }
                    }

                    prog.setChatProgress(PunishmentManager.ChatProgress.PROOF);
                    event.getPlayer().sendMessage(Messages.PREFIX + "Bitte überprüfe nochmal deine Daten. Du kannst diese später NICHT ändern. Schreibe dann Ja oder Nein");
                    event.getPlayer().sendMessage(Messages.PREFIX + "Target: §e" + prog.getPlayer());
                    event.getPlayer().sendMessage(Messages.PREFIX + "Grund: §e" + prog.getReason());
                    event.getPlayer().sendMessage(Messages.PREFIX + "Weitere Infos: §e" + prog.getMoreInfo());
                    if (prog.getUntil() != null)
                        event.getPlayer().sendMessage(Messages.PREFIX + "Zeitraum: §e" + Utils.convertToTime(prog.getUntilInMillis()));


                    break;
                case TIME:

                    prog.setUntil(event.getMessage());

                    prog.setChatProgress(PunishmentManager.ChatProgress.PROOF);
                    event.getPlayer().sendMessage(Messages.PREFIX + "Bitte überprüfe nochmal deine Daten. Du kannst diese später NICHT ändern. Schreibe dann Ja oder Nein");
                    event.getPlayer().sendMessage(Messages.PREFIX + "Target: §e" + prog.getPlayer());
                    event.getPlayer().sendMessage(Messages.PREFIX + "Grund: §e" + prog.getReason());
                    event.getPlayer().sendMessage(Messages.PREFIX + "Weitere Infos: §e" + prog.getMoreInfo());
                    if (prog.getUntil() != null)
                        event.getPlayer().sendMessage(Messages.PREFIX + "Zeitraum: §e" + Utils.convertToTime(prog.getUntilInMillis()));

                    break;
                case PROOF:
                    if (event.getMessage().equalsIgnoreCase("Ja")) {

                        prog.setProofed(true);
                        plugin.getPunishmentManager().getBuilders().remove(event.getPlayer());

                        event.getPlayer().sendMessage(Messages.PREFIX + "§cOkay. Deine Anfrage wird bearbeitet");
                        prog.build();
                        event.getPlayer().sendMessage(Messages.PREFIX + "§cFertig. §eDer Spieler hat seine Strafe erhalten.");

                    } else if (event.getMessage().equalsIgnoreCase("Nein")) {
                        prog.abort();
                        event.getPlayer().sendMessage(Messages.PREFIX + "Okay. Das Setup wurde abgebrochen. Wenn du es erneut versuchen willst, gebe wieder /pu SPIELER ein");
                    }
                    break;
            }
        }

        if (plugin.getPunishmentManager().isMuted(event.getPlayer()) != null) {
            Temporary mute = (Temporary) plugin.getPunishmentManager().isMuted(event.getPlayer());
            event.getPlayer().sendMessage(Messages.PREFIX + "Du bist noch " + mute.untilAsString() + " lang gemuted aufgrund von: §e" + mute.getReason());
            event.setCancelled(true);
        }

        if (event.isCancelled()) {
            return;
        }
        User u = plugin.getUser(event.getPlayer());
        if (u == null) {
            event.getPlayer().sendMessage("§cIrgendwas ist schief gelaufen. Probier mal zu reconnecten!");
            return;
        }
        event.setCancelled(true);
        if (event.getMessage().startsWith("*")) {
            if (event.getMessage().startsWith(String.valueOf('*'))) {

                String[] msgs = event.getMessage().split(" ");

                for (int i = 0; i < msgs.length; i++) {
                    for (Player a : Bukkit.getOnlinePlayers()) {
                        if (plugin.getEngine().hasAusweis(a)) {
                            if (a.getName().equalsIgnoreCase(msgs[i])) {
                                msgs[i] = plugin.getEngine().getAusweis(a).getFirstName() + " " + plugin.getEngine().getAusweis(a).getLastName();
                            }
                        }
                    }
                }

                String msg;

                Ausweis a = plugin.getEngine().getAusweis(event.getPlayer());

                if (a == null) {
                    event.getPlayer().sendMessage("§cBitte erstell dir erst einen Ausweis");
                    return;
                }

                msgs[0] = msgs[0].substring(1);

                if (!event.getMessage().startsWith("**")) {

                    msg = "§e" + a.getFirstName() + " " + a.getLastName() + " (" + event.getPlayer().getName() + ") ";

                } else {
                    msgs[0] = msgs[0].substring(1);
                    msg = "§e";
                }

                //msgs[0].replace("*", "");

                for (String m : msgs) {
                    msg += m + " ";
                }

                msg = msg.replace("((", "§7((");
                msg = msg.replace("))", "§7))§e");

                msg = msg.replace("\"", "§7\"");
                msg = msg.replace("\"", "§7\"§e");

                event.setCancelled(true);

                for (User b : plugin.getUser().values()) {
                    if (b.getPlayer().getWorld().getName().equalsIgnoreCase(u.getPlayer().getWorld().getName())) {
                        if (b.getPlayer().getLocation().distance(u.getPlayer().getLocation()) <= u.getActiveChannel().getRange()) {
                            b.getPlayer().sendMessage(msg);
                        }
                    }
                }

                Logger.getLogger("Minecraft").log(Level.INFO, "[Chat] " + event.getPlayer().getName() + " [RP] " + event.getMessage());
            }
            return;
        }
        if (event.getMessage().startsWith("!")) {
            plugin.getChatManager().chat(u, event.getMessage().replaceFirst("!", ""), plugin.getChatManager().getChannel("Global"));
            return;
        }

        plugin.getChatManager().chat(u, event.getMessage());

    }


}
