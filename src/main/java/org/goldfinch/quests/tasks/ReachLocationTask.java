package org.goldfinch.quests.tasks;


import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.goldfinch.quests.player.QuestPlayerData;

@AllArgsConstructor
public class ReachLocationTask extends Task<PlayerMoveEvent> {

    private Location location;
    private int radius;

    @Override
    public int check(PlayerMoveEvent event, QuestPlayerData playerData) {
        super.check(event, playerData);

        if (event.getTo().distance(this.location) > this.radius) {
            return 0;
        }

        return 1;
    }
}
