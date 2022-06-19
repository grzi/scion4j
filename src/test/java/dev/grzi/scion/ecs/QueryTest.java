package dev.grzi.scion.ecs;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {

    final static Query INTEGER_WITHOUT_BOOLEAN = Query.with(Integer.class).andWithout(Boolean.class);

    @Test
    void builder(){
        var query = Query.with(Integer.class, String.class, Boolean.class)
                .andWithout(Optional.class);

        var query2 = Query.without(Integer.class, String.class, Boolean.class)
                .andWith(Optional.class);


    }
}