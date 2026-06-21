package com.ponkstars.oreregen.listeners;

import com.ponkstars.oreregen.managers.RegionManager;
import com.ponkstars.oreregen.managers.RegenManager;
import org.bukkit.Material;
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

        // 2. If it is a valid ore, handle immediate drops and regeneration properly
        Material brokenType = block.getType();
        if (regenManager.isRegenOre(brokenType)) {
            event.setCancelled(true);
            
            // Generate natural drops using the tool without breaking the actual block into air
            for (org.bukkit.inventory.ItemStack drop : block.getDrops(player.getInventory().getItemInMainHand())) {
                block.getWorld().dropItemNaturally(block.getLocation(), drop);
            }
            
            // Start the regeneration process passing the exact ore type it used to be
            regenManager.startRegeneration(block, brokenType, 8);
            return;
        }

        // 3. If it's a regular block, protect it
        if (!player.hasPermission("oreregen.admin") && !player.isOp()) {
            event.setCancelled(true);
            player.sendMessage("§cYou cannot break standard blocks in this region!");
        }
    }
}
