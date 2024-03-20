package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class CMDrepair implements CommandExecutor {

    private final FtsSystem plugin;
    private final static int PRICE = 100;
    private final String PERMISSION;
    private final String USAGE;

    public CMDrepair(FtsSystem plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("repair");
        command.setExecutor(this);
        PERMISSION = command.getPermission();
        USAGE = command.getUsage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!cs.hasPermission(PERMISSION)) {
            cs.sendMessage(Messages.NO_PERM);
            return true;
        }

        if (args.length != 1) {
            cs.sendMessage(String.format(Messages.WRONG_USAGE, USAGE));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            cs.sendMessage(Messages.PLAYER_NOT_FOUND);
            return true;
        }

        if (!plugin.getEcon().has(target, PRICE)) {
            cs.sendMessage(String.format(Messages.NOT_ENOUGH_MONEY, PRICE));
            return true;
        }

        ItemStack item = target.getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta instanceof Damageable damageable) {
            damageable.setDamage(0);
            item.setItemMeta(damageable);
            plugin.getEcon().withdrawPlayer(target, PRICE);
            cs.sendMessage(Utils.msg(Messages.MINI_PREFIX + "Dein Item wurde repariert und dir wurden <red>" + PRICE + "</red> Taler abgezogen"));
        }

        return true;
    }
}
