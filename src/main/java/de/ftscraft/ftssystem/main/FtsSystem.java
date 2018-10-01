package de.ftscraft.ftssystem.main;

import de.ftscraft.ftssystem.channel.Channel;
import de.ftscraft.ftssystem.channel.ChatManager;
import de.ftscraft.ftssystem.commands.CMDchannel;
import de.ftscraft.ftssystem.commands.CMDftssystem;
import de.ftscraft.ftssystem.commands.CMDtogglesidebar;
import de.ftscraft.ftssystem.commands.CMDumfrage;
import de.ftscraft.ftssystem.configs.ConfigManager;
import de.ftscraft.ftssystem.listeners.ChatListener;
import de.ftscraft.ftssystem.listeners.CommandListener;
import de.ftscraft.ftssystem.listeners.JoinListener;
import de.ftscraft.ftssystem.listeners.QuitListener;
import de.ftscraft.ftssystem.poll.Umfrage;
import de.ftscraft.ftssystem.punishment.PunishmentManager;
import de.ftscraft.ftssystem.utils.FTSScoreboard;
import de.ftscraft.ftssystem.utils.Runner;
import de.ftscraft.survivalminus.main.Survival;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class FtsSystem extends JavaPlugin {

    private HashMap<String, User> user;
    private Umfrage umfrage = null;

    public boolean factionHooked = false;

    private Economy econ;
    private Permission perm;
    private Chat chat;

    private ChatManager chatManager;
    private ConfigManager configManager;

    private Survival survival;

    private FTSScoreboard ftsScoreboard;

    private PunishmentManager punishmentManager;

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
    }

    private void init() {
        user = new HashMap<>();
        punishmentManager = new PunishmentManager(this);
        chatManager = new ChatManager(this);
        configManager = new ConfigManager(this);
        ftsScoreboard = new FTSScoreboard(this);
        new CMDftssystem(this);
        new CMDchannel(this);
        new CMDumfrage(this);
        new CMDtogglesidebar(this);
        new CommandListener(this);
        new ChatListener(this);
        new JoinListener(this);
        new QuitListener(this);
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
}
