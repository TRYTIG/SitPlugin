package com.trytheitguy.sitplugin.listeners;

import com.trytheitguy.sitplugin.ChairArrow;
import com.trytheitguy.sitplugin.SitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class RightClickListener implements Listener {
    public RightClickListener(SitPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        // Check if player is sneaking
        if (event.getPlayer().isSneaking())
            return;

        // Check if action is RIGHT CLICK BLOCK
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;

        // Check if block was a stair
        if (!(Objects.requireNonNull(event.getClickedBlock()).getBlockData() instanceof Stairs))
            return;

        // Check if player is in the toggled list
        if (SitPlugin.toggledSitPlayers.contains(event.getPlayer()))
            return;

        // Check if stair is pointing the right way (whether the "half" is bottom)
        Stairs stairs = (Stairs) event.getClickedBlock().getBlockData();
        if (!stairs.getHalf().equals(Bisected.Half.BOTTOM))
            return;

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        // Check if player is already sitting/in vehicle
        if (player.isInsideVehicle())
            return;

        // Summon an arrow at the position of the block and mount the player to it
        Location chairCenterLocation = block.getLocation().add(0.5D, 0D, 0.5D);
        Arrow arrow = player.getWorld().spawnArrow(chairCenterLocation, new Vector(0, 0, 0), 0, 0);

        // Set arrow passenger to the player
        arrow.addPassenger(player);

        // Add Player and ChairArrow to list
        ChairArrow chairArrow = new ChairArrow();
        chairArrow.setArrow(arrow);
        chairArrow.setChair(event.getClickedBlock());
        SitPlugin.sittingPlayersHashMap.put(player, chairArrow);

        player.sendMessage(ChatColor.GRAY + "You are now sitting.");
    }
}
