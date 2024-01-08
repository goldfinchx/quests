package org.goldfinch.quests;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.data.core.DataObject;
import org.goldfinch.quests.player.QuestPlayerData;
import org.goldfinch.quests.quest.Quest;

@Data
@Entity
@Table(name = "active_quests")
@NoArgsConstructor
public class ActiveQuest extends DataObject<Long> {

    @ManyToOne
    private QuestPlayerData playerData;

    @ManyToOne
    private Quest quest;

    @ElementCollection
    @JoinTable(name = "active_quests_tasks")
    private List<ActiveTask> activeTasks = new ArrayList<>();

    public ActiveQuest(Quest quest) {
        this.quest = quest;
        this.activeTasks = quest.getTasks().stream()
            .map(task -> new ActiveTask(this, task))
            .toList();
    }

    public void completeTask(ActiveTask activeTask) {
        this.activeTasks.remove(activeTask);

        if (!this.activeTasks.isEmpty()) {
            return;
        }

        this.playerData.completeQuest(this);
    }

}
