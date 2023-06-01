package me.tapeline.carousellib.utils;

import java.awt.*;

public class AdaptableColor {

    private Color light;
    private Color dark;

    public AdaptableColor(Color light, Color dark) {
        this.light = light;
        this.dark = dark;
    }

    public static AdaptableColor fromLight(Color light) {
        return new AdaptableColor(light, light.darker());
    }

    public static AdaptableColor fromDark(Color dark) {
        return new AdaptableColor(dark.brighter(), dark);
    }

    public Color getColor(boolean isDark) {
        return isDark? dark : light;
    }

    public Color getLight() {
        return light;
    }

    public Color getDark() {
        return dark;
    }

}
