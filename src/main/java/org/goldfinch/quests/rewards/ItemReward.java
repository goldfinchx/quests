package org.goldfinch.quests.rewards;

import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.goldfinch.quests.player.QuestPlayerData;


@AllArgsConstructor
public class ItemReward extends Reward {

    private final ItemStack itemStack;

    @Override
    public void give(QuestPlayerData questPlayerData) {
        questPlayerData.getPlayer().getInventory().addItem(this.itemStack);
    }
}
