package org.goldfinch.quests.requirements;

import jakarta.persistence.Entity;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;
import org.goldfinch.quests.quest.Quest;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CompletedQuestsRequirement extends Requirement {

    private List<Long> completedQuests;

    @Override
    public boolean hasMet(QuestPlayerData questPlayerData) {
       return questPlayerData.getCompletedQuests().stream()
           .map(Quest::getId)
           .collect(Collectors.toSet())
           .containsAll(new HashSet<>(this.completedQuests));
    }
}
