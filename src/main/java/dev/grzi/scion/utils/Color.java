package dev.grzi.scion.utils;

import dev.grzi.scion.exception.ScionIllegalArgumentException;

public class Color {
    public static int COLOR_MIN = 0;
    public static int COLOR_MAX = 255;
    public static float ALPHA_MIN = 0.0f;
    public static float ALPHA_MAX = 1.0f;

    private int red;
    private int green;
    private int blue;
    private float alpha;

    public Color(int red, int green, int blue, float alpha) {
        if (!validArguments(red, green, blue, alpha))
            throw new ScionIllegalArgumentException("Illegal color requested");

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public Color(int red, int green, int blue) {
        this(red, green, blue, 1.0f);
    }

    private boolean validArguments(int red, int green, int blue, float alpha) {
        return red >= COLOR_MIN && green >= COLOR_MIN && blue >= COLOR_MIN
                && red <= COLOR_MAX && green <= COLOR_MAX && blue <= COLOR_MAX
                && alpha >= ALPHA_MIN && alpha <= ALPHA_MAX;
    }

    public void replace(Color replacement) {
        this.alpha = replacement.getAlpha();
        this.red = replacement.getRed();
        this.green = replacement.getGreen();
        this.blue = replacement.getBlue();
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
