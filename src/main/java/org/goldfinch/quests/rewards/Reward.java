package org.goldfinch.quests.rewards;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.data.core.DataObject;
import org.goldfinch.quests.player.QuestPlayerData;

@Getter
@Entity
@Table(name = "rewards")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Reward extends DataObject<Long> {

    @Transient
    private String title;

    @Transient //todo config
    private String failureMessage;

    public void give(QuestPlayerData questPlayerData) {}

}
