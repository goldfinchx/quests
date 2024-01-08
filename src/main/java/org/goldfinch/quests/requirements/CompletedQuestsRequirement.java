package org.goldfinch.quests.requirements;

import java.util.List;
import lombok.AllArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;

@AllArgsConstructor
public class CompletedQuestsRequirement extends Requirement {

    private List<Long> completedQuests;

    @Override
    public boolean hasMet(QuestPlayerData questPlayerData) {
        return true;
    }
}
