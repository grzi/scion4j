package dev.grzi.scion.ecs;

public class Entity {
    private EntityId entityId;
    private World world;

    Entity(EntityId entityId, World world){
        this.entityId = entityId;
        this.world = world;
    }

    public EntityId getEntityId(){
        return this.entityId;
    }
}
