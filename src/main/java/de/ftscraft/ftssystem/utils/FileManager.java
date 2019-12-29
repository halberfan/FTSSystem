/*
 * Copyright (c) 2019.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.utils;

import de.ftscraft.ftssystem.main.FtsSystem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private FtsSystem plugin;

    private File book_file;
    private FileConfiguration book_cfg;

    String bookCMD;

    public FileManager(FtsSystem plugin) {
        this.plugin = plugin;
        this.book_file = new File(plugin.getDataFolder() + "//tutorialbook.yml");
        this.book_cfg = YamlConfiguration.loadConfiguration(book_file);
        loadBookComamnd();
    }

    public void loadBookComamnd() {

        book_cfg.options().copyDefaults(true);
        book_cfg.addDefault("command", "give <player> written_book 1 0 {pages:[\"[\\\"\\\",{\\\"text\\\":\\\"Herzlich Willkommen im Land Parsifal lieber Reisender!\\\\n\\\\nHier habt ihr ein nützliches Handbuch, was euch alles wichtige für eure ersten Schritt\\\\nim Land erklärt!\\\\n\\\\nBesucht ggf. unseren \\\"},{\\\"text\\\":\\\"Einsteigerguide \\\",\\\"color\\\":\\\"red\\\",\\\"clickEvent\\\":{\\\"action\\\":\\\"open_url\\\",\\\"value\\\":\\\"https://ftscraft.de/einstieg/\\\"}}]\",\"[\\\"\\\",{\\\"text\\\":\\\"Erste Schritte\\\",\\\"color\\\":\\\"red\\\"},{\\\"text\\\":\\\"\\\\n\\\\nIhr könnt direkt ein schönen Punkt in der Wildnis suchen und euch dort niederlassen und ein eigenes, kleines Dorf bauen.\\\\n\\\\nEure Materialien holt ihr in der Abbauwelt\\\",\\\"color\\\":\\\"reset\\\"}]\",\"[\\\"\\\",{\\\"text\\\":\\\"In die Abbauwelt gelangt ihr mithilfe einer Kutsche in Lohengrin.\\\\n\\\\nAber auch in Taufeld gibt es eine Kutsch zu dieser Welt. Dorthin gelangt ihr mithilfe von \\\"},{\\\"text\\\":\\\"/spawn\\\",\\\"color\\\":\\\"red\\\"}]\",\"[\\\"\\\",{\\\"text\\\":\\\"Der Befehl hat einen hohen Cooldown, weshalb ihr ihn weise nutzen solltet.\\\\n\\\\nIn der Wildnis könnt ihr euch mithilfe von \\\"},{\\\"text\\\":\\\"/sethome \\\",\\\"color\\\":\\\"red\\\"},{\\\"text\\\":\\\"einen Homepunkt einrichten, den ihr jederzeit mit \\\",\\\"color\\\":\\\"reset\\\"},{\\\"text\\\":\\\"/home \\\",\\\"color\\\":\\\"red\\\"},{\\\"text\\\":\\\"erreichen könnt.\\\",\\\"color\\\":\\\"reset\\\"}]\",\"[\\\"\\\",{\\\"text\\\":\\\"Mithilfe der \\\"},{\\\"text\\\":\\\"Karte\\\",\\\"color\\\":\\\"red\\\",\\\"clickEvent\\\":{\\\"action\\\":\\\"open_url\\\",\\\"value\\\":\\\"https://map.ftscraft.de/\\\"}},{\\\"text\\\":\\\" könnt ihr euch wunderbar eine Orientierung schaffen.\\\\n\\\\nEbenso bietet euch die \\\",\\\"color\\\":\\\"reset\\\"},{\\\"text\\\":\\\"Karte von Lohengrin\\\",\\\"color\\\":\\\"red\\\",\\\"clickEvent\\\":{\\\"action\\\":\\\"open_url\\\",\\\"value\\\":\\\"https://ftscraft.de/karte-von-lohengrin/\\\"}},{\\\"text\\\":\\\" den idealen Wegweiser zu allen wichtigen Gebäuden.\\\\n \\\",\\\"color\\\":\\\"reset\\\"}]\",\"[\\\"\\\",{\\\"text\\\":\\\"Wirtschaft \\\",\\\"color\\\":\\\"red\\\"},{\\\"text\\\":\\\"\\\\n\\\\nSicherlich interessiert es dich brennend, wie du an Geld gelangst.\\\\n\\\\nDer leichsteste Weg ist über \\\",\\\"color\\\":\\\"reset\\\"},{\\\"text\\\":\\\"/vote\\\",\\\"color\\\":\\\"red\\\"},{\\\"text\\\":\\\". Neben Geld gibt es auch Spielerpunkte, die du in unserem \\\",\\\"color\\\":\\\"reset\\\"},{\\\"text\\\":\\\"Shop \\\",\\\"color\\\":\\\"red\\\",\\\"clickEvent\\\":{\\\"action\\\":\\\"open_url\\\",\\\"value\\\":\\\"https://ftscraft.de/echtgeld-und-playerpunkte-shop/\\\"}},{\\\"text\\\":\\\"einlösen kannst. \\\",\\\"color\\\":\\\"reset\\\"}]\",\"[\\\"\\\",{\\\"text\\\":\\\"Die zweite Möglichkeit ist natürlich \\\"},{\\\"text\\\":\\\"die Abbauwelt\\\",\\\"color\\\":\\\"red\\\"},{\\\"text\\\":\\\". \\\\n\\\\nDort könnt ihr in erster Linie Gold und andere wertvolle Materialien suchen, die ihr dann verkaufen könnt. \\\\n\\\\n\\\",\\\"color\\\":\\\"reset\\\"},{\\\"text\\\":\\\"Gold ist eure Währung!\\\",\\\"color\\\":\\\"red\\\"},{\\\"text\\\":\\\" Passt also gut darauf auf!\\\",\\\"color\\\":\\\"reset\\\"}]\",\"{\\\"text\\\":\\\" cWichtige Plugins \\\\n\\\\nIhr solltet wissen, dass es bei uns cKrankheiten gibt, die ihr euch auf unterschiedliche Weise einfangen k?nnt.\\\\n\\\\nEbenso m?sst ihr auf eine ausgewogene cErn?hrung achten. Achtet daf?r einfach rechts auf die Anzeige. \\\"}\"],title:Handbuch,author:Arthus,display:{Lore:[\"Ein Guide um den Server zu verstehen\"]}}");
        try {
            book_cfg.save(book_file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.bookCMD = book_cfg.getString("command");

    }

    public String getBookCMD() {
        return bookCMD;
    }
}
