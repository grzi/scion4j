package dev.grzi.scion.utils;

import dev.grzi.scion.exception.ScionIllegalArgumentException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void testClassicUsage(){
        var color = new Color(200, 222, 250, 1.0f);

        assertThat(color.getRed()).isEqualTo(200);
        assertThat(color.getGreen()).isEqualTo(222);
        assertThat(color.getBlue()).isEqualTo(250);
        assertThat(color.getAlpha()).isEqualTo(1.0f);

        color.replace(new Color(1,2,3, 0.1f));
        assertThat(color.getRed()).isEqualTo(1);
        assertThat(color.getGreen()).isEqualTo(2);
        assertThat(color.getBlue()).isEqualTo(3);
        assertThat(color.getAlpha()).isEqualTo(0.1f);

        var color2 = new Color(200, 222, 250);

        assertThat(color2.getRed()).isEqualTo(200);
        assertThat(color2.getGreen()).isEqualTo(222);
        assertThat(color2.getBlue()).isEqualTo(250);
        assertThat(color2.getAlpha()).isEqualTo(1.0f);
    }

    @Test
    void testLimits(){
        assertThrows(ScionIllegalArgumentException.class, () -> new Color(400, 200, 200, 1.0f));
        assertThrows(ScionIllegalArgumentException.class, () -> new Color(-1, 200, 200, 1.0f));

        assertThrows(ScionIllegalArgumentException.class, () -> new Color(200, 400, 200, 1.0f));
        assertThrows(ScionIllegalArgumentException.class, () -> new Color(200, -1, 200, 1.0f));

        assertThrows(ScionIllegalArgumentException.class, () -> new Color(200, 200, 400, 1.0f));
        assertThrows(ScionIllegalArgumentException.class, () -> new Color(200, 200, -1, 1.0f));

        assertThrows(ScionIllegalArgumentException.class, () -> new Color(200, 200, 200, 1.1f));
        assertThrows(ScionIllegalArgumentException.class, () -> new Color(200, 200, 200, -0.1f));
    }

}