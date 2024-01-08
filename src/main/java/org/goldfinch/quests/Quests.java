package org.goldfinch.quests;

import java.util.UUID;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.goldfinch.quests.data.core.Hibernate;
import org.goldfinch.quests.player.QuestPlayerData;
import org.goldfinch.quests.player.QuestPlayerDataManager;
import org.goldfinch.quests.quest.Quest;
import org.goldfinch.quests.quest.QuestDataManager;
import org.goldfinch.quests.requirements.LevelRequirement;

@Getter
public class Quests extends JavaPlugin {

    @Getter
    private static Quests instance;
    private Hibernate hibernate;

    private QuestPlayerDataManager playerDataManager;
    private QuestDataManager questDataManager;

    @Override
    public void onEnable() {
        instance = this;

        this.hibernate = new Hibernate();
        this.playerDataManager = new QuestPlayerDataManager(this);
        this.questDataManager = new QuestDataManager();

        new QuestListener(this);

        final Quest quest = Quest.builder()
            .name("Test quest")
            .description("Test description")
            .parallelTasks(false)
            .requirement(new LevelRequirement(10))
            .build();

        final ActiveQuest activeQuest = new ActiveQuest(quest);
        final QuestPlayerData questPlayerData = new QuestPlayerData(UUID.randomUUID());
        questPlayerData.getCompletedQuests().add(quest);
        questPlayerData.getActiveQuests().add(activeQuest);

        this.questDataManager.create(quest);
        this.playerDataManager.create(questPlayerData);
    }


    @Override
    public void onDisable() {
        this.hibernate.close();
    }

}