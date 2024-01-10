package org.goldfinch.quests.tasks;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.event.Event;
import org.goldfinch.quests.quest.entity.ActiveQuest;


@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ActiveTask {

    @ManyToOne
    private ActiveQuest activeQuest;

    @ManyToOne
    private Task task;

    private int progress;

    public ActiveTask(ActiveQuest activeQuest, Task task) {
        this.activeQuest = activeQuest;
        this.task = task;
        this.progress = 0;
    }

    public void tryProgress(Event event) {
        final int progressChange = this.task.tryProgress(event, this.activeQuest.getPlayerData());
        this.setProgress(this.getProgress() + progressChange);

        if (this.getProgress() < this.getTask().getTarget()) {
            return;
        }

        this.activeQuest.completeTask(this);
    }

}
