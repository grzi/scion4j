package dev.grzi.scion.ecs;

import dev.grzi.scion.exception.ScionIllegalArgumentException;
import dev.grzi.scion.exception.ScionRuntimeException;

import java.util.*;
import java.util.stream.Collectors;

public class World {

    private final String name;

    private final Map<Class<?>, Storage<?>> storages = new HashMap<>();
    // List of all 'resources' available
    private final Map<Class<?>, Object> resources = new HashMap<>();
    // List of entities ids and their components that are marked to be add at the end of the current generation
    private final Map<EntityId, Object[]> nextGeneration = new HashMap<>();
    // List of entities ids that are alive during the current generation
    private final Set<EntityId> aliveEntities = new LinkedHashSet<>();
    // List of entities ids that has been marked to be deleted at the end of the current generation
    private final Set<EntityId> zombieEntities = new LinkedHashSet<>();
    // List of indexes to be reused. Usefull to keep a limited size for the storages
    private final Set<Integer> reusableIndexes = new LinkedHashSet<>();

    private int position = 0;

    public World() {
        this.name = "World";
    }

    public World(String name) {
        this.name = name;
    }

    public EntityId push(Object... components){
        if(components.length == 0)
            throw new ScionIllegalArgumentException("components argument must have at least one element");
        var entityId = nextEntityId();
        this.nextGeneration.put(entityId, components);
        return entityId;
    }

    public void removeEntity(EntityId entityId){
        this.zombieEntities.add(entityId);
    }

    public Set<Entity> query(Query query){
        return query.compute(this).stream()
                .map(entityId -> new Entity(entityId, this))
                .collect(Collectors.toSet());
    }

    public <T> void addResource(T resource){
        if (resource == null)
            throw new ScionIllegalArgumentException("Resource can't be null");

        this.resources.put(resource.getClass(), resource);
    }

    public <T> void removeResource(Class<T> tClass){
        if (tClass == null)
            throw new ScionIllegalArgumentException("Resource type can't be null");

        this.resources.remove(tClass);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getResource(Class<T> tClass){
        return Optional.ofNullable(this.resources.get(tClass)).map(res -> (T) res);
    }

    void endGeneration(){
        zombieEntities.forEach(entityId -> aliveEntities.removeIf(e -> e.equals(entityId)));
        this.cleanStorages();
        nextGeneration.forEach((entityId, value) -> {
            for (Object component : value) {
                this.pushComponent(component, entityId);
            }
            this.aliveEntities.add(entityId);
        });
        this.aliveEntities.removeAll(this.zombieEntities);
        this.reusableIndexes.addAll(this.zombieEntities.stream()
                .map(EntityId::index).collect(Collectors.toSet()));
        this.aliveEntities.addAll(this.nextGeneration.keySet());
        this.zombieEntities.clear();
        this.nextGeneration.clear();
    }

    void cleanStorages(){
        this.storages.values().forEach(storage -> storage.removeEntities(this.zombieEntities));
    }

    @SuppressWarnings("unchecked")
    private <T> void pushComponent(T component, EntityId entityId) {
        this.upsertStorage(component.getClass());
        var storage = this.getStorage((Class<T>) component.getClass())
                .orElseThrow(() -> new ScionRuntimeException("Storage must not be empty"));
        storage.registerComponent(entityId, component);
    }



    private EntityId nextEntityId() {
        if(!this.reusableIndexes.isEmpty()){
            var i = this.reusableIndexes.iterator();
            var nextReusableIndex = i.next();
            i.remove();
            return new EntityId(nextReusableIndex);
        }
        var res = new EntityId(this.position);
        this.position++;
        return res;
    }

    <T> void upsertStorage(Class<T> type) {
        this.storages.putIfAbsent(type, new Storage<>());
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    <T> Optional<Storage<T>> getStorage(Class<T> type) {
        return Optional.ofNullable((Storage<T>)this.storages.get(type));
    }

    Set<EntityId> getAliveEntities(){
        return this.aliveEntities;
    }
}