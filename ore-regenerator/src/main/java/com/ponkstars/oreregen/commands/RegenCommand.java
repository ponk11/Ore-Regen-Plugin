package com.ponkstars.oreregen.commands;

import com.ponkstars.oreregen.managers.RegionManager;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RegenCommand implements CommandExecutor {

    private final RegionManager regionManager;

    public RegenCommand(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can execute this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.isOp() && !player.hasPermission("oreregen.admin")) {
            player.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        if (args.length > 0) {
            // Handle /oreregen pos1
            if (args[0].equalsIgnoreCase("pos1")) {
                Block target = player.getTargetBlockExact(5);
                if (target == null) {
                    player.sendMessage("§cYou must be looking at a block within 5 blocks!");
                    return true;
                }
                regionManager.setPos1(target.getLocation());
                player.sendMessage("§a[OreRegen] Position 1 set to your targeted block!");
                return true;
            }
            
            // Handle /oreregen pos2
            if (args[0].equalsIgnoreCase("pos2")) {
                Block target = player.getTargetBlockExact(5);
                if (target == null) {
                    player.sendMessage("§cYou must be looking at a block within 5 blocks!");
                    return true;
                }
                regionManager.setPos2(target.getLocation());
                player.sendMessage("§a[OreRegen] Position 2 set to your targeted block!");
                return true;
            }
        }

        player.sendMessage("§e§l--- OreRegen Commands ---");
        player.sendMessage("§b/oreregen pos1 §7- Sets position 1 at the block you look at.");
        player.sendMessage("§b/oreregen pos2 §7- Sets position 2 at the block you look at.");
        return true;
    }
}
