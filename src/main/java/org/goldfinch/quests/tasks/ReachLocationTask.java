package org.goldfinch.quests.tasks;


import org.bukkit.event.player.PlayerMoveEvent;
import org.goldfinch.quests.player.QuestPlayerData;

public class ReachLocationTask extends Task<PlayerMoveEvent> {

    private int radius;

    @Override
    public boolean check(PlayerMoveEvent event, QuestPlayerData playerData) {
        super.check(event, playerData);

        return true;
    }
}
