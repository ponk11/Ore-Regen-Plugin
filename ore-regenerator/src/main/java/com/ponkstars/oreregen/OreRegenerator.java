package com.ponkstars.oreregen;

import com.ponkstars.oreregen.commands.RegenCommand;
import com.ponkstars.oreregen.listeners.BlockBreakListener;
import com.ponkstars.oreregen.listeners.WandListener;
import com.ponkstars.oreregen.managers.RegionManager;
import com.ponkstars.oreregen.managers.RegenManager;
import org.bukkit.plugin.java.JavaPlugin;

public class OreRegenerator extends JavaPlugin {

    private RegionManager regionManager;
    private RegenManager regenManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.regionManager = new RegionManager(this);
        this.regenManager = new RegenManager(this);

        getServer().getPluginManager().registerEvents(new BlockBreakListener(regionManager, regenManager), this);
        getServer().getPluginManager().registerEvents(new WandListener(regionManager), this);

        // Pass regionManager to the command executor
        getCommand("oreregen").setExecutor(new RegenCommand(regionManager));

        getLogger().info("OreRegen configuration loaded successfully with text command bounds!");
    }

    @Override
    public void onDisable() {
        if (regenManager != null) {
            regenManager.terminateAllDisplays();
        }
        getLogger().info("OreRegen has been safely unloaded.");
    }
}
