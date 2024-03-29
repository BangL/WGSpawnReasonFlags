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
import com.mewin.WGCustomFlags.flags.CustomSetFlag;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.EnumFlag;
import de.bangl.wgsrf.listener.CreatureSpawnListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author BangL <henno.rickowski@googlemail.com>
 * @author mewin <mewin001@hotmail.de>
 */
public class WGSpawnReasonFlagsPlugin extends JavaPlugin {
    
    public static final EnumFlag SPAWNREASON_FLAG = new EnumFlag("spawnreason", SpawnReason.class);
    public static final CustomSetFlag ALLOW_SPAWNREASON_FLAG = new CustomSetFlag("allow-spawnreason", SPAWNREASON_FLAG);
    public static final CustomSetFlag DENY_SPAWNREASON_FLAG = new CustomSetFlag("deny-spawnreason", SPAWNREASON_FLAG);
    
    private WorldGuardPlugin pluginWorldGuard;
    private boolean hasDragonTravel;
    
    public WorldGuardPlugin getWGP() {
        return pluginWorldGuard;
    }
    
    public boolean hasDragonTravel() {
        return hasDragonTravel;
    }
    
    @Override
    public void onEnable() {
        
        this.pluginWorldGuard = Utils.getWorldGuard(this);
        
        this.hasDragonTravel = (this.getServer().getPluginManager().getPlugin("DragonTravel") != null);
        
        Utils.getWGCustomFlags(this).addCustomFlag(ALLOW_SPAWNREASON_FLAG);
        Utils.getWGCustomFlags(this).addCustomFlag(DENY_SPAWNREASON_FLAG);
        
        this.getServer().getPluginManager().registerEvents(new CreatureSpawnListener(this), this);
        
    }
}
