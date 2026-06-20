package com.ponkstars.oreregen;

import com.ponkstars.oreregen.commands.RegenCommand;
import com.ponkstars.oreregen.listeners.BlockBreakListener;
import com.ponkstars.oreregen.listeners.WandListener;
import com.ponkstars.oreregen.managers.RegionManager;
import com.ponkstars.oreregen.managers.RegenManager;
import org.bukkit.plugin.java.JavaPlugin;

public class OreRegenerator extends JavaPlugin {

    @Override
    public void onEnable() {
        RegionManager regionManager = new RegionManager();
        RegenManager regenManager = new RegenManager(this);

        // Register Event Listeners
        getServer().getPluginManager().registerEvents(new BlockBreakListener(regionManager, regenManager), this);
        getServer().getPluginManager().registerEvents(new WandListener(regionManager), this);

        // Register Executive Commands
        getCommand("oreregen").setExecutor(new RegenCommand());

        getLogger().info("OreRegenerator by ponkstars has been fully initialized!");
    }

    @Override
    public void onDisable() {
        getLogger().info("OreRegenerator has been safely disabled!");
    }
}
