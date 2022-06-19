package dev.grzi.scion.ecs;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Storage<T> {
    private BitSet flags = new BitSet((int)Math.pow(2,14));
    private Map<EntityId, T> components = new HashMap<>();

    void registerComponent(EntityId entityId, T component){
        this.flags.set(entityId.index());
        this.components.put(entityId, component);
    }

    public boolean hasComponentForEntityId(EntityId entityId) {
        return this.flags.get(entityId.index());
    }

    public void removeEntities(Set<EntityId> zombieEntities) {
        zombieEntities.forEach(entity -> {
            flags.clear(entity.index());
            components.remove(entity);
        });
    }

    BitSet getFlags(){
        return this.flags;
    }
}
