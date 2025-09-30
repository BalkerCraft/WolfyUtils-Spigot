package me.wolfyscript.utilities.compatibility.plugins.nexo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.nexomc.nexo.api.NexoItems;
import com.wolfyscript.utilities.KeyedStaticId;
import com.wolfyscript.utilities.bukkit.dependency.PluginIntegrationDependencyResolver;
import com.wolfyscript.utilities.bukkit.dependency.PluginIntegrationDependencyResolverSettings;
import com.wolfyscript.utilities.bukkit.world.items.reference.ItemCreateContext;
import com.wolfyscript.utilities.bukkit.world.items.reference.LegacyParser;
import com.wolfyscript.utilities.bukkit.world.items.reference.StackIdentifier;
import com.wolfyscript.utilities.bukkit.world.items.reference.StackIdentifierParser;
import com.wolfyscript.utilities.dependency.DependencyResolverSettings;
import me.wolfyscript.utilities.compatibility.plugins.NexoIntegration;
import me.wolfyscript.utilities.util.NamespacedKey;
import me.wolfyscript.utilities.util.inventory.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Optional;

@KeyedStaticId(key = "nexo")
@DependencyResolverSettings(PluginIntegrationDependencyResolver.class)
@PluginIntegrationDependencyResolverSettings(pluginName = NexoIntegration.KEY, integration = NexoIntegration.class)
public class NexoStackIdentifier implements StackIdentifier {

    public static final NamespacedKey ID = NamespacedKey.wolfyutilties("nexo");

    private final String itemID;

    @JsonCreator
    public NexoStackIdentifier(@JsonProperty("item") String itemID) {
        this.itemID = itemID;
    }

    @JsonGetter("item")
    public String itemId() {
        return itemID;
    }

    @Override
    public ItemStack stack(ItemCreateContext context) {
        if (NexoItems.exists(itemID)) {
            ItemStack stack = NexoItems.itemFromId(itemID).build();
            stack.setAmount(context.amount());
            return stack;
        }
        return ItemUtils.AIR;
    }

    @Override
    public boolean matchesIgnoreCount(ItemStack other, boolean exact) {
        String itemId = NexoItems.idFromItem(other);
        if (itemId != null && !itemId.isEmpty()) {
            return Objects.equals(this.itemID, itemId);
        }
        return false;
    }

    @Override
    public NexoRefImpl convert(double weight, int amount) {
        NexoRefImpl ref = new NexoRefImpl(itemID);
        ref.setWeight(weight);
        ref.setAmount(amount);
        return ref;
    }

    @Override
    public NamespacedKey getNamespacedKey() {
        return ID;
    }

    public static class Parser implements StackIdentifierParser<NexoStackIdentifier>, LegacyParser<NexoStackIdentifier> {

        @Override
        public int priority() {
            return 1900;
        }

        @Override
        public Optional<NexoStackIdentifier> from(ItemStack itemStack) {
            String itemId = NexoItems.idFromItem(itemStack);
            if (itemId != null && !itemId.isEmpty()) {
                return Optional.of(new NexoStackIdentifier(itemId));
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
                    Component.text("Nexo").color(NamedTextColor.DARK_AQUA).decorate(TextDecoration.BOLD),
                    new DisplayConfiguration.MaterialIconSettings(Material.DIAMOND)
            );
        }

        @Override
        public Optional<NexoStackIdentifier> from(JsonNode legacyData) {
            return Optional.of(new NexoStackIdentifier(legacyData.asText()));
        }
    }

}
