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
import com.mewin.util.Util;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
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
    
    public static boolean spawnAllowedAtLocation(WGSpawnReasonFlagsPlugin plugin, SpawnReason reason, Location loc) {
        return Util.flagAllowedAtLocation(plugin.getWGP(), reason, loc, WGSpawnReasonFlagsPlugin.ALLOW_SPAWNREASON_FLAG, WGSpawnReasonFlagsPlugin.DENY_SPAWNREASON_FLAG, SpawnReason.ANY);
    }
    
    public static SpawnReason castReason(CreatureSpawnEvent.SpawnReason reason) {
        try {
            return SpawnReason.valueOf(reason.name());
        } catch(IllegalArgumentException ex) {
            return null;
        }
    }
}
