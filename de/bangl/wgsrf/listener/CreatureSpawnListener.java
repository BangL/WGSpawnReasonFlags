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
package de.bangl.wgsrf.listener;

import de.bangl.wgsrf.Utils;
import de.bangl.wgsrf.WGSpawnReasonFlagsPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 *
 * @author BangL <henno.rickowski@googlemail.com>
 * @author mewin <mewin001@hotmail.de>
 */
public class CreatureSpawnListener implements Listener {
    private WGSpawnReasonFlagsPlugin plugin;
    
    public CreatureSpawnListener(WGSpawnReasonFlagsPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        // Only handle if spawn reason and location is not null.
        if(event.getSpawnReason() != null
                && event.getLocation() != null) {
            // Cancel if spawn reason is denied here
            if (!Utils.spawnAllowedAtLocation(this.plugin, Utils.castReason(event.getSpawnReason()), event.getLocation())
                    //  but don't cancel DragonTravel dragons if we have DragonTravel installed.
                    && (!this.plugin.hasDragonTravel()
                    || !(event.getEntityType().equals(EntityType.ENDER_DRAGON) && event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.DEFAULT)))) {
                event.setCancelled(true);
            }
        }
    }
}
