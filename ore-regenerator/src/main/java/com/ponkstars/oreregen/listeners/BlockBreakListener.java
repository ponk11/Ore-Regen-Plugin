package com.ponkstars.oreregen.listeners;

import com.ponkstars.oreregen.OreRegenerator;
import com.ponkstars.oreregen.managers.RegionManager;
import com.ponkstars.oreregen.managers.RegenManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private final RegionManager regionManager;
    private final RegenManager regenManager;

    public BlockBreakListener(RegionManager regionManager, RegenManager regenManager) {
        this.regionManager = regionManager;
        this.regenManager = regenManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        // 1. Check if the broken block is within a defined region
        if (!regionManager.isInRegion(block.getLocation())) {
            return; 
        }

        // 2. Check if the block is marked as a regenerating ore
        if (regenManager.isRegenOre(block.getType())) {
            // Cancel the natural block drop so it doesn't break permanently
            event.setCancelled(true);
            
            // Start the regeneration process (turns to bedrock, spawns timer)
            regenManager.startRegeneration(block, 30); // 30 second default timer
            
        } else {
            // 3. If it's a regular block in the region, protect it unless they have '*'
            if (!player.hasPermission("*")) {
                event.setCancelled(true);
                player.sendMessage("§cYou cannot break standard blocks in this region!");
            }
        }
    }
}