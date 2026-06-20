package com.ponkstars.oreregen.commands;

import com.ponkstars.oreregen.menu.SelectionGUI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class RegenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("wand")) {
            ItemStack wand = new ItemStack(Material.BLAZE_ROD);
            ItemMeta meta = wand.getItemMeta();
            meta.setDisplayName("§eRegen Wand");
            wand.setItemMeta(meta);
            player.getInventory().addItem(wand);
            player.sendMessage("§aYou have been given the Regen Wand!");
            return true;
        }

        // Open custom configuration GUI if no args
        SelectionGUI.openGUI(player);
        return true;
    }
}
