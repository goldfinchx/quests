package org.goldfinch.quests.quest.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.language.MessagesConfig;
import org.goldfinch.quests.tasks.ActiveTask;
import org.goldfinch.quests.libs.storages.core.DataObject;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.utils.TextUtils;

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

    public void complete() {
        this.quest.getRewards().give(this.playerData);
        this.playerData.getActiveQuests().remove(this);
        this.playerData.getCompletedQuests().add(this.getQuest());
    }

    public Component toComponent() {
        final MessagesConfig config = Quests.getInstance().getMessagesConfig();

        Component component = Component.text(this.quest.getName())
            .appendNewline()
            .append(TextUtils.divideComponent(Component.text(this.quest.getDescription()), 35))
            .appendNewline()
            .append(config.get(MessagesConfig.Message.TASKS_LIST_IN_QUEST_INFO, this.playerData));

        for (final ActiveTask activeTask : this.getActiveTasks()) {
            component = component.append(activeTask.toComponent());
        }

        if (this.quest.getConditions().getDeadline() != null) {
            component = component
                .appendNewline()
                .append(config.get(MessagesConfig.Message.CONDITIONS_DEALINE, this.playerData,
                    "%deadline%", this.quest.getConditions().getDeadline().toString())
                );
        }

        return component.appendNewline();
    }

}
