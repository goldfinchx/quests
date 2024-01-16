package org.goldfinch.quests;

import lombok.Getter;
import net.minecraft.server.dedicated.Settings;
import org.bukkit.plugin.java.JavaPlugin;
import org.goldfinch.quests.configs.SettingsConfig;
import org.goldfinch.quests.libs.commands.Commands;
import org.goldfinch.quests.commands.QuestCommands;
import org.goldfinch.quests.libs.storages.dbs.Hibernate;
import org.goldfinch.quests.configs.MessagesConfig;
import org.goldfinch.quests.listener.QuestListener;
import org.goldfinch.quests.player.QuestPlayerDataManager;
import org.goldfinch.quests.quest.QuestDataManager;

@Getter
public class Quests extends JavaPlugin {

    @Getter
    private static Quests instance;

    private Hibernate hibernate;

    private QuestPlayerDataManager playerDataManager;
    private QuestDataManager questDataManager;

    private MessagesConfig messagesConfig;
    private SettingsConfig settingsConfig;

    @Override
    public void onEnable() {
        instance = this;
        this.hibernate = new Hibernate();

        this.playerDataManager = new QuestPlayerDataManager(this);
        this.questDataManager = new QuestDataManager(this);

        this.messagesConfig = new MessagesConfig(this);
        this.settingsConfig = new SettingsConfig(this);

        new Commands(this);

        new QuestListener(this);
        new QuestCommands(this);
    }


    @Override
    public void onDisable() {
        this.hibernate.close();
    }



}