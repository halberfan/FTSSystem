/*
 * Copyright (c) 2021.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.listeners;

import com.destroystokyo.paper.util.SneakyThrow;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.main.User;
import net.ess3.api.events.AfkStatusChangeEvent;
import net.ess3.api.events.PrivateMessageSentEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.BroadcastMessageEvent;

public class EssentialsListener implements Listener {

    private FtsSystem plugin;

    public EssentialsListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMessageRecive(PrivateMessageSentEvent event) {

        Player t = Bukkit.getPlayer(event.getRecipient().getName());

        if (t != null) {

            User u = plugin.getUser(t);

            if(!u.isMsgSoundEnabled())
                return;

            t.playSound(t.getLocation(), Sound.ENTITY_CHICKEN_EGG, SoundCategory.VOICE, 1, 1);

        }

    }

     @EventHandler
     public void onBroadcast(BroadcastMessageEvent event) {

         for (User value : plugin.getUser().values()) {

             //Wenn der Spieler den Nicht-Stören-Modus an hat oder ihn nur für RP an hat aber auch im RP ist
             if((value.getDisturbStatus() == User.DoNotDisturbStatus.ON) ||
                     (value.getDisturbStatus() == User.DoNotDisturbStatus.RP && plugin.getScoreboardManager().isInRoleplayMode(value.getPlayer()))) {
                 if(event.getRecipients().contains(value.getPlayer())) {
                     event.getRecipients().remove(value.getPlayer());
                 }
             }
         }

     }

    @EventHandler
    public void onAfkChange(AfkStatusChangeEvent event) {

        Player t = Bukkit.getPlayer(event.getAffected().getName());

        if(t!=null) {
            plugin.getScoreboardManager().changeAfkStatus(t, event.getValue());
        }

    }

}
