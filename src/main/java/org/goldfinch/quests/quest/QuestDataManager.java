package org.goldfinch.quests.quest;

import org.goldfinch.quests.data.core.DataManager;

public class QuestDataManager extends DataManager<Quest, Long> {

    public QuestDataManager() {
        super(Quest.class);
    }

    @Override
    protected Quest getTemplate() {
        return new Quest();
    }
}
