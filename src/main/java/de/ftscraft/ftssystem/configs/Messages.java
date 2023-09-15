package de.ftscraft.ftssystem.configs;

public class Messages {

    public static final String
            PREFIX = "§7[§4FTS-System§7]" + " ",
            NO_PERM = PREFIX + "Dazu hast du keine Rechte!",
            NUMBER = PREFIX + "Bitte benutz eine gültige Zahl",
            NO_ACTIVE_CHANNEL = PREFIX + "Du hast keinen aktiven Channel ausgewählt. Mache dies mit /channel aktiv <Name>",
            NOW_ACTIVE_CHANNEL = PREFIX + "%s ist nun dein aktiver Channel!",
            CHOOSE_CHANNEL = PREFIX + "Bitte wähle einen Channel. Du kannst die Channel mit §c/channel list §7sehen!",
            LIST_CHANNEL = PREFIX + "Es gibt diese Channel:",
            NO_CHANNEL = PREFIX + "Es wurde kein Channel mit diesem Namen gefunden",
            ALREADY_IN_CHANNEL = PREFIX + "Du bist bereits in diesem Channel",
            NOT_IN_CHANNEL = PREFIX + "Du bist nicht in diesem Channel",
            JOINED_CHANNEL = PREFIX + "Du bist dem Channel %s beigetreten!",
            LEFT_CHANNEL = PREFIX + "Du hast den Channel %s verlassen :(",
            UMFRAGE_CREATED = PREFIX + "Die Umfrage mit der Frage §c%s§7wurde erstellt!",
            NO_UMFRAGE_FOUND = PREFIX + "Du hast keine Umfrage mit /umfrage create FRAGE erstellt!",
            UMFRAGE_ALREADY_STARTED = PREFIX + "Die Umfrage wurde schon gestartet!",
            UMFRAGE_ADDED_OPTION = PREFIX + "Die Option §c%s§7wurde hinzugefügt!",
            UMFRAGE_NOT_STARTED = PREFIX + "Die Umfrage wurde noch garnicht gestartet",
            UMFRAGE_STATED = PREFIX + "Die Umfrage wurde gestartet!",
            UMFRAGE_ENDED = PREFIX + "Die Umfrage wurde beendet!",
            UMFRAGE_ALREADY_CONFIG = PREFIX + "Es wird derzeit eine Umfrage erstellt / ist am laufen",
            PLAYER_NOT_FOUND = PREFIX + "Der Spieler wurde nicht gefunden!",
            ONLY_PLAYER = PREFIX + "Dieser CMD ist nur für Spieler!";

    public static final String
            HELP_CHANNEL =
            "§c---- §e/channel §c---- \n" +
            "§d/channel list §r§5- Listet alle Channel aus\n" +
            "§d/channel join <Name> §r§5- Joint einem Channel \n" +
            "§d/channel leave <Name> §r§5- Verlässt ein Channel \n" +
            "§d/channel aktiv <Name> §r§5- Setzt einen Channel aktiv",

    HELP_FTSSYSTEM =
            "§c---- §e/ftssystem §c----\n" +
            "§e/ftssystem fake <Join|Leave> §b- Täusche alle Spieler mit diesem einfachen Trick!\n" +
            "§e/ftssystem mute <Spieler> §b- Mute einen Spieler mit diesem Command.";

}
