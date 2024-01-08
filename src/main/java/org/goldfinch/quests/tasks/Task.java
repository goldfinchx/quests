package org.goldfinch.quests.tasks;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.event.Event;
import org.goldfinch.quests.player.QuestPlayerData;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Task<E extends Event> {

    private int target;

    public boolean check(E event, QuestPlayerData playerData) {
        return true;
    }

}
