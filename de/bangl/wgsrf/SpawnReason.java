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

/**
 *
 * @author BangL <henno.rickowski@googlemail.com>
 * @author mewin <mewin001@hotmail.de>
 */
public enum SpawnReason {
    ANY,                // When... uhm.. always!
    BREEDING,           // When an animal breeds to create a child.
    BUILD_IRONGOLEM,    // When an iron golem is spawned by being built.
    BUILD_SNOWMAN,      // When a snowman is spawned by being built.
    BUILD_WITHER,       // When a wither boss is spawned by being built.
    CHUNK_GEN,          // When a creature spawns due to chunk generation.
    CUSTOM,             // When a creature is spawned by plugins.
    EGG,                // When a creature spawns from an egg.
    JOCKEY,             // When an entity spawns as a jockey of another entity. (mostly spider jockeys)
    LIGHTNING,          // When a creature spawns because of a lightning strike.
    NATURAL,            // When something spawns from natural means.
    SLIM_SPLIT,         // When a slime splits.
    SPAWNER,            // When a creature spawns from a spawner.
    SPAWNER_EGG,        // When a creature spawns from a spawner egg.
    VILLAGE_DEFENSE,    // When an iron golem is spawned to defend a village.
    VILLAGE_INVASION    // When a zombie is spawned to invade a village.
}
