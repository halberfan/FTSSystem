package de.ftscraft.ftssystem.listeners;

import de.ftscraft.ftssystem.main.FtsSystem;
import io.papermc.paper.event.player.PlayerOpenSignEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerOpenSignListener implements Listener {

    private final FtsSystem plugin;
    public PlayerOpenSignListener(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSignEdit(PlayerOpenSignEvent event) {
        Sign sign = event.getSign();
        Side side = sign.getInteractableSideFor(event.getPlayer());
        SignSide signSide = sign.getSide(side);
        boolean empty = true;
        for (Component line : signSide.lines()) {
            if (!((TextComponent) line).content().isEmpty())
                empty = false;
        }
        event.setCancelled(!empty);
    }

}
