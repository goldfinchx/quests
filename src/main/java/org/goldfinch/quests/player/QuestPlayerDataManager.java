package org.goldfinch.quests.player;

import java.util.UUID;
import org.goldfinch.quests.Quests;
import org.goldfinch.quests.data.core.DataManager;
import org.goldfinch.quests.data.player.PlayerData;
import org.goldfinch.quests.data.player.PlayerDataManager;

public class QuestPlayerDataManager extends PlayerDataManager<QuestPlayerData> {

    public QuestPlayerDataManager(Quests plugin) {
        super(plugin, QuestPlayerData.class);
    }

    @Override
    protected QuestPlayerData getTemplate() {
        return new QuestPlayerData();
    }
}
