package org.goldfinch.quests.quest;

import org.goldfinch.quests.Quests;
import org.goldfinch.quests.libs.storages.core.DataManager;
import org.goldfinch.quests.quest.entity.Quest;

public class QuestDataManager extends DataManager<Quest, Long> {

    private final Quests plugin;

    public QuestDataManager(Quests plugin) {
        super(Quest.class, plugin.getHibernate());
        this.plugin = plugin;
    }

    @Override
    protected Quest getTemplate() {
        return new Quest();
    }
}
