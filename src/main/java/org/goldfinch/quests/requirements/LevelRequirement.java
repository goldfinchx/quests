package org.goldfinch.quests.requirements;

import lombok.AllArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;

@AllArgsConstructor
public class LevelRequirement extends Requirement {

    private int requiredLevel;

    @Override
    public boolean hasMet(QuestPlayerData questPlayerData) {
        return questPlayerData.getLevel() >= this.requiredLevel;
    }
}
