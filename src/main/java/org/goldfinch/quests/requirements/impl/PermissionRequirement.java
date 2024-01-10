package org.goldfinch.quests.requirements.impl;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.requirements.Requirement;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequirement extends Requirement {

    private String permission;

    @Override
    public boolean hasMet(QuestPlayerData questPlayerData) {
        return questPlayerData.getBukkitPlayer().hasPermission(this.permission);
    }
}
