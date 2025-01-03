package me.wolfyscript.utilities.compatibility.plugins.helixitems;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import hu.kamillplayz.helixitems.HelixItems;
import hu.kamillplayz.helixitems.data.KeyRegistry;
import me.wolfyscript.utilities.api.inventory.custom_items.references.APIReference;
import me.wolfyscript.utilities.util.inventory.ItemUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.io.IOException;

public class HelixItemRefImpl extends APIReference {

    private final String itemID;

    public HelixItemRefImpl(String itemID) {
        super();
        this.itemID = itemID;
    }

    public HelixItemRefImpl(HelixItemRefImpl craftItemRefImpl) {
        super(craftItemRefImpl);
        this.itemID = craftItemRefImpl.itemID;
    }

    /**
     * @return The latest ItemStack available in HelixItems under the specified itemID or AIR.
     */
    @Override
    public ItemStack getLinkedItem() {
        var customStack = HelixItems.getInstance().getPresetItemsManager().getPresetItemConfig(itemID).build(itemID);
        if (customStack != null) {
            return customStack;
        }
        return ItemUtils.AIR;
    }

    @Override
    public boolean isValidItem(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) return false;

        ItemMeta itemMeta = itemStack.getItemMeta();
        String itemId = itemMeta.getPersistentDataContainer().get(KeyRegistry.INTERNAL_ID_KEY, PersistentDataType.STRING);
        return itemId != null;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("helixitems", itemID);
    }

    @Override
    public APIReference clone() {
        return new HelixItemRefImpl(this);
    }

    @Override
    protected HelixItemStackIdentifier convert() {
        return new HelixItemStackIdentifier(itemID);
    }

    public static class Parser extends PluginParser<HelixItemRefImpl> {

        public Parser() {
            super("HelixItems", "helixitems");
        }

        @Override
        public @Nullable HelixItemRefImpl construct(ItemStack itemStack) {
            if (!itemStack.hasItemMeta()) return null;

            ItemMeta itemMeta = itemStack.getItemMeta();
            String itemId = itemMeta.getPersistentDataContainer().get(KeyRegistry.INTERNAL_ID_KEY, PersistentDataType.STRING);
            if (itemId == null) return null;

            return new HelixItemRefImpl(itemId);
        }

        @Override
        public @Nullable HelixItemRefImpl parse(JsonNode element) {
            return new HelixItemRefImpl(element.asText());
        }
    }
}