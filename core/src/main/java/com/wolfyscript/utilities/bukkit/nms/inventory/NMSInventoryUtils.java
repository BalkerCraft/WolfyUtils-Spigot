package com.wolfyscript.utilities.bukkit.nms.inventory;

import me.wolfyscript.utilities.util.NamespacedKey;
import me.wolfyscript.utilities.util.version.MinecraftVersions;
import me.wolfyscript.utilities.util.version.ServerVersion;
import org.bukkit.inventory.Inventory;

public class NMSInventoryUtils {

    @Deprecated(forRemoval = true, since = "4.17")
    public static void setCurrentRecipe(Inventory inventory, NamespacedKey recipeId) {
        // skip on latest versions
    }


}
