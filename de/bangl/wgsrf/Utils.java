/*
 * Copyright (C) 2012 BangL <henno.rickowski@googlemail.com>
 *                    mewin <mewin001@hotmail.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.bangl.wgsrf;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author BangL <henno.rickowski@googlemail.com>
 * @author mewin <mewin001@hotmail.de>
 */
public class Utils {
    
    public static WGCustomFlagsPlugin getWGCustomFlags(WGSpawnReasonFlagsPlugin plugin) {
        final Plugin wgcf = plugin.getServer().getPluginManager().getPlugin("WGCustomFlags");
        if (wgcf == null || !(wgcf instanceof WGCustomFlagsPlugin)) {
            return null;
        }
        return (WGCustomFlagsPlugin)wgcf;
    }
    
    public static WorldGuardPlugin getWorldGuard(WGSpawnReasonFlagsPlugin plugin) {
        final Plugin wg = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        if (wg == null || !(wg instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin)wg;
    }
    
    public static boolean spawnAllowedAtLocation(WorldGuardPlugin wgp, SpawnReason reason, Location loc) {
        RegionManager rm = wgp.getRegionManager(loc.getWorld());
        if (rm == null) {
            return true;
        }
        ApplicableRegionSet regions = rm.getApplicableRegions(loc);
        Iterator<ProtectedRegion> itr = regions.iterator();
        Map<ProtectedRegion, Boolean> regionsToCheck = new HashMap<>();
        Set<ProtectedRegion> ignoredRegions = new HashSet<>();
        
        while(itr.hasNext()) {
            ProtectedRegion region = itr.next();
            
            if (ignoredRegions.contains(region)) {
                continue;
            }
            
            Object allowed = spawnAllowedInRegion(region, reason);
            
            if (allowed != null) {
                ProtectedRegion parent = region.getParent();
                
                while(parent != null) {
                    ignoredRegions.add(parent);
                    
                    parent = parent.getParent();
                }
                
                regionsToCheck.put(region, (boolean) allowed);
            }
        }
        
        if (regionsToCheck.size() >= 1) {
            Iterator<Map.Entry<ProtectedRegion, Boolean>> itr2 = regionsToCheck.entrySet().iterator();
            
            while(itr2.hasNext()) {
                Map.Entry<ProtectedRegion, Boolean> entry = itr2.next();
                
                ProtectedRegion region = entry.getKey();
                boolean value = entry.getValue();
                
                if (ignoredRegions.contains(region)) {
                    continue;
                }
                
                if (value) { // allow > deny
                    return true;
                }
            }
            
            return false;
        } else {
            ProtectedRegion global = rm.getRegion("__global__");
            Object allowed = null;
            if (global != null) {
                allowed = spawnAllowedInRegion(rm.getRegion("__global__"), reason);
            }
            if (allowed != null) {
                return (boolean) allowed;
            } else {
                return true;
            }
        }
    }
    
    public static Object spawnAllowedInRegion(ProtectedRegion region, SpawnReason reason) {
        HashSet<SpawnReason> allowedCauses = (HashSet<SpawnReason>) region.getFlag(WGSpawnReasonFlagsPlugin.ALLOW_SPAWNREASON_FLAG);
        HashSet<SpawnReason> blockedCauses = (HashSet<SpawnReason>) region.getFlag(WGSpawnReasonFlagsPlugin.DENY_SPAWNREASON_FLAG);
        
        if (allowedCauses != null
                && (allowedCauses.contains(reason) || allowedCauses.contains(SpawnReason.ANY))) {
            return true;
        }
        else if(blockedCauses != null
                && (blockedCauses.contains(reason) || blockedCauses.contains(SpawnReason.ANY))) {
            return false;
        } else {
            return null;
        }
    }
    
    public static SpawnReason castReason(CreatureSpawnEvent.SpawnReason reason) {
        try {
            return SpawnReason.valueOf(reason.name());
        } catch(IllegalArgumentException ex) {
            return null;
        }
    }
}
