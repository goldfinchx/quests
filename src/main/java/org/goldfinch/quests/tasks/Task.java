package org.goldfinch.quests.tasks;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.event.Event;
import org.goldfinch.quests.configs.MessagesConfig;
import org.goldfinch.quests.libs.storages.core.DataObject;
import org.goldfinch.quests.player.entity.QuestPlayerData;

@Getter
@Entity
@Table(name = "tasks")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Task<E extends Event> extends DataObject<Long> {

    @Enumerated(value = EnumType.STRING)
    private MessagesConfig.Message title;
    private int target;

    public abstract int tryProgress(E event, QuestPlayerData playerData);

    public abstract Component toComponent(QuestPlayerData playerData, ActiveTask activeTask);

}
