package org.goldfinch.quests.wrappers.impl;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.goldfinch.quests.data.core.DataObject;
import org.goldfinch.quests.wrappers.Wrapper;

@Getter
@Entity
@Table(name = "itemstacks")
@AllArgsConstructor
@NoArgsConstructor
public class ItemStackWrapper extends DataObject<Long> implements Wrapper<ItemStack> {

    private String title;

    @ElementCollection
    @JoinTable(name = "itemstacks_lore")
    private List<String> lore;

    @Enumerated(value = EnumType.STRING)
    private Material material;

    @ElementCollection
    @MapKeyColumn(name = "enchantment_name")
    @JoinTable(name = "itemstacks_enchantments")
    private Map<String, Integer> enchantments;

    private int customModelData;

    private int amount;

    private byte[] persistentDataContainer;

    public ItemStackWrapper(ItemStack itemStack) {
        this.material = itemStack.getType();
        this.amount = itemStack.getAmount();
        this.enchantments = itemStack.getEnchantments().entrySet().stream()
            .collect(Collectors.toMap(entry -> entry.getKey().getKey().getKey(), Map.Entry::getValue));

        if (itemStack.hasItemMeta()) {
            final ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta.hasDisplayName()) {
                this.title = MiniMessage.miniMessage().serialize(itemMeta.displayName());
            }

            if (itemMeta.hasLore()) {
                this.lore = itemMeta.lore().stream().map(MiniMessage.miniMessage()::serialize).toList();
            }

            if (itemMeta.hasCustomModelData()) {
                this.customModelData = itemMeta.getCustomModelData();
            }

            try {
                this.persistentDataContainer = itemMeta.getPersistentDataContainer().serializeToBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public ItemStack unwrap() {
        final ItemStack itemStack = new ItemStack(this.material, this.amount);
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (this.title != null) {
            itemMeta.displayName(MiniMessage.miniMessage().deserialize(this.title));
        }

        if (this.lore != null) {
            itemMeta.lore(this.lore.stream().map(MiniMessage.miniMessage()::deserialize).toList());
        }

        if (this.customModelData != 0) {
            itemMeta.setCustomModelData(this.customModelData);
        }

        if (this.persistentDataContainer != null) {
            try {
                itemMeta.getPersistentDataContainer().readFromBytes(this.persistentDataContainer, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (this.enchantments != null) {
            this.enchantments.forEach((enchantment, level) -> itemStack.addUnsafeEnchantment(Enchantment.getByName(enchantment), level));
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
