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
        saveDefaultConfig();

        RegionManager regionManager = new RegionManager(this);
        RegenManager regenManager = new RegenManager(this);

        getServer().getPluginManager().registerEvents(new BlockBreakListener(regionManager, regenManager), this);
        getServer().getPluginManager().registerEvents(new WandListener(regionManager), this);

        getCommand("oreregen").setExecutor(new RegenCommand());

        getLogger().info("OreRegen has been fully initialized with persistent saving!");
    }

    @Override
    public void onDisable() {
        getLogger().info("OreRegen has been safely disabled!");
    }
}
