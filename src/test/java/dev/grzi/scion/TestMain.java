package dev.grzi.scion;

import dev.grzi.scion.config.ScionConfig;
import dev.grzi.scion.core.Scion;

public class TestMain {

    public static void main(String[] args) {
        Scion.app(new ScionConfig()).run();
    }
}
