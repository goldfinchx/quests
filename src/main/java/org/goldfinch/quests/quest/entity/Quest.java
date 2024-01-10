package org.goldfinch.quests.quest.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.goldfinch.quests.active.ActiveQuest;
import org.goldfinch.quests.data.core.DataObject;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.requirements.Requirement;
import org.goldfinch.quests.rewards.Reward;
import org.goldfinch.quests.tasks.Task;

@Builder
@Getter
@Entity
@Table(name = "quests")
@NoArgsConstructor
@AllArgsConstructor
public class Quest extends DataObject<Long> {

    private String name;
    private String description;

    @Singular
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Singular
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reward> rewards;

    @Singular
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Requirement> requirements;


    // todo move to conditions
    @Builder.Default
    private boolean parallelTasks = true;

    @Builder.Default
    private boolean repeatable = false;

    public void start(QuestPlayerData playerData) {
        if (playerData.isCompletingQuest(this)) {
            // todo send message
            return;
        }

        if (this.hasMetRequirements(playerData)) {
            // todo send message
            return;
        }

        final ActiveQuest activeQuest = new ActiveQuest(this, playerData);
        playerData.getActiveQuests().add(activeQuest);
    }

    public boolean hasMetRequirements(QuestPlayerData playerData) {
        return this.requirements.stream().allMatch(requirement -> requirement.hasMet(playerData));
    }

}
