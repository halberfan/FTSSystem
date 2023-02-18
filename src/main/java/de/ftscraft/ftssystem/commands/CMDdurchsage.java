package de.ftscraft.ftssystem.commands;

import de.ftscraft.ftssystem.configs.ConfigManager;
import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CMDdurchsage implements CommandExecutor {

    private FtsSystem plugin;

    public CMDdurchsage(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("durchsage").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!cs.hasPermission("ftssystem.durchsage")) {
            cs.sendMessage(Messages.NO_PERM);
            return true;
        }

        ConfigManager cm = plugin.getConfigManager();

        if (args.length == 0) {
            cs.sendMessage(Messages.PREFIX+"/durchsage new [Info/Werbung] [Nachricht]");
            cs.sendMessage(Messages.PREFIX+"/durchsage remove");
            cs.sendMessage(Messages.PREFIX+"/durchsage remove [Index]");
            return true;
        }

        if (args[0].equalsIgnoreCase("new")) {

            if (args.length < 5) {
                cs.sendMessage(Messages.PREFIX+"Bitte schreibe eine Nachricht die mindestens 3 Wörter lang ist");
                return true;
            }
            String prefix = null;
            if (args[1].equalsIgnoreCase("werbung")) {
                prefix = "§7[§cWerbung§7] ";
            } else if (args[1].equalsIgnoreCase("info")) {
                prefix = "§7[§aInfos§7] ";
            }

            if (prefix == null) {
                cs.sendMessage(Messages.PREFIX+"Bitte definiere welche Art von Durchsage es sein soll. §c/durchsage new [Werbung/Info] [Nachricht]");
                return true;
            }

            StringBuilder sb = new StringBuilder(prefix);

            int a = 0;
            if (args[args.length - 1].equalsIgnoreCase("Ja")) {
                a = 1;
            }

            for (int i = 2; i < args.length - a; i++) {
                sb.append(args[i].replace('&', '§')).append(" ");
            }

            if (a == 1) {
                cm.getAutoMessages().add(sb.toString());
                cs.sendMessage(Messages.PREFIX + "Die Nachricht wurde zum Pool hinzugefügt.");
                return true;
            } else {
                cs.sendMessage(Messages.PREFIX + "So würde die Nachricht aussehen: \n" + sb + "\n Wenn das so richtig ist, sende den gleichen Befehl nochmal. Schreibe nur am Ende ein \"Ja\" dazu");
                return true;
            }

        } else if (args[0].equalsIgnoreCase("remove")) {

            if (args.length == 2) {

                int index = -1;
                try {
                    index = Integer.parseInt(args[1]);
                } catch (NumberFormatException ignored) { /* Ignore */ }
                if (index >= 0 && index < cm.getAutoMessages().size()) {

                    cm.getAutoMessages().remove(index);
                    cs.sendMessage(Messages.PREFIX+"Der Eintrag wurde entfernt");

                } else
                    cs.sendMessage(Messages.PREFIX + "Bitte gebe einen validen Index an.");
            } else {

                for (int i = 0; i < cm.getAutoMessages().size(); i++) {
                    cs.sendMessage("§3" + i + "§7: " + cm.getAutoMessages().get(i));
                }

                cs.sendMessage(Messages.PREFIX + "Sende §c/durchsage remove [Index] §7um eine Durchsage zu entfernen");

            }

        }

        return false;
    }

}
