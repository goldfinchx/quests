package org.goldfinch.quests;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.event.Event;
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

    @ManyToOne
    private Task task;

    private int progress;

    public ActiveTask(ActiveQuest activeQuest, Task task) {
        this.activeQuest = activeQuest;
        this.task = task;
        this.progress = 0;
    }

    public void check(Event event) {
        final int progressChange = this.task.checkProgress(event, this.playerData);
        this.setProgress(this.getProgress() + progressChange);

        if (this.getProgress() < this.getTask().getTarget()) {
            return;
        }

        this.activeQuest.completeTask(this);
    }

}
