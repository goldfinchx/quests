package org.goldfinch.quests.wrappers;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.goldfinch.quests.data.core.DataObject;

@Getter
@Entity
@Table(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
public class LocationWrapper extends DataObject<Long> implements Wrapper<Location> {

    private String world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public LocationWrapper(Location location) {
        this(location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    @Override
    public Location unwrap() {
        final World world = Bukkit.getWorld(this.world);

        if (world == null) {
            throw new IllegalStateException("World " + this.world + " is not loaded!");
        }

        return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

}
