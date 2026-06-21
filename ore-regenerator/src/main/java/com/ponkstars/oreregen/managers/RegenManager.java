package com.ponkstars.oreregen.managers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class RegenManager {

    private final JavaPlugin plugin;
    private final Set<Material> validOres = EnumSet.noneOf(Material.class);
    private final List<TextDisplay> activeDisplays = new ArrayList<>();

    public RegenManager(JavaPlugin plugin) {
        this.plugin = plugin;
        initializeOres();
    }

    private void initializeOres() {
        validOres.addAll(Set.of(
            Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE,
            Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE,
            Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE,
            Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE, Material.NETHER_GOLD_ORE,
            Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE,
            Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE,
            Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE,
            Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE,
            Material.NETHER_QUARTZ_ORE, Material.ANCIENT_DEBRIS
        ));
    }

    public boolean isRegenOre(Material material) {
        return validOres.contains(material);
    }

    public void startRegeneration(Block block, Material originalMaterial, int delaySeconds) {
        Location blockLoc = block.getLocation();
        block.setType(Material.BEDROCK);

        Location spawnLoc = blockLoc.clone().add(0.5, 1.3, 0.5);
        TextDisplay textDisplay = blockLoc.getWorld().spawn(spawnLoc, TextDisplay.class, display -> {
            display.setBillboard(org.bukkit.entity.Display.Billboard.CENTER);
            display.setText("§eRegenerating in: §f" + delaySeconds + "s");
            display.setPersistent(false);
        });
        
        activeDisplays.add(textDisplay);

        new BukkitRunnable() {
            int timeLeft = delaySeconds;

            @Override
            public void run() {
                timeLeft--;
                
                if (timeLeft <= 0) {
                    block.setType(originalMaterial);
                    if (textDisplay.isValid()) {
                        textDisplay.remove();
                        activeDisplays.remove(textDisplay);
                    }
                    this.cancel();
                } else {
                    if (textDisplay.isValid()) {
                        textDisplay.setText("§eRegenerating in: §f" + timeLeft + "s");
                    } else {
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    public void terminateAllDisplays() {
        for (TextDisplay display : activeDisplays) {
            if (display != null && display.isValid()) {
                display.remove();
            }
        }
        activeDisplays.clear();
    }
}
