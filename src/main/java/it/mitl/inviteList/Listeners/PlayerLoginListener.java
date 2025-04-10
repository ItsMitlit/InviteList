package it.mitl.inviteList.Listeners;

import it.mitl.inviteList.Subroutines.WhitelistManager;
import java.util.UUID;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLoginListener implements Listener {
    public PlayerLoginListener() {
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();
        if (event.getPlayer().isOp()) {
            event.allow();
        } else if (WhitelistManager.isWhitelisted(playerUUID) == 0) {
            event.allow();
        } else {
            event.disallow(Result.KICK_WHITELIST, "You are not whitelisted on this server. Ask someone to invite you with /invite <player> or do it through console.");
        }

    }
}
