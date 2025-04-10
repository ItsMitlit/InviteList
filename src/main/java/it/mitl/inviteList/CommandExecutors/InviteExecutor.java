package it.mitl.inviteList.CommandExecutors;

import it.mitl.inviteList.Subroutines.WhitelistManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InviteExecutor implements CommandExecutor {
    public InviteExecutor() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(String.valueOf(ChatColor.RED) + "Usage: /invite <player>");
            return true;
        } else {
            String playerName = args[0];
            OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
            if (sender instanceof Player) {
                Player inviter = (Player)sender;
                int response = WhitelistManager.addToWhitelist(inviter, player);
                this.handleResponse(sender, playerName, response);
            } else {
                int response = WhitelistManager.addToWhitelist((Player)null, player);
                this.handleResponse(sender, playerName, response);
            }

            return true;
        }
    }

    private void handleResponse(CommandSender sender, String playerName, int response) {
        if (response == 0) {
            String var10001 = String.valueOf(ChatColor.GREEN);
            sender.sendMessage(var10001 + playerName + " has been invited to the server!");
        } else if (response == 1) {
            String var4 = String.valueOf(ChatColor.RED);
            sender.sendMessage(var4 + playerName + " has already been invited to the server.");
        } else {
            String var5 = String.valueOf(ChatColor.RED);
            sender.sendMessage(var5 + "An error occurred while inviting " + playerName + " to the server.");
        }

    }
}
