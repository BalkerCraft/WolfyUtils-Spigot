//package me.wolfyscript.utilities.compatibility.plugins.executableitems;
//
//import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
//import me.wolfyscript.utilities.annotations.WUPluginIntegration;
//import me.wolfyscript.utilities.api.WolfyUtilCore;
//import me.wolfyscript.utilities.api.inventory.custom_items.references.APIReference;
//import me.wolfyscript.utilities.compatibility.PluginIntegrationAbstract;
//import me.wolfyscript.utilities.compatibility.plugins.ExecutableItemsIntegration;
//import org.bukkit.plugin.Plugin;
//
//import java.util.List;
//
//@WUPluginIntegration(pluginName = ExecutableItemsIntegration.PLUGIN_NAME)
//public class ExecutableItemsImpl extends PluginIntegrationAbstract implements ExecutableItemsIntegration {
//
//    /**
//     * The main constructor that is called whenever the integration is created.<br>
//     *
//     * @param core       The WolfyUtilCore.
//     */
//    protected ExecutableItemsImpl(WolfyUtilCore core) {
//        super(core, ExecutableItemsIntegration.PLUGIN_NAME);
//    }
//
//    @Override
//    public boolean isAPIReferenceIncluded(APIReference reference) {
//        return reference instanceof ExecutableItemsRef;
//    }
//
//    @Override
//    public void init(Plugin plugin) {
//        core.registerAPIReference(new ExecutableItemsRef.Parser(ExecutableItemsAPI.getExecutableItemsManager()));
//        core.getRegistries().getStackIdentifierParsers().register(new ExecutableItemsStackIdentifier.Parser(ExecutableItemsAPI.getExecutableItemsManager()));
//        core.getRegistries().getStackIdentifierTypeRegistry().register(ExecutableItemsStackIdentifier.class);
//    }
//
//    @Override
//    public boolean hasAsyncLoading() {
//        return false;
//    }
//
//    @Override
//    public boolean isValidID(String id) {
//        return ExecutableItemsAPI.getExecutableItemsManager().isValidID(id);
//    }
//
//    @Override
//    public List<String> getExecutableItemIdsList() {
//        return ExecutableItemsAPI.getExecutableItemsManager().getExecutableItemIdsList();
//    }
//}
