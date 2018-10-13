package de.ftscraft.ftssystem.main;

import de.ftscraft.ftssystem.channel.Channel;
import de.ftscraft.ftssystem.channel.ChatManager;
import de.ftscraft.ftssystem.commands.*;
import de.ftscraft.ftssystem.configs.ConfigManager;
import de.ftscraft.ftssystem.listeners.*;
import de.ftscraft.ftssystem.poll.Umfrage;
import de.ftscraft.ftssystem.punishment.PunishmentManager;
import de.ftscraft.ftssystem.reisepunkte.ReisepunktManager;
import de.ftscraft.ftssystem.utils.FTSScoreboard;
import de.ftscraft.ftssystem.utils.Runner;
import de.ftscraft.survivalminus.main.Survival;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;

import java.util.HashMap;

public class FtsSystem extends JavaPlugin {

    private HashMap<String, User> user;
    private Umfrage umfrage = null;

    public boolean factionHooked = false;
    public static final String PREFIX = "§7[§cFTS-System§7] ";

    private Economy econ;
    private Permission perm;
    private Chat chat;

    private ChatManager chatManager;
    private ConfigManager configManager;

    private Survival survival;

    private FTSScoreboard ftsScoreboard;

    private PunishmentManager punishmentManager;
    private ReisepunktManager reisepunktManager;

    @Override
    public void onEnable() {
        super.onEnable();
        hook();
        init();

    }


    private void hook() {
        factionHooked = getServer().getPluginManager().getPlugin("Factions") != null;
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        RegisteredServiceProvider<Economy> rsp2 = getServer().getServicesManager().getRegistration(Economy.class);
        econ = rsp2.getProvider();
        RegisteredServiceProvider<Permission> rsp3 = getServer().getServicesManager().getRegistration(Permission.class);
        perm = rsp3.getProvider();
        survival = (Survival) getServer().getPluginManager().getPlugin("SurvivalMinus");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        save();
    }

    private void save() {
        configManager.setConfig(ConfigManager.ConfigValue.LATEST_PUNISH_ID, getPunishmentManager().getLatestID());

        configManager.save();
    }

    private void init() {
        user = new HashMap<>();
        configManager = new ConfigManager(this);
        chatManager = new ChatManager(this);
        punishmentManager = new PunishmentManager(this);
        reisepunktManager = new ReisepunktManager(this);
        ftsScoreboard = new FTSScoreboard(this);
        new CMDftssystem(this);
        new CMDchannel(this);
        new CMDumfrage(this);
        new CMDtogglesidebar(this);
        new CMDpu(this);
        new CommandListener(this);
        new ChatListener(this);
        new JoinListener(this);
        new QuitListener(this);
        new LoginListener(this);
        new InvClickListener(this);
        new Runner(this);
    }

    public User getUser(Player player) {
        return user.get(player.getName());
    }

    public HashMap<String, User> getUser() {
        return user;
    }

    public Economy getEcon() {
        return econ;
    }

    public Permission getPerm() {
        return perm;
    }

    public Chat getChat() {
        return chat;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Umfrage getUmfrage() {
        return umfrage;
    }

    public void setUmfrage(Umfrage umfrage) {
        this.umfrage = umfrage;
    }

    public Survival getSurvival() {
        return survival;
    }

    public FTSScoreboard getFtsScoreboard() {
        return ftsScoreboard;
    }

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    public ReisepunktManager getReisepunktManager() {
        return reisepunktManager;
    }
}
