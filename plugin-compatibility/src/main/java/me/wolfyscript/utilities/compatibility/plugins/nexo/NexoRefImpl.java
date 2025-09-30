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

package me.wolfyscript.utilities.compatibility.plugins.nexo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nexomc.nexo.api.NexoItems;
import me.wolfyscript.utilities.api.inventory.custom_items.references.APIReference;
import me.wolfyscript.utilities.util.inventory.ItemUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Objects;

/**
 * Links to Nexo and saves the specified id of the item.
 */
public class NexoRefImpl extends APIReference implements NexoRef {

    private final String itemID;

    public NexoRefImpl(String itemID) {
        super();
        this.itemID = itemID;
    }

    public NexoRefImpl(NexoRefImpl nexoRefImpl) {
        super(nexoRefImpl);
        this.itemID = nexoRefImpl.itemID;
    }

    @Override
    public ItemStack getLinkedItem() {
        if (NexoItems.exists(itemID)) {
            return NexoItems.itemFromId(itemID).build();
        }
        return ItemUtils.AIR;
    }

    @Override
    public ItemStack getIdItem() {
        return getLinkedItem();
    }

    @Override
    public boolean isValidItem(ItemStack itemStack) {
        String itemId = NexoItems.idFromItem(itemStack);
        if (itemId != null && !itemId.isEmpty()) {
            return Objects.equals(this.itemID, itemId);
        }
        return false;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("nexo", itemID);
    }

    @Override
    public String getItemID() {
        return itemID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NexoRefImpl nexoRefImpl)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(itemID, nexoRefImpl.itemID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), itemID);
    }

    @Override
    protected NexoStackIdentifier convert() {
        return new NexoStackIdentifier(itemID);
    }

    @Override
    public NexoRefImpl clone() {
        return new NexoRefImpl(this);
    }

    public static class Parser extends PluginParser<NexoRefImpl> {

        public Parser() {
            super("Nexo", "nexo");
        }

        @Override
        public @Nullable NexoRefImpl construct(ItemStack itemStack) {
            String itemId = NexoItems.idFromItem(itemStack);
            if (itemId != null && !itemId.isEmpty()) {
                return new NexoRefImpl(itemId);
            }
            return null;
        }

        @Override
        public @Nullable NexoRefImpl parse(JsonNode element) {
            return new NexoRefImpl(element.asText());
        }
    }
}
