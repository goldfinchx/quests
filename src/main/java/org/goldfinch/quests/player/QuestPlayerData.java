package org.goldfinch.quests.player;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.ActiveQuest;
import org.goldfinch.quests.ActiveTask;
import org.goldfinch.quests.data.core.DataObject;
import org.goldfinch.quests.data.player.PlayerData;
import org.goldfinch.quests.data.player.PlayerDataManager;
import org.goldfinch.quests.quest.Quest;
import org.goldfinch.quests.tasks.Task;

@Data
@Entity
@Table(name = "quest_players_data")
@NoArgsConstructor
public class QuestPlayerData extends PlayerData {

    private int level;
    private int questPoints;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ActiveQuest> activeQuests;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Quest> completedQuests;

    public List<ActiveTask> getActiveTasks(Class<? extends Task> taskType) {
        return this.activeQuests.stream()
            .flatMap(activeQuest -> activeQuest.getActiveTasks().stream())
            .filter(activeTask -> activeTask.getTask().getClass().equals(taskType))
            .toList();
    }

    public QuestPlayerData(UUID uuid) {
        super(uuid);
        this.level = 1;
        this.questPoints = 0;
        this.activeQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
    }

}
