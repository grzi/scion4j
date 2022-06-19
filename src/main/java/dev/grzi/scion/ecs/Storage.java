package dev.grzi.scion.ecs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Storage<T> {
    private boolean[] flags = new boolean[(int)Math.pow(2,14)];
    private Map<EntityId, T> components = new HashMap<>();

    void registerComponent(EntityId entityId, T component){
        this.flags[entityId.index()] = true;
        this.components.put(entityId, component);
    }

    public boolean hasComponentForEntityId(EntityId entityId) {
        return this.flags[entityId.index()];
    }

    public void removeEntities(Set<EntityId> zombieEntities) {
        zombieEntities.forEach(entity -> {
            flags[entity.index()] = false;
            components.remove(entity);
        });
    }
}
