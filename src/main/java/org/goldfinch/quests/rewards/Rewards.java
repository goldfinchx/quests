package org.goldfinch.quests.rewards;

import jakarta.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.language.MessagesConfig;
import org.goldfinch.quests.player.entity.QuestPlayerData;
import org.goldfinch.quests.wrappers.impl.ItemStackWrapper;

@Builder
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Rewards {

    @Builder.Default
    private int experience = 0;

    @Builder.Default
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
