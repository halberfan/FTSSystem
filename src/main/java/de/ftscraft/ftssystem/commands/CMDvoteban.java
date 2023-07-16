package de.ftscraft.ftssystem.commands;

import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import de.ftscraft.ftssystem.configs.Messages;
import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class CMDvoteban implements CommandExecutor {

    final private int maxPlaytime = 20 * 60 * 60 * 4; //4 Stunden
    private final HashMap<OfflinePlayer, VoteBan> voteBans;
    private final HashMap<OfflinePlayer, VoteBan> pendingConfirmations;

    private final FtsSystem plugin;

    //Todo: Mehrfache Abstimmung + 2x hintereinander /voteban eingeben

    public CMDvoteban(FtsSystem plugin) {
        this.plugin = plugin;
        plugin.getCommand("voteban").setExecutor(this);
        voteBans = new HashMap<>();
        pendingConfirmations = new HashMap<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(cs instanceof Player player)) {
            cs.sendMessage("cmd ist nur für player");
            return true;
        }

        if (!cs.hasPermission("group.burger")) {
            cs.sendMessage(Messages.PREFIX + "Du musst mindestens Bürger sein um einen Voteban zu starten.");
            return true;
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("confirm")) {
                VoteBan voteBan = pendingConfirmations.get(player);
                if (voteBan != null) {
                    voteBan.start(player);
                    if (voteBan.neededVotes != 1)
                        voteBan.addVote(player);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("abort")) {
                OfflinePlayer target = pendingConfirmations.get(player).target;
                pendingConfirmations.remove(player);
                voteBans.remove(target);
                player.sendMessage(Messages.PREFIX + "§cDu hast den Voteban abgebrochen.");
                return true;
            } else if (pendingConfirmations.containsKey(player)) {
                player.sendMessage(Messages.PREFIX + "§cBitte schließe erst deinen anderen Voteban ab mit /voteban confirm bzw /voteban abort");
                return true;
            }

            String sTarget = args[0];

            OfflinePlayer target = Bukkit.getOfflinePlayer(sTarget);

            if (voteBans.containsKey(target)) {

                VoteBan voteBan = voteBans.get(target);

                if (!voteBan.started) {
                    voteBan.start(player);
                    voteBan.initiator.getPlayer().sendMessage(Messages.PREFIX + "§cJemand anderes war auch der Meinung dass der Spieler gebannt werden sollte. Daher wurde der Vote auch ohne deine letzliche Bestätigung gestartet.");
                    player.sendMessage(Messages.PREFIX + "§eWeil jemand anderes schon einen Voteban eingerichtet hat, wurde dieser jetzt gestartet. ");
                }

                if (voteBan.voted.contains(player)) {
                    player.sendMessage(Messages.PREFIX + "Du hast bereits abgestimmt. ");
                    return true;
                }
                voteBan.addVote(player);
                player.sendMessage(Messages.PREFIX + "§cDu hast erfolgreich für den Voteban abgestimmt.");

            } else {

                if (pendingConfirmations.containsKey(player)) {
                    cs.sendMessage(Messages.PREFIX + "Bitte schließe erst deinen anderen Voteban ab mit /voteban confirm bzw /voteban abort");
                }

                if (!target.isOnline()) {
                    cs.sendMessage(Messages.PREFIX + "Der Spieler ist nicht online.");
                    return true;
                }

                if (target.getStatistic(Statistic.PLAY_ONE_MINUTE) > maxPlaytime) {
                    cs.sendMessage(Messages.PREFIX + "Der Spieler spielt zu lange um ihn Votebannen zu können.");
                    return true;
                }

                int count = 0;
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("group.burger"))
                        count++;
                }

                if (count == 1) {

                    cs.sendMessage(Messages.PREFIX + "§cDu bist der einzige der derzeit Abstimmberechtigt ist. Du musst also alleine entscheiden ob die Person gebannt werden soll. " +
                            "Wenn du das ausnutzen solltest, wirst du sehr wahrscheinlich eine starke Strafe erhalten. Um den Bann durchzuführen gebe " +
                            "/voteban confirm ein. Sonst, um abzubrechen, /voteban abort");

                } else {

                    count = count / 4;
                    if (count == 1 || count == 0) {
                        count = 2;
                    }

                    cs.sendMessage(Messages.PREFIX + "Um den Voteban durchzuführen, gebe /voteban confirm ein. Um abzubrechen /voteban abort. Bitte nehme zur Kenntnis dass" +
                            " wenn du diese Funktion ausnutzen solltest, mit einer schweren Strafe rechnen kannst.");

                }

                VoteBan voteBan = new VoteBan(target, player, count);

            }

        }

        return false;
    }

    class VoteBan {

        private OfflinePlayer target;
        private OfflinePlayer initiator;
        private ArrayList<OfflinePlayer> voted;
        private int neededVotes;
        private boolean started;

        public VoteBan(OfflinePlayer target, OfflinePlayer initiator, int neededVotes) {
            this.target = target;
            this.initiator = initiator;
            this.voted = new ArrayList<>();
            this.neededVotes = neededVotes;
            this.started = false;
            voteBans.put(target, this);
            pendingConfirmations.put(initiator, this);
        }

        public ArrayList<OfflinePlayer> getVoted() {
            return voted;
        }

        public OfflinePlayer getInitiator() {
            return initiator;
        }

        public OfflinePlayer getTarget() {
            return target;
        }

        public void addVote(OfflinePlayer voter) {
            voted.add(voter);
            if (voted.size() >= neededVotes && neededVotes != 1) {
                finish();
            } else {
                voter.getPlayer().sendMessage(Messages.PREFIX + "§cEs fehlen noch " + (neededVotes - voted.size()) + " Stimmen.");
            }
        }

        public void start(Player starter) {
            started = true;
            pendingConfirmations.remove(initiator);
            if (neededVotes == 1) {
                finish();
                return;
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("group.burger")) {
                    if (onlinePlayer != starter)
                        onlinePlayer.sendMessage(Messages.PREFIX + "Es wurde ein Votebann gegen den Spieler §c" + target.getName() + " §7gestartet. \n" +
                                "Wenn du der Meinung bist, dass diese Person (vorerst) gebannt werden sollte, stimme für den Bann mit §c/voteban " + target.getName() + " §7" +
                                "ab. Bitte sei dir im Klaren dass das dokumentiert wird und bei Missbrauch eine Strafe zu erwarten ist. " +
                                "Es werden insgesamt §c" + (neededVotes - 1) + "§7 stimmen benötigt.");
                }
            }

            starter.chat("/ticket Ich habe einen Voteban für den Spieler " + target.getName() + " eingeleitet. Bitte überprüfen (Automatisches Ticket)");

        }

        public void finish() {

            StringBuilder sb = new StringBuilder("Für den Bann haben abgestimmt: ");
            getVoted().forEach(offlinePlayer -> {
                sb.append(offlinePlayer.getName()).append(" ");
            });
            sb.append("Initiator war: ").append(initiator.getName());

            String moreInfo = sb.toString();

            plugin.getPunishmentManager().addTempBan("§cDie Mehrheit des Servers war der Meinung, dass du gebannt werden solltest. Wenn du denkst, dass das eine Fehlentscheidung war, melde dich bitte im Discord o.ä.", "System", target.getName(), moreInfo, "12h");

            voteBans.remove(target);

        }

    }

}
