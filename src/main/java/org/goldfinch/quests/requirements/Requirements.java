package org.goldfinch.quests.requirements;

import jakarta.persistence.Embeddable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.bukkit.entity.Player;
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.language.MessagesConfig;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.quest.entity.Quest;

@Builder
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Requirements {

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
