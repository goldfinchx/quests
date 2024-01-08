package org.goldfinch.quests.quest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.goldfinch.quests.data.core.DataObject;
import org.goldfinch.quests.requirements.Requirement;
import org.goldfinch.quests.rewards.Reward;
import org.goldfinch.quests.tasks.Task;

@Builder
@Getter
@Entity
@Table(name = "quests")
@NoArgsConstructor
@AllArgsConstructor
public class Quest extends DataObject<Long> {

    private String name;
    private String description;

    @Singular
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Singular
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reward> rewards;

    @Singular
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Requirement> requirements;

    @Builder.Default
    private boolean parallelTasks = true;

}
