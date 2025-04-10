package it.mitl.inviteList;

import it.mitl.inviteList.CommandExecutors.InviteExecutor;
import it.mitl.inviteList.CommandExecutors.InviteListExecutor;
import it.mitl.inviteList.Listeners.PlayerLoginListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.bukkit.plugin.java.JavaPlugin;

public final class InviteList extends JavaPlugin {
    public InviteList() {
    }

    public void onEnable() {
        File userdataDir = new File("plugins/InviteList/userdata");
        if (!userdataDir.exists()) {
            userdataDir.mkdirs();
        }

        File whitelistFile = new File("plugins/InviteList/whitelist.json");
        if (!whitelistFile.exists()) {
            try {
                Files.createFile(Paths.get("plugins/InviteList/whitelist.json"));

                try (FileWriter writer = new FileWriter(whitelistFile)) {
                    writer.write("{\n  \"whitelist\": [\n  ]\n}");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.getCommand("invitelist").setExecutor(new InviteListExecutor());
        this.getCommand("invite").setExecutor(new InviteExecutor());
        this.getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
    }

    public void onDisable() {
    }
}
