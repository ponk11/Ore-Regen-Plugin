package com.ponkstars.oreregen.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class RegionManager {

    private final JavaPlugin plugin;
    private final File file;
    private FileConfiguration config;

    private Location pos1;
    private Location pos2;

    public RegionManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "regions.yml");
        loadRegions();
    }

    public void setPos1(Location loc) {
        this.pos1 = loc;
        saveRegions();
    }

    public void setPos2(Location loc) {
        this.pos2 = loc;
        saveRegions();
    }

    public boolean isInRegion(Location loc) {
        if (pos1 == null || pos2 == null) return false;
        if (!loc.getWorld().equals(pos1.getWorld())) return false;

        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        double minX = Math.min(pos1.getX(), pos2.getX());
        double maxX = Math.max(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }

    private void saveRegions() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        
        config = new YamlConfiguration();
        
        if (pos1 != null) {
            config.set("region.world", pos1.getWorld().getName());
            config.set("region.pos1.x", pos1.getX());
            config.set("region.pos1.y", pos1.getY());
            config.set("region.pos1.z", pos1.getZ());
        }
        if (pos2 != null) {
            config.set("region.pos2.x", pos2.getX());
            config.set("region.pos2.y", pos2.getY());
            config.set("region.pos2.z", pos2.getZ());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save regions.yml!");
        }
    }

    private void loadRegions() {
        if (!file.exists()) return;
        
        config = YamlConfiguration.loadConfiguration(file);
        if (!config.contains("region.world")) return;

        World world = Bukkit.getWorld(config.getString("region.world"));
        if (world == null) return;

        if (config.contains("region.pos1")) {
            pos1 = new Location(world, 
                config.getDouble("region.pos1.x"), 
                config.getDouble("region.pos1.y"), 
                config.getDouble("region.pos1.z")
            );
        }
        if (config.contains("region.pos2")) {
            pos2 = new Location(world, 
                config.getDouble("region.pos2.x"), 
                config.getDouble("region.pos2.y"), 
                config.getDouble("region.pos2.z")
            );
        }
    }
}
