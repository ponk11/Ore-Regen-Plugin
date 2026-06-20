package com.ponkstars.oreregen.listeners;

import com.ponkstars.oreregen.managers.RegionManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandListener implements Listener {

    private final RegionManager regionManager;

    public WandListener(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    @EventHandler
    public void onWandUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || item.getType() != Material.BLAZE_ROD) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.getDisplayName().contains("Regen Wand")) return;

        if (event.getClickedBlock() == null) return;
        event.setCancelled(true);

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            regionManager.setPosition1(event.getClickedBlock().getLocation());
            player.sendMessage("§aPosition 1 set!");
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            regionManager.setPosition2(event.getClickedBlock().getLocation());
            player.sendMessage("§aPosition 2 set!");
        }
    }
}
