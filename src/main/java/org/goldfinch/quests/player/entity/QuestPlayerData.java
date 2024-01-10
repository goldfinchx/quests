package org.goldfinch.quests.player.entity;

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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.goldfinch.quests.active.ActiveQuest;
import org.goldfinch.quests.active.ActiveTask;
import org.goldfinch.quests.data.player.PlayerData;
import org.goldfinch.quests.quest.entity.Quest;
import org.goldfinch.quests.tasks.Task;

@Data
@Entity
@Table(name = "quest_players_data")
@NoArgsConstructor
public class QuestPlayerData extends PlayerData {

    private int level;
    private int questPoints;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ActiveQuest> activeQuests;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Quest> completedQuests;

    public QuestPlayerData(UUID uuid) {
        super(uuid);
        this.level = 1;
        this.questPoints = 0;
        this.activeQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
    }

    public List<ActiveTask> getActiveTasks(Class<? extends Task> taskType) {
        return this.getActiveQuests()
            .stream()
            .flatMap(activeQuest -> activeQuest.getActiveTasks().stream())
            .filter(activeTask -> activeTask.getTask().getClass().equals(taskType))
            .toList();
    }

    public boolean isCompletingQuest(Quest quest) {
        return this.getActiveQuests().stream().anyMatch(activeQuest -> activeQuest.getQuest().equals(quest));
    }

    public Player getBukkitPlayer() {
        final Player player = Bukkit.getPlayer(this.getId());

        if (player == null) {
            throw new NullPointerException("Player is offline!");
        }

        return player;
    }

}
