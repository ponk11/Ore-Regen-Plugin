package com.ponkstars.oreregen.listeners;

import com.ponkstars.oreregen.managers.RegionManager;
import com.ponkstars.oreregen.managers.RegenManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private final RegionManager regionManager;
    private final RegenManager regenManager;

    public BlockBreakListener(RegionManager regionManager, RegenManager regenManager) {
        this.regionManager = regionManager;
        this.regenManager = regenManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        // 1. Check if the block is inside the region boundaries
        if (!regionManager.isInRegion(block.getLocation())) {
            return; 
        }

        // 2. If it is a valid ore, handle immediate drops and regeneration
        if (regenManager.isRegenOre(block.getType())) {
            event.setCancelled(true);
            
            // Drop items immediately using the tool the player broke it with (respects Fortune/Silk Touch)
            block.breakNaturally(player.getInventory().getItemInMainHand());
            
            // Start the regeneration process with an 8-second delay
            regenManager.startRegeneration(block, 8);
            return;
        }

        // 3. If it's a regular block, protect it
        if (!player.hasPermission("oreregen.admin") && !player.isOp()) {
            event.setCancelled(true);
            player.sendMessage("§cYou cannot break standard blocks in this region!");
        }
    }
}
