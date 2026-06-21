package com.ponkstars.oreregen.listeners;

import com.ponkstars.oreregen.managers.RegionManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WandListener implements Listener {

    private final RegionManager regionManager;

    public WandListener(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if player is holding a wooden hoe (the default selection wand)
        if (item == null || item.getType() != Material.WOODEN_HOE) {
            return;
        }

        // Check if player has permission
        if (!player.hasPermission("oreregen.admin") && !player.isOp()) {
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock() != null) {
            event.setCancelled(true);
            regionManager.setPos1(event.getClickedBlock().getLocation());
            player.sendMessage("§aOreRegen: Position 1 set!");
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
            event.setCancelled(true);
            regionManager.setPos2(event.getClickedBlock().getLocation());
            player.sendMessage("§aOreRegen: Position 2 set!");
        }
    }
}
