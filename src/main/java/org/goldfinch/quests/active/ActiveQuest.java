package org.goldfinch.quests.active;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.data.core.DataObject;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.quest.entity.Quest;

@Getter
@Entity
@Table(name = "active_quests")
@NoArgsConstructor
public class ActiveQuest extends DataObject<Long> {

    @ManyToOne(cascade = CascadeType.ALL)
    private QuestPlayerData playerData;

    @ManyToOne
    private Quest quest;

    @ElementCollection
    @JoinTable(name = "active_quests_tasks")
    private List<ActiveTask> activeTasks;

    public ActiveQuest(Quest quest, QuestPlayerData playerData) {
        this.setId(playerData.getActiveQuests().size() + 1L);
        this.quest = quest;
        this.playerData = playerData;
        this.activeTasks = quest.getTasks().stream()
            .map(task -> new ActiveTask(this, task))
            .toList();
    }

    public void completeTask(ActiveTask activeTask) {
        this.activeTasks.remove(activeTask);

        if (!this.activeTasks.isEmpty()) {
            return;
        }

        this.complete();
    }

    public void complete() {
        this.giveRewards();
        this.playerData.getActiveQuests().remove(this);
        this.playerData.getCompletedQuests().add(this.getQuest());
    }

    private void giveRewards() {
        this.quest.getRewards().forEach(reward -> reward.give(this.playerData));
    }

}
