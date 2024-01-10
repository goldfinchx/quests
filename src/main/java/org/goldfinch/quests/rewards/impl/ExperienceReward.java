package org.goldfinch.quests.rewards.impl;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.rewards.Reward;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceReward extends Reward {

    private int amount;

    @Override
    public void give(QuestPlayerData questPlayerData) {
        questPlayerData.getBukkitPlayer().giveExp(this.amount);
    }
}
