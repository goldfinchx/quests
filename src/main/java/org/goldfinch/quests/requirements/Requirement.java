package org.goldfinch.quests.requirements;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;

@Getter
@Embeddable
@NoArgsConstructor
public class Requirement {

    private String test;

    public boolean hasMet(QuestPlayerData questPlayerData) {
        return true;
    }

}
