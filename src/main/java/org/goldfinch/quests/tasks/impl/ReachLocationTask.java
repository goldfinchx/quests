package org.goldfinch.quests.tasks.impl;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.configs.MessagesConfig;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.tasks.ActiveTask;
import org.goldfinch.quests.tasks.Task;
import org.goldfinch.quests.libs.storages.wrappers.impl.LocationWrapper;

@Entity
@NoArgsConstructor
public class ReachLocationTask extends Task<PlayerMoveEvent> {

    @OneToOne
    private LocationWrapper location;
    private int radius;

    public ReachLocationTask(Location location, int radius) {
        super(MessagesConfig.Message.TASK_REACH_LOCATION, 1);
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

    @Override
    public Component toComponent(QuestPlayerData playerData, ActiveTask activeTask) {
        final MessagesConfig messagesConfig = Quests.getInstance().getMessagesConfig();
        return messagesConfig.get(this.getTitle(), playerData.getLanguage(),
            "%location%", this.location.getX() + ", " + this.location.getY() + ", " + this.location.getZ(),
            "%radius%", String.valueOf(this.radius));
    }

}
