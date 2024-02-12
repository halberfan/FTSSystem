package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.FTSCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;


public class CMDftssystem implements FTSCommand {

    private final FtsSystem plugin;

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
                                a.sendMessage("§b" + p.getName() + " §rhat Parsifal betreten!");
                            }
                        } else if (args[1].equalsIgnoreCase("leave")) {
                            for (Player a : Bukkit.getOnlinePlayers()) {
                                a.sendMessage("§b" + p.getName() + " §rhat Parsifal verlassen!");
                            }
                        }
                    } else p.sendMessage(Messages.HELP_FTSSYSTEM);
                }
            } else if (args[0].equalsIgnoreCase("repair")) {

                if (args.length == 2) {

                    if (p.hasPermission("ftssystem.admin")) {

                        Player t = Bukkit.getPlayer(args[1]);

                        ItemStack item = t.getInventory().getItemInMainHand();

                        if (item.getType() == Material.AIR || !item.hasItemMeta()) {
                            t.sendMessage("Probier es noch mal mit nem richtigen Item!");
                            givePearl(t);
                            return true;
                        }

                        ItemMeta meta = item.getItemMeta();

                        if (meta instanceof Damageable dmg) {

                            dmg.setDamage(0);

                            item.setItemMeta(meta);

                            t.sendMessage("Das sollte funktioniert haben!");
                            return true;

                        } else
                            t.sendMessage("§cDas Item hat keine Haltbarkeit!");

                        givePearl(t);

                    }

                }

            } else if (args[0].equalsIgnoreCase("playtime")) {

                if (p.hasPermission("ftssystem.admin")) {

                    p.sendMessage("§cFolgende online Spieler haben bereits über 50h Spielzeit aber noch nicht den Bürgerrang: ");

                    boolean someone = false;

                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayer.hasPermission("group.burger")) {
                            continue;
                        }

                        double ticks = onlinePlayer.getStatistic(Statistic.PLAY_ONE_MINUTE);
                        double seconds = ticks / 20;
                        double minutes = seconds / 60;
                        double hours = minutes / 60;


                        if (hours > 50.0) {
                            someone = true;
                            p.sendMessage("§e- " + onlinePlayer.getName() + " §c(" + hours + "h)");
                        }
                    }

                    if (!someone)
                        p.sendMessage("§eNiemand");

                } else {
                    double ticks = p.getStatistic(Statistic.PLAY_ONE_MINUTE);
                    double seconds = ticks / 20;
                    double minutes = seconds / 60;
                    double hours = minutes / 60;

                    hours = Math.round(hours * 100d) / 100d;

                    p.sendMessage("§cDu hast " + hours + " Stunden Spielzeit");
                }


            }
        } else p.sendMessage(Messages.HELP_FTSSYSTEM);
        return false;
    }

    private void givePearl(Player t) {
        t.getInventory().addItem(new ItemStack(Material.GHAST_TEAR, 1));
    }

}
