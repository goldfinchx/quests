package org.goldfinch.quests.tasks;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.event.Event;
import org.goldfinch.quests.libs.storages.core.DataObject;
import org.goldfinch.quests.player.entity.QuestPlayerData;

@Getter
@Entity
@Table(name = "tasks")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Task<E extends Event> extends DataObject<Long> {

    private int target;

    public abstract int tryProgress(E event, QuestPlayerData playerData);

}
