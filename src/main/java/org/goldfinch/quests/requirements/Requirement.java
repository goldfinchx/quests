package org.goldfinch.quests.requirements;


import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import org.goldfinch.quests.player.QuestPlayerData;

@Embeddable
@MappedSuperclass
public class Requirement {

    private String name;

    public boolean hasMet(QuestPlayerData questPlayerData) {
        return true;
    }

}
