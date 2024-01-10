package org.goldfinch.quests.conditions.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.conditions.Condition;
import org.goldfinch.quests.player.entity.QuestPlayerData;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TasksLogicCondition extends Condition {

    @Enumerated(value = EnumType.STRING)
    private Logic logic;

    public enum Logic {
        PARALLEL,
        SEQUENTIAL,
        RANDOM
    }


}
