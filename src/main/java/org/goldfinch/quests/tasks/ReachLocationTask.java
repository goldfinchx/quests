package org.goldfinch.quests.tasks;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.goldfinch.quests.player.QuestPlayerData;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ReachLocationTask extends Task<PlayerMoveEvent> {

    private Location location;
    private int radius;

    @Override
    public int checkProgress(PlayerMoveEvent event, QuestPlayerData playerData) {
        if (event.getTo().distance(this.location) > this.radius) {
            return 0;
        }

        return 1;
    }
}
