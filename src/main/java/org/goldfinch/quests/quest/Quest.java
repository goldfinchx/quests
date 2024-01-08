package org.goldfinch.quests.quest;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.goldfinch.quests.data.DataObject;
import org.goldfinch.quests.requirements.Requirement;
import org.goldfinch.quests.rewards.Reward;
import org.goldfinch.quests.tasks.Task;

@Entity
@Getter
@Builder
@Table(name = "quests")
@NoArgsConstructor
@AllArgsConstructor
public class Quest extends DataObject<Long> {

    private String name;
    private String description;

    @Singular
    @ElementCollection
    private List<Task> tasks;

    @Singular
    @ElementCollection
    private List<Reward> rewards;

    @Singular
    @ElementCollection
    private List<Requirement> requirements;

    @Builder.Default
    private boolean parallelTasks = true;

}
