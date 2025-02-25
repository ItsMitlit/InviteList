package it.mitl.invlteList;

import it.mitl.invlteList.CommandExecutors.InviteExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class InviteList extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic


        this.getCommand("invite").setExecutor(new InviteExecutor());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
