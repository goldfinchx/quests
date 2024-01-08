package org.goldfinch.quests.tasks;

import lombok.AllArgsConstructor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.goldfinch.quests.player.QuestPlayerData;

@AllArgsConstructor
public class KillMobsTask extends Task<EntityDeathEvent> {

    private final EntityType entityType;
    private final int amount;

    @Override
    public boolean check(EntityDeathEvent event, QuestPlayerData playerData) {
        super.check(event, playerData);

        return true;
    }
}
