package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class CMDradius implements CommandExecutor {

    private final FtsSystem plugin;

    public CMDradius(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("radius").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player p = (Player) sender;
        int range = plugin.getUser(p).getActiveChannel().getRange();

        if (range == -1) {
            p.sendMessage(Messages.PREFIX + "Dein aktiver Channel hat eine unendliche Reichweite die sich nur schwer darstellen lässt.");
            return true;
        }

        new BukkitRunnable() {
            int c = 0;

            @Override
            public void run() {
                c++;
                drawCircle(p.getLocation(), range, range * 15, p);
                if (c == 5)
                    cancel();
            }

        }.runTaskTimer(plugin, 0, 7);

        return false;
    }

    private void drawCircle(Location loc, int radius, int amount, Player p) {

        double incr = Math.PI * 2 / amount;

        for (double i = 0; i < Math.PI * 2; i += incr) {
            double x = radius * Math.sin(i) + loc.x();
            double z = radius * Math.cos(i) + loc.z();
            FtsSystem.getPluginLogger().log(Level.INFO, x + " " + z);
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.WHITE, 1f);
            p.spawnParticle(Particle.REDSTONE, x, loc.getY(), z, 2, dustOptions);
        }


    }

}
