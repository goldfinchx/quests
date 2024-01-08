package org.goldfinch.quests.requirements;

import java.util.HashSet;
import java.util.List;
import lombok.AllArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;
import org.goldfinch.quests.quest.Quest;

@AllArgsConstructor
public class CompletedQuestsRequirement extends Requirement {

    private List<Quest> completedQuests;

    @Override
    public boolean hasMet(QuestPlayerData questPlayerData) {
        return new HashSet<>(questPlayerData.getCompletedQuests()).containsAll(this.completedQuests);
    }
}
