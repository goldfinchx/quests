package org.goldfinch.quests.libs.storages.player;

import jakarta.persistence.MappedSuperclass;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.goldfinch.quests.libs.storages.core.DataObject;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class PlayerData extends DataObject<UUID> {

    public PlayerData(UUID id) {
        super(id);
    }

}
