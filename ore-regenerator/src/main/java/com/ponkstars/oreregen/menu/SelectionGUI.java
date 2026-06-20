package com.ponkstars.oreregen.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SelectionGUI {

    public static void openGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, "§8Ore Regen Settings");

        ItemStack clock = new ItemStack(Material.CLOCK);
        ItemMeta meta = clock.getItemMeta();
        meta.setDisplayName("§bAdjust Regen Delay");
        clock.setItemMeta(meta);

        gui.setItem(13, clock); // Place button in center slot
        player.openInventory(gui);
    }
}
