package org.goldfinch.quests.player;

import org.goldfinch.quests.Quests;
import org.goldfinch.quests.data.player.PlayerDataManager;
import org.goldfinch.quests.player.entity.QuestPlayerData;

public class QuestPlayerDataManager extends PlayerDataManager<QuestPlayerData> {

    public QuestPlayerDataManager(Quests plugin) {
        super(plugin, QuestPlayerData.class, plugin.getHibernate());
    }

    @Override
    protected QuestPlayerData getTemplate() {
        return new QuestPlayerData();
    }


}
