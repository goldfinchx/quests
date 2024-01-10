package org.goldfinch.quests.conditions.impl;

import jakarta.persistence.Entity;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.conditions.Condition;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.quest.entity.Quest;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DeadlineCondition extends Condition {

    private Timestamp deadline;

}
