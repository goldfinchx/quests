package org.goldfinch.quests.requirements;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequirement extends Requirement {

    private String permission;

    @Override
    public boolean hasMet(QuestPlayerData questPlayerData) {
        return questPlayerData.getPlayer().hasPermission(this.permission);
    }
}
