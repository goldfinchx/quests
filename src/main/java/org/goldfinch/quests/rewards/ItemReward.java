package org.goldfinch.quests.rewards;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.goldfinch.quests.player.QuestPlayerData;
import org.goldfinch.quests.wrappers.ItemStackWrapper;


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
        questPlayerData.getPlayer().getInventory().addItem(this.itemStack.unwrap());
    }
}
