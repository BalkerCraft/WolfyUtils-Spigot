package me.wolfyscript.utilities.compatibility.plugins.helixitems;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.wolfyscript.utilities.KeyedStaticId;
import com.wolfyscript.utilities.bukkit.dependency.PluginIntegrationDependencyResolver;
import com.wolfyscript.utilities.bukkit.dependency.PluginIntegrationDependencyResolverSettings;
import com.wolfyscript.utilities.bukkit.world.items.reference.ItemCreateContext;
import com.wolfyscript.utilities.bukkit.world.items.reference.LegacyParser;
import com.wolfyscript.utilities.bukkit.world.items.reference.StackIdentifier;
import com.wolfyscript.utilities.bukkit.world.items.reference.StackIdentifierParser;
import com.wolfyscript.utilities.dependency.DependencyResolverSettings;
import hu.kamillplayz.helixitems.HelixItems;
import hu.kamillplayz.helixitems.data.KeyRegistry;
import me.wolfyscript.utilities.util.NamespacedKey;
import me.wolfyscript.utilities.util.inventory.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

@KeyedStaticId(key = "helixitems")
@DependencyResolverSettings(PluginIntegrationDependencyResolver.class)
@PluginIntegrationDependencyResolverSettings(pluginName = HelixItemImpl.PLUGIN_NAME, integration = HelixItemImpl.class)
public class HelixItemStackIdentifier implements StackIdentifier {

    public static final NamespacedKey ID = NamespacedKey.wolfyutilties("helixitems");

    private final String itemId;

    @JsonCreator
    public HelixItemStackIdentifier(@JsonProperty("id") String itemId) {
        this.itemId = itemId;
    }

    @JsonGetter("id")
    public String itemId() {
        return itemId;
    }

    @Override
    public ItemStack stack(ItemCreateContext context) {
        var customStack = HelixItems.getInstance().getPresetItemsManager().getPresetItemConfig(itemId).build(itemId);
        if (customStack != null) {
            customStack.setAmount(context.amount());
            return customStack;
        }
        return customStack;
    }

    @Override
    public boolean matchesIgnoreCount(ItemStack other, boolean exact) {
        if (ItemUtils.isAirOrNull(other)) return false;
        if (!other.hasItemMeta()) return false;

        ItemMeta itemMeta = other.getItemMeta();
        String id = itemMeta.getPersistentDataContainer().get(KeyRegistry.INTERNAL_ID_KEY, PersistentDataType.STRING);

        var customStack = HelixItems.getInstance().getPresetItemsManager().getPresetItemConfig(itemId).build(itemId);

        if (customStack == null) {
            return false;
        }

        return itemId.equalsIgnoreCase(id);
    }

    @Override
    public HelixItemRefImpl convert(double weight, int amount) {
        HelixItemRefImpl ref = new HelixItemRefImpl(itemId);
        ref.setWeight(weight);
        ref.setAmount(amount);
        return ref;
    }

    @Override
    public NamespacedKey getNamespacedKey() {
        return ID;
    }

    public static class Parser implements StackIdentifierParser<HelixItemStackIdentifier>, LegacyParser<HelixItemStackIdentifier> {

        @Override
        public int priority() {
            return 2000;
        }

        @Override
        public Optional<HelixItemStackIdentifier> from(ItemStack itemStack) {
            if (!itemStack.hasItemMeta()) return Optional.empty();

            ItemMeta itemMeta = itemStack.getItemMeta();
            String itemId = itemMeta.getPersistentDataContainer().get(KeyRegistry.INTERNAL_ID_KEY, PersistentDataType.STRING);
            if (itemId == null) return Optional.empty();

            if (HelixItems.getInstance().getPresetItemsManager().getPresetItems().contains(itemId)) {
                return Optional.of(new HelixItemStackIdentifier(itemId));
            }
            return Optional.empty();
        }

        @Override
        public NamespacedKey getNamespacedKey() {
            return ID;
        }

        @Override
        public DisplayConfiguration displayConfig() {
            return new DisplayConfiguration.SimpleDisplayConfig(
                Component.text("Helix Item").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD),
                new DisplayConfiguration.MaterialIconSettings(Material.BLAZE_POWDER)
            );
        }

        @Override
        public Optional<HelixItemStackIdentifier> from(JsonNode legacyData) {
            return Optional.of(new HelixItemStackIdentifier(legacyData.asText()));
        }
    }

}