package de.ftscraft.ftssystem.main;

import de.ftscraft.ftsengine.main.Engine;
import de.ftscraft.ftssystem.channel.chatmanager.ChatManager;
import de.ftscraft.ftssystem.channel.chatmanager.FTSChatManager;
import de.ftscraft.ftssystem.channel.chatmanager.ReichChatManager;
import de.ftscraft.ftssystem.commands.*;
import de.ftscraft.ftssystem.configs.ConfigManager;
import de.ftscraft.ftssystem.configs.ConfigVal;
import de.ftscraft.ftssystem.listeners.*;
import de.ftscraft.ftssystem.menus.fts.MenuItems;
import de.ftscraft.ftssystem.poll.Umfrage;
import de.ftscraft.ftssystem.punishment.PunishmentManager;
import de.ftscraft.ftssystem.scoreboard.FTSScoreboardManager;
import de.ftscraft.ftssystem.utils.FileManager;
import de.ftscraft.ftssystem.utils.ForumHook.ForumHook;
import de.ftscraft.ftssystem.utils.PremiumManager;
import de.ftscraft.ftssystem.utils.Runner;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

public class FtsSystem extends JavaPlugin {

    private HashMap<String, User> user;
    private Umfrage umfrage = null;

    public boolean factionHooked = false;
    public static final String PREFIX = "§7[§cFTS-System§7] ";

    private Economy econ;
    private Permission perms;
    private Chat chat;

    private ConfigManager configManager;
    private FileManager fileManager;

    private FTSScoreboardManager scoreboardManager;
    private MenuItems menuItems;

    private Engine engine;

    private PunishmentManager punishmentManager;

    private boolean wartung;

    private ChatManager chatManager;

    private boolean blockreich = false;

    private PremiumManager premiumManager;
    private ForumHook forumHook;

    private static Logger pluginLogger;

    @Override
    public void onEnable() {
        super.onEnable();
        hook();

        pluginLogger = getLogger();

        init();
    }


    private void hook() {
        factionHooked = getServer().getPluginManager().getPlugin("Factions") != null;
        setupEconomy();
        setupChat();
        setupPermissions();
        engine = (Engine) getServer().getPluginManager().getPlugin("FTSEngine");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        save();
    }

    private void save() {
        configManager.setConfig(ConfigVal.LATEST_PUNISH_ID, getPunishmentManager().getLatestID());
        configManager.setConfig(ConfigVal.WARTUNG, isInWartung());
        configManager.setConfig(ConfigVal.MESSAGES, configManager.getAutoMessages());

        configManager.save();
        fileManager.savePremium();
    }

    private void init() {
        user = new HashMap<>();
        configManager = new ConfigManager(this);
        fileManager = new FileManager(this);
        if (getServer().getPluginManager().isPluginEnabled("Factions")) {
            chatManager = new FTSChatManager(this);
        } else if (getServer().getPluginManager().isPluginEnabled("Towny")) {
            chatManager = new ReichChatManager(this);
            blockreich = true;
        }
        punishmentManager = new PunishmentManager(this);
        scoreboardManager = new FTSScoreboardManager(this);
        menuItems = new MenuItems();
        premiumManager = new PremiumManager(this);
        forumHook = new ForumHook(this);
        new CMDakte(this);
        new CMDbroadcast(this);
        new CMDcheckcv(this);
        new CMDdurchsage(this);
        new CMDfts(this);
        new CMDftssystem(this);
        new CMDpasswort(this);
        new CMDpremium(this);
        new CMDpu(this);
        new CMDradius(this);
        new CMDroleplay(this);
        new CMDsetvotehome(this);
        new CMDtutorialbuch(this);
        new CMDumfrage(this);
        new CMDvoteban(this);
        new CMDvotehome(this);
        new CMDwartung(this);

        new DeathListener(this);
        new CommandListener(this);
        new ChatListener(this);
        new JoinListener(this);
        new EssentialsListener(this);
        new QuitListener(this);
        new LoginListener(this);
        new InvClickListener(this);
        new SneakListener(this);
        new EntityDeathListener(this);
        new PlayerAttackListener(this);
        new FactionListener(this);
        new PlayerOpenSignListener(this);
        new Runner(this);

        Iterator<Recipe> recipes = Bukkit.recipeIterator();
        while (recipes.hasNext()) {
            Recipe recipe = recipes.next();

            if (recipe instanceof FurnaceRecipe furnaceRecipe) {

                if (furnaceRecipe.getInput().getType() == Material.RAW_GOLD) {
                    recipes.remove();
                }

            } else if (recipe instanceof BlastingRecipe blastingRecipe) {

                if (blastingRecipe.getInput().getType() == Material.RAW_GOLD) {
                    recipes.remove();
                }

            }

        }

        postInit();

    }

    private void postInit() {
        wartung = configManager.isWartung();

        fileManager.loadSecrets();
        fileManager.loadPremium();
        premiumManager.checkPremiumPlayers();
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


    public FTSScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    public PremiumManager getPremiumManager() {
        return premiumManager;
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

    public Engine getEngine() {
        return engine;
    }

    public MenuItems getMenuItems() {
        return menuItems;
    }

    public boolean isInWartung() {
        return wartung;
    }

    public void setWartung(boolean wartung) {
        this.wartung = wartung;
    }

    public boolean isBlockreich() {
        return blockreich;
    }

    public ForumHook getForumHook() {
        return forumHook;
    }

    public static Logger getPluginLogger() {
        return pluginLogger;
    }

}
