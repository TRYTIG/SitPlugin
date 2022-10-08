package com.trytheitguy.sitplugin.commands;

import com.trytheitguy.sitplugin.SitPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SitToggleCommand implements CommandExecutor {
    public SitToggleCommand(SitPlugin plugin) {
        Objects.requireNonNull(plugin.getCommand("sittoggle")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
            return true;
        }

        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Usage: /" + label);
            return true;
        }

        // Check if player is in the toggled list
        if (SitPlugin.toggledSitPlayers.contains(player)) {
            SitPlugin.toggledSitPlayers.remove(player);
            player.sendMessage(ChatColor.GRAY + "You have toggled the ability to sit " + ChatColor.GREEN + "ON");
            return true;
        }

        // Player not in list, add.
        SitPlugin.toggledSitPlayers.add(player);
        player.sendMessage(ChatColor.GRAY + "You have toggled the ability to sit " + ChatColor.RED + "OFF");

        return true;
    }
}
