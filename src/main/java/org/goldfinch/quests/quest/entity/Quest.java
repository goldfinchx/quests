package org.goldfinch.quests.quest.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.libs.storages.wrappers.impl.ItemStackWrapper;
import org.goldfinch.quests.libs.storages.core.DataObject;
import org.goldfinch.quests.configs.MessagesConfig;
import org.goldfinch.quests.player.entity.QuestPlayerData;
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
        final MessagesConfig config = Quests.getInstance().getMessagesConfig();

        if (playerData.isCompletingQuest(this)) {
            config.send(playerData, MessagesConfig.Message.ALREADY_COMPLETING_QUEST);
            return;
        }

        if (!this.requirements.hasMet(playerData)) {
            config.send(playerData, MessagesConfig.Message.REQUIREMENTS_NOT_MET);
            return;
        }

        if (playerData.hasCompleted(this) && !this.conditions.isRepeatable()) {
            config.send(playerData, MessagesConfig.Message.CONDITIONS_NOT_REPEATABLE);
        }

        final ActiveQuest activeQuest = new ActiveQuest(this, playerData);
        playerData.getActiveQuests().add(activeQuest);

        this.conditions.getStartCommands().forEach(str -> Bukkit.getServer().dispatchCommand(playerData.getBukkitPlayer(), str));
    }

    @Builder
    @Getter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Requirements {

        @Builder.Default
        private int level = 0;

        @Singular
        private List<String> permissions = new ArrayList<>();

        @Singular
        private List<Long> completedQuests = new ArrayList<>();

        public boolean hasMet(QuestPlayerData questPlayerData) {
            return this.hasMetLevel(questPlayerData) &&
                   this.hasMetPermissions(questPlayerData) &&
                   this.hasMetCompletedQuests(questPlayerData);
        }

        private boolean hasMetLevel(QuestPlayerData questPlayerData) {
            final boolean hasMet = questPlayerData.getLevel() >= this.level;

            if (!hasMet) {
                Quests.getInstance().getMessagesConfig().send(questPlayerData, MessagesConfig.Message.REQUIREMENTS_NOT_MET_LEVEL);
            }

            return hasMet;
        }
        private boolean hasMetPermissions(QuestPlayerData questPlayerData) {
            final Player player = questPlayerData.getBukkitPlayer();
            final boolean hasMet = this.permissions.stream().allMatch(player::hasPermission);

            if (!hasMet) {
                Quests.getInstance().getMessagesConfig().send(questPlayerData, MessagesConfig.Message.REQUIREMENTS_NOT_MET_PERMISSIONS);
            }

            return hasMet;
        }
        private boolean hasMetCompletedQuests(QuestPlayerData questPlayerData) {
            final boolean hasMet = questPlayerData.getCompletedQuests().stream()
                .map(Quest::getId)
                .collect(Collectors.toSet())
                .containsAll(new HashSet<>(this.completedQuests));

            if (!hasMet) {
                Quests.getInstance().getMessagesConfig().send(questPlayerData, MessagesConfig.Message.REQUIREMENTS_NOT_MET_COMPLETED_QUESTS);
            }

            return hasMet;
        }

    }

    @Builder
    @Getter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Conditions {

        @Builder.Default
        private TaskLogic tasksLogic = TaskLogic.PARALLEL;

        @Builder.Default//todo
        private Timestamp deadline = null;

        @Builder.Default//todo
        private boolean isCancelable = true;

        @Builder.Default
        private boolean isRepeatable = false;

        @Builder.Default//todo
        private boolean startViaCommand = true;

        @Builder.Default//todo
        private int questNpcId = -1;

        @Singular
        private List<String> startCommands;

        @Singular
        private List<String> endCommands;

        public enum TaskLogic {
            PARALLEL,
            SEQUENTIAL,
            RANDOM
        }

    }

    @Builder
    @Getter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rewards {

        @Builder.Default
        private int experience = 0;

        @Builder.Default
        @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        private List<ItemStackWrapper> items = new ArrayList<>();

        @Builder.Default
        private int questPoints = 0;

        public void give(QuestPlayerData questPlayerData) {
            this.giveExperience(questPlayerData);
            this.giveItems(questPlayerData);
            this.giveQuestPoints(questPlayerData);
        }

        private void giveExperience(QuestPlayerData questPlayerData) {
            Quests.getInstance().getMessagesConfig().send(questPlayerData, MessagesConfig.Message.REWARD_EXPERIENCE);
            questPlayerData.getBukkitPlayer().giveExp(this.experience);
        }

        private void giveItems(QuestPlayerData questPlayerData) {
            Quests.getInstance().getMessagesConfig().send(questPlayerData, MessagesConfig.Message.REWARD_ITEMS);

            final Player player = questPlayerData.getBukkitPlayer();
            this.items.forEach(itemStackWrapper -> player.getInventory().addItem(itemStackWrapper.unwrap()));
        }

        private void giveQuestPoints(QuestPlayerData questPlayerData) {
            Quests.getInstance().getMessagesConfig().send(questPlayerData, MessagesConfig.Message.REWARD_QUEST_POINTS);
            questPlayerData.setQuestPoints(questPlayerData.getQuestPoints() + this.questPoints);
        }

    }

}
