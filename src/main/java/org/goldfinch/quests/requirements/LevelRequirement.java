package org.goldfinch.quests.requirements;

import org.goldfinch.quests.player.QuestPlayerData;

public class LevelRequirement extends Requirement {

    private final int requiredLevel;

    public LevelRequirement(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    @Override
    public boolean hasMet(QuestPlayerData questPlayerData) {
        return questPlayerData.getLevel() >= this.requiredLevel;
    }
}
