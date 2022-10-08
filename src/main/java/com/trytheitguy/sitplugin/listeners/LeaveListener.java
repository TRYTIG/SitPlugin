package com.trytheitguy.sitplugin.listeners;

import com.trytheitguy.sitplugin.ChairArrow;
import com.trytheitguy.sitplugin.SitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {
    public LeaveListener(SitPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Check if player is in chair
        if (!SitPlugin.sittingPlayersHashMap.containsKey(player))
            return;

        ChairArrow chairArrow = SitPlugin.sittingPlayersHashMap.get(player);
        chairArrow.getArrow().remove();
        player.teleport(chairArrow.getChair().getLocation().add(0.5D, 1D, 0.5D));

        SitPlugin.sittingPlayersHashMap.remove(player);

        player.sendMessage(ChatColor.GRAY + "You are no longer sitting.");
    }
}
