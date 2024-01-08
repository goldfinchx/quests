package org.goldfinch.quests;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.event.Event;
import org.goldfinch.quests.data.core.DataObject;
import org.goldfinch.quests.player.QuestPlayerData;
import org.goldfinch.quests.tasks.Task;


@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ActiveTask {

    @ManyToOne
    private QuestPlayerData playerData;

    @ManyToOne
    private ActiveQuest activeQuest;

    private Task task;
    private int progress;

    public ActiveTask(ActiveQuest activeQuest, Task task) {
        this.activeQuest = activeQuest;
        this.task = task;
        this.progress = 0;
    }

    public void check(Event event) {
        final int changeResult = this.task.check(event, this.playerData);
        this.setProgress(this.getProgress() + changeResult);

        if (this.getProgress() >= this.getTask().getTarget()) {
            this.complete();
        }
    }

    private void complete() {
        this.activeQuest.getActiveTasks().remove(this);
    }

}
