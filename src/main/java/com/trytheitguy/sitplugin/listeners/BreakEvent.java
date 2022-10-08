package com.trytheitguy.sitplugin.listeners;

import com.trytheitguy.sitplugin.ChairArrow;
import com.trytheitguy.sitplugin.SitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

public class BreakEvent implements Listener {
    public BreakEvent(SitPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        remove(event.getBlock());
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        remove(event.getBlock());
    }

    private void remove(Block block) {
        // Loop through sitting players
        for (Player sittingPlayer : SitPlugin.sittingPlayersHashMap.keySet()) {
            ChairArrow chairArrow = SitPlugin.sittingPlayersHashMap.get(sittingPlayer);

            // Player sitting exists at this location
            if (block.equals(chairArrow.getChair())) {
                chairArrow.getArrow().remove();
                sittingPlayer.teleport(chairArrow.getChair().getLocation().add(0.5D, 1D, 0.5D));
                SitPlugin.sittingPlayersHashMap.remove(sittingPlayer);
                sittingPlayer.sendMessage(ChatColor.GRAY + "You are no longer sitting!");
            }
        }
    }
}
