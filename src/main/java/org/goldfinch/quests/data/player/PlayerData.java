package org.goldfinch.quests.data.player;

import jakarta.persistence.MappedSuperclass;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.data.core.DataObject;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class PlayerData extends DataObject<UUID> {

    public PlayerData(UUID uuid) {
        super(uuid);
    }

}
