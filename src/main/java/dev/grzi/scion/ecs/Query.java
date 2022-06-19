package dev.grzi.scion.ecs;

import dev.grzi.scion.exception.ScionIllegalArgumentException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Query {
    public static Query ALL = new Query();

    private Set<Class<?>> joins = new LinkedHashSet<>();
    private Set<Class<?>> exclusions = new LinkedHashSet<>();

    public static Query with(Class<?>... joins){
        if(joins.length == 0)
            throw new ScionIllegalArgumentException("joins argument must have at least one argument");
        var query = new Query();

        query.joins.addAll(Arrays.asList(joins));
        return query;
    }

    public Query andWith(Class<?>... joins){
        if(joins.length == 0)
            throw new ScionIllegalArgumentException("joins argument must have at least one argument");
        this.joins.addAll(Arrays.asList(joins));
        return this;
    }

    public static Query without(Class<?>... exclusions){
        if(exclusions.length == 0)
            throw new ScionIllegalArgumentException("exclusions argument must have at least one argument");
        var query = new Query();
        query.exclusions.addAll(Arrays.asList(exclusions));
        return query;
    }

    public Query andWithout(Class<?>... exclusions){
        if(exclusions.length == 0)
            throw new ScionIllegalArgumentException("exclusions argument must have at least one argument");

        this.exclusions.addAll(Arrays.asList(exclusions));
        return this;
    }


    public Set<EntityId> compute(World world) {
        var result = new HashSet<>(world.getAliveEntities());
        for (Class<?> type: this.joins) {
            var optStorage = world.getStorage(type);
            if (optStorage.isEmpty())
                return new HashSet<>();
            var storage = optStorage.get();
            result.removeIf(entityId -> !storage.hasComponentForEntityId(entityId));
        }

        for (Class<?> type: this.exclusions){
            var optStorage = world.getStorage(type);
            if (optStorage.isEmpty())
                continue;
            var storage = optStorage.get();
            result.removeIf(storage::hasComponentForEntityId);
        }

        return result;
    }
}
