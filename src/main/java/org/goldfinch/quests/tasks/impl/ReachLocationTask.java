package org.goldfinch.quests.tasks.impl;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.tasks.Task;
import org.goldfinch.quests.wrappers.impl.LocationWrapper;

@Entity
@NoArgsConstructor
public class ReachLocationTask extends Task<PlayerMoveEvent> {

    @OneToOne
    private LocationWrapper location;
    private int radius;

    public ReachLocationTask(Location location, int radius) {
        this.location = new LocationWrapper(location);
        this.radius = radius;
    }

    @Override
    public int tryProgress(PlayerMoveEvent event, QuestPlayerData playerData) {
        if (event.getTo().distance(this.location.unwrap()) > this.radius) {
            return 0;
        }

        return 1;
    }
}
