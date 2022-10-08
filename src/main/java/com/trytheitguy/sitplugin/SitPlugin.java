package com.trytheitguy.sitplugin;

import com.trytheitguy.sitplugin.commands.SitToggleCommand;
import com.trytheitguy.sitplugin.listeners.BreakEvent;
import com.trytheitguy.sitplugin.listeners.LeaveListener;
import com.trytheitguy.sitplugin.listeners.MoveListener;
import com.trytheitguy.sitplugin.listeners.RightClickListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public final class SitPlugin extends JavaPlugin {
    public static HashMap<Player, ChairArrow> sittingPlayersHashMap = new HashMap<>();
    public static List<Player> toggledSitPlayers = new ArrayList<>();

    private Logger logger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = getLogger();

        // Register Events
        new RightClickListener(this);
        new MoveListener(this);
        new BreakEvent(this);
        new LeaveListener(this);

        // Register commands
        new SitToggleCommand(this);

        logger.info("Plugin Enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("Server is shutting down, removing arrows.");

        // Remove players from chairs
        for (Player player : sittingPlayersHashMap.keySet()) {
            ChairArrow chairArrow = SitPlugin.sittingPlayersHashMap.get(player);
            chairArrow.getArrow().remove();
            player.teleport(chairArrow.getChair().getLocation().add(0.5D, 1D, 0.5D));
            SitPlugin.sittingPlayersHashMap.remove(player);
            player.sendMessage(ChatColor.GRAY + "You are no longer sitting.");
        }
    }
}
