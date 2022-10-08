package com.trytheitguy.sitplugin.listeners;

import com.trytheitguy.sitplugin.ChairArrow;
import com.trytheitguy.sitplugin.SitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class MoveListener implements Listener {
    public MoveListener(SitPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        check(player);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        check(player);
    }

    private void check(Player player) {
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
