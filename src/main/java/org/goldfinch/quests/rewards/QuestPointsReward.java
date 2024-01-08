package org.goldfinch.quests.rewards;

import lombok.AllArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;

@AllArgsConstructor
public class QuestPointsReward extends Reward {

    private final int amount;

    @Override
    public void give(QuestPlayerData questPlayerData) {
        questPlayerData.setQuestPoints(questPlayerData.getQuestPoints() + this.amount);
    }
}
