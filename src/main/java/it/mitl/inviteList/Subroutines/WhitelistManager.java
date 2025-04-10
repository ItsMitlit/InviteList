package it.mitl.inviteList.Subroutines;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class WhitelistManager {
    private static final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

    public WhitelistManager() {
    }

    public static int addToWhitelist(Player inviter, OfflinePlayer invitee) {
        UUID inviteeUUID = invitee.getUniqueId();
        String inviterUUID = inviter != null ? inviter.getUniqueId().toString() : "CONSOLE";
        File whitelistFile = new File("plugins/InviteList/whitelist.json");

        try {
            byte var17;
            try (FileReader reader = new FileReader(whitelistFile)) {
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                JsonArray whitelistArray = jsonObject.getAsJsonArray("whitelist");

                for(int i = 0; i < whitelistArray.size(); ++i) {
                    if (whitelistArray.get(i).getAsString().equals(inviteeUUID.toString())) {
                        byte var9 = 1;
                        return var9;
                    }
                }

                whitelistArray.add(inviteeUUID.toString());

                try (FileWriter writer = new FileWriter(whitelistFile)) {
                    writer.write(gson.toJson(jsonObject));
                }

                updateUserdataFiles(inviterUUID, inviteeUUID.toString());
                var17 = 0;
            }

            return var17;
        } catch (IOException e) {
            e.printStackTrace();
            return 3;
        }
    }

    public static int isWhitelisted(UUID playerUUID) {
        File whitelistFile = new File("plugins/InviteList/whitelist.json");

        try (FileReader reader = new FileReader(whitelistFile)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray whitelistArray = jsonObject.getAsJsonArray("whitelist");

            for(int i = 0; i < whitelistArray.size(); ++i) {
                if (whitelistArray.get(i).getAsString().equals(playerUUID.toString())) {
                    return 0;
                }
            }

            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 2;
        }
    }

    private static void updateUserdataFiles(String inviterUUID, String inviteeUUID) throws IOException {
        File inviterFile = new File("plugins/InviteList/userdata/" + inviterUUID + ".json");
        File inviteeFile = new File("plugins/InviteList/userdata/" + inviteeUUID + ".json");
        JsonObject inviteeData = new JsonObject();
        if (inviteeFile.exists()) {
            try (FileReader reader = new FileReader(inviteeFile)) {
                inviteeData = JsonParser.parseReader(reader).getAsJsonObject();
            }
        }

        inviteeData.addProperty("invitedby", inviterUUID);
        FileWriter writer = new FileWriter(inviteeFile);

        try {
            writer.write(gson.toJson(inviteeData));
        } catch (Throwable var16) {
            try {
                writer.close();
            } catch (Throwable var12) {
                var16.addSuppressed(var12);
            }

            throw var16;
        }

        writer.close();
        if (!inviterUUID.equals("CONSOLE")) {
            JsonObject inviterData = new JsonObject();
            if (inviterFile.exists()) {
                try (FileReader reader = new FileReader(inviterFile)) {
                    inviterData = JsonParser.parseReader(reader).getAsJsonObject();
                }
            }

            JsonArray invitedArray = inviterData.has("invited") ? inviterData.getAsJsonArray("invited") : new JsonArray();
            invitedArray.add(inviteeUUID);
            inviterData.add("invited", invitedArray);

            try (FileWriter writer = new FileWriter(inviterFile)) {
                writer.write(gson.toJson(inviterData));
            }
        }

    }
}
