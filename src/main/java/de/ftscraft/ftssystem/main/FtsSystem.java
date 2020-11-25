package de.ftscraft.ftssystem.main;

import com.hmmcrunchy.disease.Disease;
import de.ftscraft.ftsengine.main.Engine;
import de.ftscraft.ftssystem.channel.ChatManager;
import de.ftscraft.ftssystem.commands.*;
import de.ftscraft.ftssystem.configs.ConfigManager;
import de.ftscraft.ftssystem.listeners.*;
import de.ftscraft.ftssystem.poll.Umfrage;
import de.ftscraft.ftssystem.punishment.PunishmentManager;
import de.ftscraft.ftssystem.scoreboard.FTSScoreboardManager;
import de.ftscraft.ftssystem.utils.FTSScoreboard;
import de.ftscraft.ftssystem.utils.FileManager;
import de.ftscraft.ftssystem.utils.Runner;
import de.ftscraft.survivalminus.main.Survival;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class FtsSystem extends JavaPlugin {

    private HashMap<String, User> user;
    private Umfrage umfrage = null;

    public boolean factionHooked = false;
    public static final String PREFIX = "§7[§cFTS-System§7] ";

    private Economy econ;
    private Permission perms;
    private Chat chat;

    private ChatManager chatManager;
    private ConfigManager configManager;
    private FileManager fileManager;

    private FTSScoreboardManager scoreboardManager;

    private Survival survival;
    private Engine engine;

    private PunishmentManager punishmentManager;
    private Disease disease = null;

    private LuckPerms luckPermsApi;


    @Override
    public void onEnable() {
        super.onEnable();
        hook();
        init();

        //NPC Reload

        Bukkit.getScheduler().runTaskLater(this, () -> {
            getServer().dispatchCommand(getServer().getConsoleSender(), "citizens reload");
        }, 20 * 20);

    }


    private void hook() {
        factionHooked = getServer().getPluginManager().getPlugin("Factions") != null;
        setupEconomy();
        setupChat();
        setupPermissions();
        survival = (Survival) getServer().getPluginManager().getPlugin("FTSSurvival");
        engine = (Engine) getServer().getPluginManager().getPlugin("FTSEngine");
        if (getServer().getPluginManager().isPluginEnabled("Disease"))
            disease = (Disease) getServer().getPluginManager().getPlugin("Disease");
        /*RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
        }

         */
    }

    @Override
    public void onDisable() {
        super.onDisable();
        save();
    }

    private void save() {
        configManager.setConfig(ConfigManager.ConfigVal.LATEST_PUNISH_ID, getPunishmentManager().getLatestID());

        configManager.save();
    }

    private void init() {
        user = new HashMap<>();
        configManager = new ConfigManager(this);
        fileManager = new FileManager(this);
        chatManager = new ChatManager(this);
        punishmentManager = new PunishmentManager(this);
        scoreboardManager = new FTSScoreboardManager(this);
        new CMDftssystem(this);
        new CMDchannel(this);
        new CMDumfrage(this);
        new CMDtogglesidebar(this);
        new CMDpu(this);
        new CMDtutorialbuch(this);
        new CMDakte(this);
        new CMDroleplay(this);
        new CMDpremium(this);
        new CMDpasswort(this);

        new DeathListener(this);
        new CommandListener(this);
        new ChatListener(this);
        new JoinListener(this);
        new QuitListener(this);
        new LoginListener(this);
        new InvClickListener(this);
        new SneakListener(this);
        new EntityDeathListener(this);
        new PlayerAttackListener(this);
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

    public Permission getPerms() {
        return perms;
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

    public FTSScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    public Disease getDisease() {
        return disease;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public LuckPerms getLuckPermsApi() {
        return luckPermsApi;
    }

    public Engine getEngine() {
        return engine;
    }
}
