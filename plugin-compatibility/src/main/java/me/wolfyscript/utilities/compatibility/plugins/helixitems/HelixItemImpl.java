package me.wolfyscript.utilities.compatibility.plugins.helixitems;

import me.wolfyscript.utilities.annotations.WUPluginIntegration;
import me.wolfyscript.utilities.api.WolfyUtilCore;
import me.wolfyscript.utilities.compatibility.PluginIntegrationAbstract;
import org.bukkit.plugin.Plugin;

@WUPluginIntegration(pluginName = HelixItemImpl.PLUGIN_NAME)
public class HelixItemImpl extends PluginIntegrationAbstract {

    static final String PLUGIN_NAME = "HelixItems";

    public HelixItemImpl(WolfyUtilCore core) {
        super(core, PLUGIN_NAME);
    }

    @Override
    public void init(Plugin plugin) {
        core.registerAPIReference(new HelixItemRefImpl.Parser());
        core.getRegistries().getStackIdentifierParsers().register(new HelixItemStackIdentifier.Parser());
        core.getRegistries().getStackIdentifierTypeRegistry().register(HelixItemStackIdentifier.class);
    }

    @Override
    public boolean hasAsyncLoading() {
        return false;
    }
}
