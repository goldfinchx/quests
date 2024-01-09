package org.goldfinch.quests;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.goldfinch.quests.commands.Commands;
import org.goldfinch.quests.commands.QuestCommands;
import org.goldfinch.quests.data.core.Hibernate;
import org.goldfinch.quests.player.QuestPlayerDataManager;
import org.goldfinch.quests.quest.QuestDataManager;

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
        new Commands(this);
        new QuestCommands(this);
    }


    @Override
    public void onDisable() {
        this.hibernate.close();
    }

}