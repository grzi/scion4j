package dev.grzi.scion.ecs;

import dev.grzi.scion.exception.ScionIllegalArgumentException;

import java.util.*;
import java.util.stream.Collectors;

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
        var bitset = new BitSet((int)Math.pow(2,14));
        world.getAliveEntities().forEach(e -> bitset.set(e.index()));
        for (Class<?> type: this.joins) {
            var optStorage = world.getStorage(type);
            if (optStorage.isEmpty())
                return new HashSet<>();
            var storage = optStorage.get();
            bitset.and(storage.getFlags());
        }

        for (Class<?> type: this.exclusions){
            var optStorage = world.getStorage(type);
            if (optStorage.isEmpty())
                continue;
            var storage = optStorage.get();
            bitset.andNot(storage.getFlags());
        }

        return bitset.stream().mapToObj(EntityId::new)
                .collect(Collectors.toSet());
    }
}
