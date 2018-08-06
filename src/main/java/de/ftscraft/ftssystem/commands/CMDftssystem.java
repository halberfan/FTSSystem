package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.utils.FTSCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDftssystem implements FTSCommand {

    private FtsSystem plugin;

    public CMDftssystem(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("ftssystem").setExecutor(this);
    }

    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(!(cs instanceof Player)) {
            cs.sendMessage(Messages.ONLY_PLAYER);
            return true;
        }

        Player p = (Player)cs;

        p.sendMessage("§cDieser CMD funktioniert! JUHU!!!!");

        return false;
    }

}
