package dev.grzi.scion.ecs;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class WorldTest {

    @Test
    void initWorld() {
        var world = new World("My awesome world");
        assertThat(world.getName()).isEqualTo("My awesome world");
    }

    @Test
    void testStandardCycle() {
        var world = new World("Testing world");
        var entityId1 = world.push("Name of the entity", 58, true, new AtomicInteger(19));
        var entityId2 = world.push("Name of the entity", 58, true, new AtomicInteger(19));

        assertThat(world.query(Query.ALL)).isEmpty(); // No entities because they are marked to be add at the end of the current cycle

        world.endGeneration();
        world.query(Query.with(AtomicInteger.class));

        var allAliveEntities = world.query(Query.ALL);
        assertThat(allAliveEntities).hasSize(2);
        assertThat(allAliveEntities.stream()).anyMatch(e -> e.getEntityId().equals(entityId1));
        assertThat(allAliveEntities.stream()).anyMatch(e -> e.getEntityId().equals(entityId2));

        world.removeEntity(entityId1);
        assertThat(world.query(Query.ALL)).hasSize(2); // Two entity because entity2 has been marked to be removed at the end of current cycle

        world.endGeneration();

        allAliveEntities = world.query(Query.ALL);
        assertThat(allAliveEntities).hasSize(1);
        assertThat(allAliveEntities.stream()).anyMatch(e -> e.getEntityId().equals(entityId2));

        var nextEntity = world.push("Another entity that should reuse index");
        assertThat(nextEntity.index()).isEqualTo(entityId1.index());
    }

    @Test
    void testResources() {
        var world = new World();
        world.addResource(89);

        assertThat(world.getResource(Integer.class)).isPresent();

        world.removeResource(Integer.class);
        assertThat(world.getResource(Integer.class)).isEmpty();
    }
}