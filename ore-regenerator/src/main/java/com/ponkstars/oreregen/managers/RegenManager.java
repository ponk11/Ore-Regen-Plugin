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
        
        // Coal
        validOres.add(Material.COAL_ORE);
        validOres.add(Material.DEEPSLATE_COAL_ORE);
        
        // Iron
        validOres.add(Material.IRON_ORE);
        validOres.add(Material.DEEPSLATE_IRON_ORE);
        
        // Copper
        validOres.add(Material.COPPER_ORE);
        validOres.add(Material.DEEPSLATE_COPPER_ORE);
        
        // Gold
        validOres.add(Material.GOLD_ORE);
        validOres.add(Material.DEEPSLATE_GOLD_ORE);
        validOres.add(Material.NETHER_GOLD_ORE);
        
        // Redstone
        validOres.add(Material.REDSTONE_ORE);
        validOres.add(Material.DEEPSLATE_REDSTONE_ORE);
        
        // Lapis Lazuli
        validOres.add(Material.LAPIS_ORE);
        validOres.add(Material.DEEPSLATE_LAPIS_ORE);
        
        // Diamond
        validOres.add(Material.DIAMOND_ORE);
        validOres.add(Material.DEEPSLATE_DIAMOND_ORE);
        
        // Emerald
        validOres.add(Material.EMERALD_ORE);
        validOres.add(Material.DEEPSLATE_EMERALD_ORE);
        
        // Nether Quartz & Ancient Debris
        validOres.add(Material.NETHER_QUARTZ_ORE);
        validOres.add(Material.ANCIENT_DEBRIS);
    }

    public boolean isRegenOre(Material material) {
        return validOres.contains(material);
    }

    public void startRegeneration(Block block, int delaySeconds) {
        Material originalMaterial = block.getType();
        Location blockLoc = block.getLocation();
        
        block.setType(Material.BEDROCK);

        Location spawnLoc = blockLoc.clone().add(0.5, 1.5, 0.5);
        TextDisplay textDisplay = blockLoc.getWorld().spawn(spawnLoc, TextDisplay.class, display -> {
            display.setBillboard(org.bukkit.entity.Display.Billboard.CENTER);
            display.setText("§eRegenerating in: §f" + delaySeconds + "s");
        });

        new BukkitRunnable() {
            int timeLeft = delaySeconds;

            @Override
            public void run() {
                timeLeft--;
                
                if (timeLeft <= 0) {
                    block.setType(originalMaterial);
                    blockLoc.getWorld().dropItemNaturally(blockLoc, new org.bukkit.inventory.ItemStack(originalMaterial));
                    textDisplay.remove();
                    this.cancel();
                } else {
                    textDisplay.setText("§eRegenerating in: §f" + timeLeft + "s");
                }
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }
}
