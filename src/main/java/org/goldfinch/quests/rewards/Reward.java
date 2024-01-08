package org.goldfinch.quests.rewards;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;

@Embeddable
@NoArgsConstructor
public abstract class Reward {

    //todo remove
    private String test;

    public void give(QuestPlayerData questPlayerData) {

    }

}
