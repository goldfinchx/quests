package org.goldfinch.quests.conditions;


import jakarta.persistence.Embeddable;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;


@Builder
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Conditions {

    @Builder.Default
    private TaskLogic tasksLogic = TaskLogic.PARALLEL;

    @Builder.Default
    private long timeLimitMinutes = 0;

    @Builder.Default
    private Timestamp deadline = null;

    @Builder.Default
    private boolean isCancelable = true;

    @Builder.Default
    private boolean isRepeatable = false;

    @Builder.Default
    private boolean startViaCommand = true;

    @Builder.Default
    private int questNpcId = -1;

    @Singular
    private List<String> startCommands;

    @Singular
    private List<String> endCommands;

    public enum TaskLogic {
        PARALLEL,
        SEQUENTIAL,
        RANDOM
    }

}
