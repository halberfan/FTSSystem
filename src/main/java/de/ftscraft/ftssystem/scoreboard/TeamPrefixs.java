/*
 * Copyright (c) 2020.
 * halberfan - AfGMedia / AfGeSports
 */

package de.ftscraft.ftssystem.scoreboard;

import org.bukkit.entity.Player;

public enum  TeamPrefixs {

    ADMIN("§bAdmin", "ftsengine.admin", "000Admin"),
    MODERATOR("§bModerator", "ftsengine.moderator", "001Moderator"),
    HELFER("§bHelfer", "ftsengine.helfer", "002Helfer"),
    WALKURE("§cWalküre", "ftsengine.walkure", "003Walkuere"),
    PREMIUM("§cPremium", "ftsengine.premium", "004Premium"),
    ARCHITEKT("§eArchitekt", "ftsengine.architekt", "005Architekt"),
    EHRENBURGER("§eEhrenbürger", "ftsengine.ehrenburger", "006EBuerger"),
    PAPST("§9Papst","ftsengine.papst", "007Papst"),
    RAUBER("§9Söldner", "ftsengine.rauber", "008Rauber"),
    RICHTER("§9Richter", "ftsengine.richter", "009Richter"),
    MEJSTER("§9Medikus", "ftsengine.mejster", "010Mejster"),
    KAUFMANN("§9Kaufmann", "ftsengine.kaufmann", "011Kaufmann"),
    BRAUMEISTER("§9Braumeister", "ftsengine.braumeister", "012BMeister"),
    KURATOR("§2Kurator", "ftsengine.kurator", "013Kurator"),
    KONIG("§2König", "ftsengine.konig", "014Konig"),
    HERZOG("§2Herzog", "ftsengine.herzog", "015Herzog"),
    FURST("§2Fürst", "ftsengine.furst", "016Furst"),
    GRAF("§2Graf", "ftsengine.graf", "017Graf"),
    BURGHERR("§2Burgherr", "ftsengine.burgherr", "018Burgherr"),
    RITTER("§2Ritter", "ftsengine.ritter", "019Ritter"),
    INTENDANT("§2Intendant", "ftsengine.intendant", "020Intendant"),
    GILDENHERR("§2Gildenherr", "ftsengine.gildenherr", "021Gildenherr"),
    STADTHERR("§2Stadtherr", "ftsengine.stadtherr", "022Stadtherr"),
    MEISTER("§2Meister", "ftsengine.meister", "023Meister"),
    BURGERMEISTER("§2Bürgermeister","ftsengine.burgermeister", "024BMeister"),
    SIEDLER("§2Siedler", "ftsengine.siedler", "025Siedler"),
    VOGT("§6Vogt", "ftsengine.vogt", "026Vogt"),
    HEROLD("§6Herold", "ftsengine.herold", "027Herold"),
    KNAPPE("§6Knappe", "ftsengine.knappe", "028Knappe"),
    SCHAUSPIELER("§6Schauspieler", "ftsengine.schauspieler", "029SSpieler"),
    MUSIKER("§6Musiker", "ftsengine.musiker", "030Musiker"),
    SCHREIBER("§6Schreiber", "ftsengine.schreiber", "031Schreiber"),
    SEEFAHRER("§6Seefahrer", "ftsengine.seefahrer", "032Seefahrer"),
    HAFENMEISTER("§6Hafenmeister", "ftsengine.hafenmeister", "033HMeister"),
    HANDLER("§6Händler", "ftsengine.handler", "034Handler"),
    BURGER("§6Bürger", "ftsengine.burger", "035Burger"),
    REISENDER("§6Reisender", "ftsengine.reisender", "036Reisender"),
    NEULING("§6Neuling", "ftsengine.neuling", "037Neuling");

    String prefix;
    String permission;
    String teamName;
    TeamPrefixs(String prefix, String permission, String teamName)
    {
        this.teamName = teamName;
        this.prefix = prefix;
        this.permission = permission;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getPermission()
    {
        return permission;
    }

    public String getPrefix()
    {
        return prefix;
    }

}
