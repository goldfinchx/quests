package org.goldfinch.quests.requirements;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LevelRequirement extends Requirement {

    private int requiredLevel;

    @Override
    public boolean hasMet(QuestPlayerData questPlayerData) {
        return questPlayerData.getLevel() >= this.requiredLevel;
    }
}
