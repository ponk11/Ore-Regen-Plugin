package com.ponkstars.oreregen.managers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashSet;
import java.util.Set;

public class RegenManager {

    private final JavaPlugin plugin;
    private final Set<Material> validOres = new HashSet<>();

    public RegenManager(JavaPlugin plugin) {
        this.plugin = plugin;
        // Define what blocks are allowed to regenerate
        validOres.add(Material.DIAMOND_ORE);
        validOres.add(Material.DEEPSLATE_DIAMOND_ORE);
        validOres.add(Material.GOLD_ORE);
        validOres.add(Material.IRON_ORE);
    }

    public boolean isRegenOre(Material material) {
        return validOres.contains(material);
    }

    public void startRegeneration(Block block, int delaySeconds) {
        Material originalMaterial = block.getType();
        Location blockLoc = block.getLocation();
        
        // Change the ore to Bedrock
        block.setType(Material.BEDROCK);

        // Spawn a TextDisplay entity 1.5 blocks above the bedrock for the hologram
        Location spawnLoc = blockLoc.clone().add(0.5, 1.5, 0.5);
        TextDisplay textDisplay = blockLoc.getWorld().spawn(spawnLoc, TextDisplay.class, display -> {
            display.setBillboard(org.bukkit.entity.Display.Billboard.CENTER);
            display.setText("§eRegenerating in: §f" + delaySeconds + "s");
        });

        // Run a repetitive scheduler ticking every 20 server ticks (1 second)
        new BukkitRunnable() {
            int timeLeft = delaySeconds;

            @Override
            public void run() {
                timeLeft--;
                
                if (timeLeft <= 0) {
                    // Time's up: revert block, drop original ore item, delete text
                    block.setType(originalMaterial);
                    blockLoc.getWorld().dropItemNaturally(blockLoc, new org.bukkit.inventory.ItemStack(originalMaterial));
                    textDisplay.remove();
                    this.cancel();
                } else {
                    // Update the floating hologram text string dynamically
                    textDisplay.setText("§eRegenerating in: §f" + timeLeft + "s");
                }
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }
}