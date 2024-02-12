package de.ftscraft.ftssystem.configs;

import net.kyori.adventure.text.format.NamedTextColor;

public class Messages {

    public static final NamedTextColor TEXT_COLOR = NamedTextColor.GRAY;
    public static final NamedTextColor HIGHLIGHT_COLOR = NamedTextColor.RED;

    public static final String
            PREFIX = "§7[§4FTS-System§7] ",
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
            UMFRAGE_NOT_STARTED = PREFIX + "Die Umfrage wurde noch gar nicht gestartet",
            UMFRAGE_STATED = PREFIX + "Die Umfrage wurde gestartet!",
            UMFRAGE_ENDED = PREFIX + "Die Umfrage wurde beendet!",
            UMFRAGE_ALREADY_CONFIG = PREFIX + "Es wird derzeit eine Umfrage erstellt / ist am laufen",
            PLAYER_NOT_FOUND = PREFIX + "Der Spieler wurde nicht gefunden!",
            WRONG_USAGE = PREFIX + "Bitte nutze den Befehl so: " + HIGHLIGHT_COLOR + "%s",
            NOT_ENOUGH_MONEY = PREFIX + "Du brauchst %d Taler.",
            ONLY_PLAYER = PREFIX + "Dieser CMD ist nur für Spieler!";


    public static final String

    HELP_FTSSYSTEM =
            """
                    §c---- §e/ftssystem §c----
                    §e/ftssystem fake <Join|Leave> §b- Täusche alle Spieler mit diesem einfachen Trick!
                    §e/ftssystem mute <Spieler> §b- Mute einen Spieler mit diesem Command.""";

}
