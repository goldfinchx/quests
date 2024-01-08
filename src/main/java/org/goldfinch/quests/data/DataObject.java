package org.goldfinch.quests.data;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class DataObject<I extends Serializable> {

    @Id
    @GeneratedValue
    private I id;

}
