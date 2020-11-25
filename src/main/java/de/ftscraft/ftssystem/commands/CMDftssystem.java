package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.FTSCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;


public class CMDftssystem implements FTSCommand {

    private FtsSystem plugin;

    public CMDftssystem(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("ftssystem").setExecutor(this);
    }

    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        Player p = (Player) cs;

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("fake")) {
                if (p.hasPermission("ftssystem.fake")) {
                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("join")) {
                            for (Player a : Bukkit.getOnlinePlayers()) {
                                a.sendMessage("§6" + p.getName() + " §rhat Parsifal betreten!");
                            }
                        } else if (args[1].equalsIgnoreCase("leave")) {
                            for (Player a : Bukkit.getOnlinePlayers()) {
                                a.sendMessage("§6" + p.getName() + " §rhat Parsifal verlassen!");
                            }
                        }
                    } else p.sendMessage(Messages.HELP_FTSSYSTEM);
                }
            } else if (args[0].equalsIgnoreCase("repair")) {

                if(args.length == 2) {

                    if(p.hasPermission("ftssystem.admin")) {

                        Player t = Bukkit.getPlayer(args[1]);

                        ItemStack item = t.getInventory().getItemInMainHand();

                        if(item.getType() == Material.AIR || !item.hasItemMeta()) {
                            t.sendMessage("Probier es noch mal mit nem richtigen Item!");
                            givePearl(t);
                            return true;
                        }

                        ItemMeta meta = item.getItemMeta();

                        if (meta instanceof Damageable) {

                            int maxDurability = item.getType().getMaxDurability();

                            Damageable dmg = (Damageable) meta;

                            dmg.setDamage(maxDurability);

                            item.setItemMeta(meta);

                            t.sendMessage("Das sollte funktioniert haben!");
                            return true;

                        } else
                            t.sendMessage("§cDas Item hat keine Haltbarkeit!");

                        givePearl(t);

                    }

                }

            } else p.sendMessage(Messages.HELP_FTSSYSTEM);
        } else p.sendMessage(Messages.HELP_FTSSYSTEM);

        return false;
    }

    private void givePearl(Player t) {
        t.getInventory().addItem(new ItemStack(Material.GHAST_TEAR, 1));
    }

}
