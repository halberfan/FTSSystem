package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CMDxpstore implements CommandExecutor {

    private final FtsSystem plugin;

    public CMDxpstore(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("xpstore").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {


            if(args.length == 1) {

                String playerName = args[0];
                Player pl = Bukkit.getServer().getPlayer(playerName);

                if(pl != null && pl.isOnline()) {

                    if(pl.getTotalExperience() >= 20 && pl.getInventory().contains(Material.GLASS_BOTTLE) && pl.getInventory().contains(Material.REDSTONE) && pl.getInventory().contains(Material.LAPIS_LAZULI)) {

                        pl.setExperienceLevelAndProgress(pl.calculateTotalExperiencePoints()-20);

                        int bottleDone = 0;
                        int redstoneDone = 0;
                        int lapisDone = 0;

                        for(ItemStack item : pl.getInventory().getContents()) {
                            if(item == null) {
                                continue;
                            }

                            if(item.getType() == Material.GLASS_BOTTLE && bottleDone != 2) {
                                item.setAmount(item.getAmount() - 2);
                                bottleDone++;
                            } else if(item.getType() == Material.REDSTONE && redstoneDone != 8) {
                                item.setAmount(item.getAmount() - 8);
                                redstoneDone++;
                            } else if(item.getType() == Material.LAPIS_LAZULI && lapisDone != 8) {
                                item.setAmount(item.getAmount() - 8);
                                lapisDone++;
                            } else {
                                sender.sendMessage("Der Spieler hat nicht genug Rohstoffe im Inventar.");

                            }
                        }



                        ItemStack isExpBottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 2);
                        pl.getInventory().addItem(isExpBottle);

                    }
                } else {
                    sender.sendMessage("Der Spieler '"+args[0]+"' konnte nicht gefunden werden!");
                }

                return false;

            } else {
                sender.sendMessage("Nutze den Befehl bitte wie folgt: /xpstore [Spieler]");
            }

        } else {
            sender.sendMessage("§4Dieser Command kann nur über die Serverkonsole ausgeführt werden!");
        }
        return false;
    }
}
