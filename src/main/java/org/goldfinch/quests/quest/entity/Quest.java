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
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.active.ActiveQuest;
import org.goldfinch.quests.conditions.Conditions;
import org.goldfinch.quests.data.core.DataObject;
import org.goldfinch.quests.language.MessagesConfig;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.requirements.Requirements;
import org.goldfinch.quests.rewards.Rewards;
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

    @Builder.Default
    private Rewards rewards = new Rewards();

    @Builder.Default
    private Requirements requirements = new Requirements();

    @Builder.Default
    private Conditions conditions = new Conditions();

    public void start(QuestPlayerData playerData) {
        if (playerData.isCompletingQuest(this)) {
            Quests.getInstance().getMessagesConfig().send(playerData, MessagesConfig.Message.ALREADY_COMPLETING_QUEST);
            return;
        }

        if (!this.requirements.hasMet(playerData)) {
            Quests.getInstance().getMessagesConfig().send(playerData, MessagesConfig.Message.REQUIREMENTS_NOT_MET);
            return;
        }

        final ActiveQuest activeQuest = new ActiveQuest(this, playerData);
        playerData.getActiveQuests().add(activeQuest);
    }

}
