package org.goldfinch.quests.rewards;

import lombok.AllArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;

@AllArgsConstructor
public class ExperienceReward extends Reward {

    private final int amount;

    @Override
    public void give(QuestPlayerData questPlayerData) {

    }
}
