package org.goldfinch.quests.tasks.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.NoArgsConstructor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.tasks.Task;

@Entity
@NoArgsConstructor
public class KillMobsTask extends Task<EntityDeathEvent> {

    @Enumerated(value = EnumType.STRING)
    private EntityType entityType;
    
    public KillMobsTask(EntityType entityType, int amount) {
        super(amount);
        this.entityType = entityType;
    }

    @Override
    public int tryProgress(EntityDeathEvent event, QuestPlayerData playerData) {
        if (event.getEntityType() != this.entityType) {
            return 0;
        }

        return 1;
    }
}
