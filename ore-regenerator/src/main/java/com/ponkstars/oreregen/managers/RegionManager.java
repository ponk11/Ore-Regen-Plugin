package com.ponkstars.oreregen.managers;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class RegionManager {

    private Location pos1;
    private Location pos2;

    public void setPosition1(Location loc) {
        this.pos1 = loc;
    }

    public void setPosition2(Location loc) {
        this.pos2 = loc;
    }

    public boolean isRegionSet() {
        return pos1 != null && pos2 != null;
    }

    public boolean isInRegion(Location loc) {
        if (!isRegionSet() || !loc.getWorld().equals(pos1.getWorld())) {
            return false;
        }

        int x1 = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int x2 = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int y1 = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int y2 = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int z1 = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int z2 = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        int bx = loc.getBlockX();
        int by = loc.getBlockY();
        int bz = loc.getBlockZ();

        return bx >= x1 && bx <= x2 && by >= y1 && by <= y2 && bz >= z1 && bz <= z2;
    }
}
