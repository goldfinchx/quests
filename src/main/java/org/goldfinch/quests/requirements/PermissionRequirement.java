package org.goldfinch.quests.requirements;

import org.goldfinch.quests.player.QuestPlayerData;

public class PermissionRequirement extends Requirement {

    private String permission;

    public PermissionRequirement(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean hasMet(QuestPlayerData questPlayerData) {
        return questPlayerData.getPlayer().hasPermission(this.permission);
    }
}
