package de.ftscraft.ftssystem.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.ftscraft.ftssystem.main.FtsSystem;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Scanner;

public class APIHandling {

    private static String antiVpnApiKey = "";

    public static boolean hasUserVPN(InetAddress address) {

        String ip = address.toString();

        try {

            URL url = new URL("http://proxycheck.io/v2" + ip + "?vpn=1&asn=1&key=" + antiVpnApiKey);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() != 200) {
                FtsSystem.getPluginLogger().warning("VPN Dienst bei der IP " + ip + " gefailt");
                return false;
            }

            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            JsonElement jsonElement = JsonParser.parseString(inline.toString());
            JsonObject object = jsonElement.getAsJsonObject();

            if (!object.get("status").getAsString().equals("ok")) {
                FtsSystem.getPluginLogger().warning( "VPN Dienst bei der IP " + ip + " gefailt");
                return false;
            }

            JsonObject ipObject = object.getAsJsonObject(ip.substring(1));
            String usingVpnString = ipObject.get("proxy").getAsString();

            return usingVpnString.equals("yes");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void setAntiVpnApiKey(String antiVpnApiKey) {
        APIHandling.antiVpnApiKey = antiVpnApiKey;
    }



}
