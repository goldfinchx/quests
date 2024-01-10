package org.goldfinch.quests.rewards.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.NoArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.rewards.Reward;
import org.goldfinch.quests.wrappers.impl.ItemStackWrapper;


@Entity
@NoArgsConstructor
public class ItemReward extends Reward {

    @OneToOne
    private ItemStackWrapper itemStack;

    public ItemReward(ItemStack itemStack) {
        this.itemStack = new ItemStackWrapper(itemStack);
    }

    @Override
    public void give(QuestPlayerData questPlayerData) {
        questPlayerData.getBukkitPlayer().getInventory().addItem(this.itemStack.unwrap());
    }
}
