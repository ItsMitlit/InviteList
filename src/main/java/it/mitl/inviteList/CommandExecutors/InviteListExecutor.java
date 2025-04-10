package it.mitl.inviteList.CommandExecutors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class InviteListExecutor implements CommandExecutor {
    public InviteListExecutor() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(String.valueOf(ChatColor.RED) + "Invalid usage. Use /invitelist help for help.");
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(String.valueOf(ChatColor.GOLD) + "InviteList Help");
            sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/invite <player> - Adds a player to the whitelist.");
            sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/invitelist help - Displays this help message.");
            sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/invitelist site - Responds with a link to the modrinth page.");
            return true;
        } else if (args[0].equalsIgnoreCase("site")) {
            sender.sendMessage(String.valueOf(ChatColor.GOLD) + "Modrinth -> https://modrinth.com/plugin/invitelist)");
            return true;
        } else {
            return false;
        }
    }
}
