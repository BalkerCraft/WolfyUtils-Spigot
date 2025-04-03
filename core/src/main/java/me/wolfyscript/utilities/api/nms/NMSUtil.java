/*
 *       WolfyUtilities, APIs and Utilities for Minecraft Spigot plugins
 *                      Copyright (C) 2021  WolfyScript
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.wolfyscript.utilities.api.nms;

import com.wolfyscript.utilities.bukkit.nms.fallback.FallbackNMSEntry;
import me.wolfyscript.utilities.api.WolfyUtilities;
import me.wolfyscript.utilities.util.version.ServerVersion;
import org.bukkit.plugin.Plugin;

public abstract class NMSUtil {

    private final WolfyUtilities wolfyUtilities;

    private final Plugin plugin;
    protected BlockUtil blockUtil;
    protected ItemUtil itemUtil;
    protected RecipeUtil recipeUtil;
    protected InventoryUtil inventoryUtil;
    protected NBTUtil nbtUtil;

    protected NetworkUtil networkUtil;

    /**
     * The class that implements this NMSUtil needs to have a constructor with just the WolfyUtilities parameter.
     *
     * @param wolfyUtilities
     */
    protected NMSUtil(WolfyUtilities wolfyUtilities) {
        this.wolfyUtilities = wolfyUtilities;
        this.plugin = wolfyUtilities.getPlugin();
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * Creates an instance of the specific NMSUtil of the current Minecraft version.
     *
     * @param wolfyUtilities
     * @return
     */
    public static NMSUtil create(WolfyUtilities wolfyUtilities) {
        if(ServerVersion.isIsJUnitTest()) {
            return null;
        }
        return new FallbackNMSEntry(wolfyUtilities); // When using 1.21+ WolfyUtils no longer provides NMSUtils
    }

    public WolfyUtilities getWolfyUtilities() {
        return wolfyUtilities;
    }

    @Deprecated(forRemoval = true, since = "4.17")
    public BlockUtil getBlockUtil() {
        return blockUtil;
    }

    @Deprecated(forRemoval = true, since = "4.17")
    public ItemUtil getItemUtil() {
        return itemUtil;
    }

    @Deprecated(forRemoval = true, since = "4.17")
    public InventoryUtil getInventoryUtil() {
        return inventoryUtil;
    }

    @Deprecated(forRemoval = true, since = "4.17")
    public NBTUtil getNBTUtil() {
        return nbtUtil;
    }

    @Deprecated(forRemoval = true, since = "4.17")
    public RecipeUtil getRecipeUtil() {
        return recipeUtil;
    }

    @Deprecated(forRemoval = true, since = "4.17")
    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

}
