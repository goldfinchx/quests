package org.goldfinch.quests.libs.storages.core;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class DataObject<I extends Serializable> {

    @Id
    @GenericGenerator(name = "smart_id_gen", type = SmartIdGenerator.class)
    @GeneratedValue(generator = "smart_id_gen")
    private I id;

}
