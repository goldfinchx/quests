package org.goldfinch.quests.requirements;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.player.QuestPlayerData;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Requirement {


    @Transient
    private String title;

    @Transient //todo config
    private String failureMessage;

    public boolean hasMet(QuestPlayerData questPlayerData) {
        return true;
    }


}
