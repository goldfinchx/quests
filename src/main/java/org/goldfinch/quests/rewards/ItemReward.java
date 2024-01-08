package org.goldfinch.quests.rewards;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.goldfinch.quests.player.QuestPlayerData;


@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ItemReward extends Reward {

    private ItemStack itemStack;

    @Override
    public void give(QuestPlayerData questPlayerData) {
        questPlayerData.getPlayer().getInventory().addItem(this.itemStack);
    }
}
