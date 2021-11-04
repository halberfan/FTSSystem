/*
 * Copyright (c) 2018.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.destroystokyo.paper.profile.PlayerProfile;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.punishment.Punishment;
import de.ftscraft.ftssystem.punishment.PunishmentType;
import de.ftscraft.ftssystem.punishment.Temporary;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class LoginListener implements Listener {

    private FtsSystem plugin;

    public LoginListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPing(PaperServerListPingEvent event) {
        if(plugin.isInWartung()) {
            event.setMotd("\u00a7bDer Server ist leider derzeit in\u00a74 Wartung\u00a7r!\n\u00a7bWeitere Infos findest du im Forum oder im Discord");
            event.setMaxPlayers(0);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {

        Player p = event.getPlayer();

        if(plugin.isInWartung()) {
            if(!p.hasPermission("ftssystem.wartung.bypass")) {
                event.setResult(PlayerLoginEvent.Result.KICK_WHITELIST);
                event.setKickMessage("§4Wir haben derzeit Wartungsarbeiten.\n" +
                        "§bProbier es später nochmal. Im Forum oder im Discord wirst du wahrscheinlich weitere Infos erhalten!\n" +
                        "§bBis später :)");
            }
        }

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
