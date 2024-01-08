package org.goldfinch.quests.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LocalCacheManager<D extends DataObject<I>, I extends Serializable> {

    private final Map<I, D> cache = new HashMap<>();

    public void save(D data) {
        this.cache.put(data.getId(), data);
    }

    public void invalidate(I id) {
        this.cache.remove(id);
    }

    public D get(I id) {
        return this.cache.get(id);
    }

    public Map<I, D> getAll() {
        return this.cache;
    }

    public boolean isCached(I id) {
        return this.cache.containsKey(id);
    }
}
