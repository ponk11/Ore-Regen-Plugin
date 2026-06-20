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

        // 1. Force check if the block is inside the region boundaries
        if (!regionManager.isInRegion(block.getLocation())) {
            return; 
        }

        // 2. If it is a valid ore, handle the regeneration loop
        if (regenManager.isRegenOre(block.getType())) {
            event.setCancelled(true);
            regenManager.startRegeneration(block, 30);
            return;
        }

        // 3. If it's a regular block, explicitly cancel it unless they are OP or have admin perms
        if (!player.hasPermission("oreregen.admin") && !player.isOp()) {
            event.setCancelled(true);
            player.sendMessage("§cYou cannot break standard blocks in this region!");
        }
    }
}
