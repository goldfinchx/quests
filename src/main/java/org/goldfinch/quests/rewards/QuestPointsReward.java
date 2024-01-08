package org.goldfinch.quests.rewards;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class QuestPointsReward extends Reward {

    private int amount;

    @Override
    public void give(QuestPlayerData questPlayerData) {
        questPlayerData.setQuestPoints(questPlayerData.getQuestPoints() + this.amount);
    }
}
