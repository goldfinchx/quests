package org.goldfinch.quests.data.player;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.plugin.java.JavaPlugin;
import org.goldfinch.quests.data.core.DataManager;
import org.goldfinch.quests.data.core.Hibernate;

@Setter
@Accessors(chain = true, fluent = true)
public abstract class PlayerDataManager<P extends PlayerData> extends DataManager<P, UUID> {

    private Consumer<P> onCreate = $ -> {};

    public PlayerDataManager(JavaPlugin plugin, Class<P> playerDataClass, Hibernate hibernate) {
        super(playerDataClass, hibernate);
        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(this), plugin);
    }

    @Override
    public P load(UUID id) {
        if (this.isCached(id)) {
            return this.getCached(id);
        }

        if (this.isExists(id)) {
            final P data = this.getRemote(id);
            this.onLoad().accept(data);
            this.cache(data);
            return data;
        } else {
            try {
                final P data = this.create((P) this.getTemplate().getClass().getConstructor(UUID.class).newInstance(id));
                this.onCreate.accept(data);
                return data;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
