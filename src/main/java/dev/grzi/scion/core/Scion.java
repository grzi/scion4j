package dev.grzi.scion.core;

import dev.grzi.scion.config.ScionConfig;
import dev.grzi.scion.ecs.World;

public class Scion implements Runnable{

    private ScionConfig config;
    private World mainWorld;
    private long window;

    private Scion(ScionConfig config) {
        this.config = config;
    }

    /**
     * Creates a new `Scion` application using given configurations
     */
    public static Scion app(ScionConfig config){
        return new Scion(config);
    }

    @Override
    public void run() {
        lateInit();
    }

    private void lateInit()  {
        this.mainWorld = new World();
    }
}
