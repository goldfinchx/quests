package org.goldfinch.quests.conditions.impl;

import jakarta.persistence.Entity;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.conditions.Condition;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class QuestLogicCondition extends Condition {

    private boolean isCancelable;

}
