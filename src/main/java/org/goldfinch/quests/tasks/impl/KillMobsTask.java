package org.goldfinch.quests.tasks.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.language.MessagesConfig;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.quest.entity.Quest;
import org.goldfinch.quests.tasks.ActiveTask;
import org.goldfinch.quests.tasks.Task;

@Entity
@NoArgsConstructor
public class KillMobsTask extends Task<EntityDeathEvent> {

    @Enumerated(value = EnumType.STRING)
    private EntityType entityType;
    
    public KillMobsTask(EntityType entityType, int amount) {
        super(MessagesConfig.Message.TASK_KILL_MOBS, amount);
        this.entityType = entityType;
    }

    @Override
    public int tryProgress(EntityDeathEvent event, QuestPlayerData playerData) {
        if (event.getEntityType() != this.entityType) {
            return 0;
        }

        return 1;
    }

    @Override
    public Component toComponent(QuestPlayerData playerData, ActiveTask activeTask) {
        final MessagesConfig messagesConfig = Quests.getInstance().getMessagesConfig();
        return messagesConfig.get(this.getTitle(), playerData.getLanguage(),
            "%target%", activeTask.getProgress(),
            "%entity_type%", this.entityType.name().toLowerCase());
    }
}
