package org.goldfinch.quests;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.data.core.DataObject;
import org.goldfinch.quests.quest.Quest;

@Data
@Entity
@Table(name = "active_quests")
@NoArgsConstructor
public class ActiveQuest extends DataObject<Long> {

    @ManyToOne
    private Quest quest;

    @ElementCollection
    private List<ActiveTask> activeTasks = new ArrayList<>();

    public ActiveQuest(Quest quest) {
        this.quest = quest;
        this.activeTasks = quest.getTasks().stream()
            .map(task -> new ActiveTask(this, task))
            .toList();
    }

}
