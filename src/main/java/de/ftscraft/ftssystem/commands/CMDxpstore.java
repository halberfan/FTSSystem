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

                    if(pl.getTotalExperience() >= 10 && pl.getInventory().contains(Material.GLASS_BOTTLE) && pl.getInventory().contains(Material.REDSTONE) && pl.getInventory().contains(Material.LAPIS_LAZULI)) {

                        pl.setExperienceLevelAndProgress(pl.calculateTotalExperiencePoints()-10);

                        boolean bottleDone = false;
                        boolean redstoneDone = false;
                        boolean lapisDone = false;

                        for(ItemStack item : pl.getInventory().getContents()) {
                            if(item == null) {
                                continue;
                            }

                            if(item.getType() == Material.GLASS_BOTTLE && !bottleDone) {
                                item.setAmount(item.getAmount() - 1);
                                bottleDone = true;
                            } else if(item.getType() == Material.REDSTONE && !redstoneDone) {
                                item.setAmount(item.getAmount() - 1);
                                redstoneDone = true;
                            } else if(item.getType() == Material.LAPIS_LAZULI && !lapisDone) {
                                item.setAmount(item.getAmount() - 1);
                                lapisDone = true;
                            } else {
                                sender.sendMessage("Der Spieler hat nicht genug Rohstoffe im Inventar.");

                            }
                        }



                        ItemStack isExpBottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
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
