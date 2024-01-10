package org.goldfinch.quests.conditions.impl;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.conditions.Condition;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartingCondition extends Condition {

    private boolean viaCommand;
    private boolean viaNPC;
    private boolean viaMenu;

}
