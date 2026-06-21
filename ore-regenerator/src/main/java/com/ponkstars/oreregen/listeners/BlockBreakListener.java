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
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

    private final RegionManager regionManager;
    private final RegenManager regenManager;

    public BlockBreakListener(RegionManager regionManager, RegenManager regenManager) {
        this.regionManager = regionManager;
        this.regenManager = regenManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (!regionManager.isInRegion(block.getLocation())) {
            return; 
        }

        Player player = event.getPlayer();
        Material brokenType = block.getType();

        if (regenManager.isRegenOre(brokenType)) {
            event.setCancelled(true);
            
            // Generate immediate block drops respecting enchantments
            for (ItemStack drop : block.getDrops(player.getInventory().getItemInMainHand())) {
                block.getWorld().dropItemNaturally(block.getLocation(), drop);
            }
            
            // Turn block to bedrock and queue 8-second refresh loop
            regenManager.startRegeneration(block, brokenType, 8);
            return;
        }

        // Lock standard blocks inside the region bounds
        if (!player.isOp() && !player.hasPermission("oreregen.admin")) {
            event.setCancelled(true);
            player.sendMessage("§cYou cannot break environmental blocks inside this region!");
        }
    }
}
