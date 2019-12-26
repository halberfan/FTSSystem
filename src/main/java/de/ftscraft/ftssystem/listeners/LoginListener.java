/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.punishment.Punishment;
import de.ftscraft.ftssystem.punishment.PunishmentType;
import de.ftscraft.ftssystem.punishment.Temporary;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    private FtsSystem plugin;

    public LoginListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {

        Player p = event.getPlayer();

        if(event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }

        Punishment pu = plugin.getPunishmentManager().isBanned(p);
        if (pu == null) {
            event.setResult(PlayerLoginEvent.Result.ALLOWED);
            return;
        }

        if (pu.getType() == PunishmentType.BAN) {
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            event.setKickMessage("§4Du wurdest gebannt! \n" +
                    "§eGebannt von: §b" + pu.getAuthor() + "\n" +
                    "§eBis: §bPERMANENT \n" +
                    "§eGrund: §b" + pu.getReason() + "\n" +
                    " \n" +
                    "§6Du kannst ein Entbannungsbeitrag im Forum schreiben");
        } else {
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            event.setKickMessage("§4Du wurdest gebannt! \n" +
                    "§eGebannt von: §b" + pu.getAuthor() + "\n" +
                    "§eNoch: §b"+ ((Temporary)pu).untilAsString() + "\n" +
                    "§eGrund: §b" + pu.getReason() + "\n" +
                    " \n" +
                    "§6Du kannst ein Entbannungsbeitrag im Forum schreiben");
        }

    }
}
