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

package me.wolfyscript.utilities.compatibility.plugins.mythicmobs;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import me.wolfyscript.utilities.annotations.WUPluginIntegration;
import me.wolfyscript.utilities.api.WolfyUtilCore;
import me.wolfyscript.utilities.api.WolfyUtilities;
import me.wolfyscript.utilities.api.inventory.custom_items.references.APIReference;
import me.wolfyscript.utilities.compatibility.PluginIntegrationAbstract;
import me.wolfyscript.utilities.compatibility.plugins.MythicMobsIntegration;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

@WUPluginIntegration(pluginName = MythicMobsIntegration.KEY)
public class MythicMobsImpl extends PluginIntegrationAbstract implements MythicMobsIntegration {

    protected MythicMobsImpl(WolfyUtilCore core) {
        super(core, MythicMobsIntegration.KEY);
    }

    @Override
    public void init(Plugin plugin) {
        if (WolfyUtilities.hasClass("io.lumine.mythic.bukkit.MythicBukkit")) {
            core.registerAPIReference(new MythicMobs5RefImpl.Parser());
        } else {
            core.registerAPIReference(new MythicMobsRefImpl.Parser());
        }
        core.getRegistries().getStackIdentifierParsers().register(new MythicMobsStackIdentifier.Parser());
        core.getRegistries().getStackIdentifierTypeRegistry().register(MythicMobsStackIdentifier.class);
    }

    @Override
    public boolean hasAsyncLoading() {
        return false;
    }

    @Override
    public boolean isAPIReferenceIncluded(APIReference reference) {
        return reference instanceof MythicMobsRefImpl;
    }

    @Override
    public void spawnMob(String mobName, Location location, int mobLevel) {
        MythicMob mythicMob = MythicBukkit.inst().getMobManager().getMythicMob(mobName).orElse(null);
        if(mythicMob != null) {
            mythicMob.spawn(BukkitAdapter.adapt(location), mobLevel);
        }
    }
}
