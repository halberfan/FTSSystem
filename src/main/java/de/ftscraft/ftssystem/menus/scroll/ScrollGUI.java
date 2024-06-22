package de.ftscraft.ftssystem.menus.scroll;

import de.ftscraft.ftssystem.main.FtsSystem;
import de.ftscraft.ftssystem.menus.FTSGUI;
import de.ftscraft.ftsutils.items.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

public class ScrollGUI implements FTSGUI {

    private final Inventory inventory;

    public static ScrollGUI scrollGUI = null;

    private ScrollGUI(FtsSystem plugin) {
        inventory = Bukkit.createInventory(null, 9, Component.text("Schriftrolle").color(NamedTextColor.DARK_BLUE));
        for (String s : plugin.getEssentialsPlugin().getWarps().getList()) {
            if (s.startsWith("SCROLL_POINT")) {
                inventory.addItem(new ItemBuilder(Material.PAPER)
                        .name(s.substring(12))
                        .addPDC("SCROLL", s, PersistentDataType.STRING)
                        .lore("§7Nutze deine Schriftrolle und teleportiere dich hierhin")
                        .build());
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public static void init(FtsSystem plugin) {
        if (scrollGUI == null)
            scrollGUI = new ScrollGUI(plugin);
    }

}
