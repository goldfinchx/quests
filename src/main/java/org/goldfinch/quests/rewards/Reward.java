package org.goldfinch.quests.rewards;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public abstract class Reward {

    @Transient
    private String title;

    @Transient //todo config
    private String failureMessage;

    public void give(QuestPlayerData questPlayerData) {}

}
