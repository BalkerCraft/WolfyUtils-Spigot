package me.wolfyscript.utilities.compatibility.plugins.mmoitems;

import com.wolfyscript.utilities.bukkit.world.items.reference.ItemCreateContext;
import com.wolfyscript.utilities.bukkit.world.items.reference.StackIdentifier;
import com.wolfyscript.utilities.bukkit.world.items.reference.StackIdentifierParser;
import io.lumine.mythic.lib.api.item.NBTItem;
import me.wolfyscript.utilities.util.NamespacedKey;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Optional;

public class MMOItemsStackIdentifier implements StackIdentifier {

    public static final NamespacedKey ID = NamespacedKey.wolfyutilties("mmoitems");

    private final Type itemType;
    private final String itemName;

    public MMOItemsStackIdentifier(Type itemType, String itemName) {
        this.itemType = itemType;
        this.itemName = itemName;
    }

    @Override
    public ItemStack item(ItemCreateContext context) {
        MMOItem item = MMOItems.plugin.getMMOItem(itemType, itemName);
        return item != null ? item.newBuilder().buildSilently() : null;
    }

    @Override
    public boolean matches(ItemStack other, int count, boolean exact, boolean ignoreAmount) {
        var nbtItem = NBTItem.get(other);
        if (nbtItem.hasType()) {
            return Objects.equals(this.itemType, MMOItems.plugin.getTypes().get(nbtItem.getType())) && Objects.equals(this.itemName, nbtItem.getString("MMOITEMS_ITEM_ID"));
        }
        return false;
    }

    @Override
    public MMOItemsRefImpl convert(double weight, int amount) {
        MMOItemsRefImpl ref = new MMOItemsRefImpl(itemType, itemName);
        ref.setWeight(weight);
        ref.setAmount(amount);
        return ref;
    }

    @Override
    public NamespacedKey getNamespacedKey() {
        return ID;
    }

    public static class Parser implements StackIdentifierParser<MMOItemsStackIdentifier> {

        @Override
        public int priority() {
            return 0;
        }

        @Override
        public Optional<MMOItemsStackIdentifier> from(ItemStack itemStack) {
            NBTItem nbtItem = NBTItem.get(itemStack);
            if (nbtItem.hasType()) {
                Type type = MMOItems.plugin.getTypes().get(nbtItem.getType());
                String itemId = nbtItem.getString("MMOITEMS_ITEM_ID");
                return Optional.of(new MMOItemsStackIdentifier(type, itemId));
            }
            return Optional.empty();
        }

        @Override
        public NamespacedKey getNamespacedKey() {
            return ID;
        }
    }

}
