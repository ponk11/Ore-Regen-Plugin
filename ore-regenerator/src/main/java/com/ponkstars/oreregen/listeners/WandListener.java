package com.ponkstars.oreregen.listeners;

import com.ponkstars.oreregen.managers.RegionManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class WandListener implements Listener {

    private final RegionManager regionManager;

    public WandListener(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    // 1. Catches Right Click (Pos 2) and Survival Left Click (Pos 1)
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || item.getType() != Material.WOODEN_HOE) return;
        if (!player.isOp() && !player.hasPermission("oreregen.admin")) return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock() != null) {
            event.setCancelled(true);
            regionManager.setPos1(event.getClickedBlock().getLocation());
            player.sendMessage("§a[OreRegen] Position 1 successfully saved!");
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
            event.setCancelled(true);
            regionManager.setPos2(event.getClickedBlock().getLocation());
            player.sendMessage("§a[OreRegen] Position 2 successfully saved!");
        }
    }

    // 2. Catch-all for Creative Mode Left Click (Pos 1) to stop the block from breaking
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCreativeLeftClick(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() != Material.WOODEN_HOE) return;
        if (!player.isOp() && !player.hasPermission("oreregen.admin")) return;

        // Force set Position 1 and cancel the block break completely
        event.setCancelled(true);
        regionManager.setPos1(event.getBlock().getLocation());
        player.sendMessage("§a[OreRegen] Position 1 successfully saved (Creative)! ");
    }
}
