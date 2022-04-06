package de.ftscraft.ftssystem.main;

import de.afgmedia.ftspest.main.FTSPest;
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
import de.ftscraft.ftssystem.utils.Runner;
import de.ftscraft.survivalminus.main.Survival;
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

    private Survival survival;
    private Engine engine;

    private PunishmentManager punishmentManager;
    private FTSPest pest = null;

    private boolean wartung;

    private ChatManager chatManager;

    private boolean blockreich = false;

    @Override
    public void onEnable() {
        super.onEnable();
        hook();
        init();

    }


    private void hook() {
        factionHooked = getServer().getPluginManager().getPlugin("Factions") != null;
        setupEconomy();
        setupChat();
        setupPermissions();
        survival = (Survival) getServer().getPluginManager().getPlugin("FTSSurvival");
        engine = (Engine) getServer().getPluginManager().getPlugin("FTSEngine");
        if (getServer().getPluginManager().isPluginEnabled("FTSPest"))
            pest = (FTSPest) getServer().getPluginManager().getPlugin("FTSPest");
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
        configManager.setConfig(ConfigVal.LATEST_PUNISH_ID, getPunishmentManager().getLatestID());
        configManager.setConfig(ConfigVal.WARTUNG, isInWartung());

        configManager.save();
    }

    private void init() {
        user = new HashMap<>();
        configManager = new ConfigManager(this);
        fileManager = new FileManager(this);
        if(getServer().getPluginManager().isPluginEnabled("Factions")) {
            chatManager = new FTSChatManager(this);
        } else if(getServer().getPluginManager().isPluginEnabled("Towny")) {
            chatManager = new ReichChatManager(this);
            blockreich = true;
        }
        punishmentManager = new PunishmentManager(this);
        scoreboardManager = new FTSScoreboardManager(this);
        menuItems = new MenuItems();
        new CMDftssystem(this);
        new CMDchannel(this);
        new CMDumfrage(this);
        new CMDtogglesidebar(this);
        new CMDpu(this);
        new CMDtutorialbuch(this);
        new CMDakte(this);
        new CMDroleplay(this);
        new CMDpremium(this);
        new CMDfts(this);
        new CMDpasswort(this);
        new CMDbroadcast(this);
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
        new Runner(this);

        Iterator<Recipe> recipes = Bukkit.recipeIterator();

        while (recipes.hasNext()) {
            Recipe recipe = recipes.next();

            if(recipe instanceof FurnaceRecipe) {

                FurnaceRecipe furnaceRecipe = (FurnaceRecipe) recipe;
                if(furnaceRecipe.getInput().getType() == Material.RAW_GOLD)
                    recipes.remove();

            } else if(recipe instanceof BlastingRecipe) {

                BlastingRecipe blastingRecipe = (BlastingRecipe) recipe;
                if(blastingRecipe.getInput().getType() == Material.RAW_GOLD)
                    recipes.remove();

            }

        }

        wartung = configManager.isWartung();

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

    public FTSPest getPest() {
        return pest;
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
}
