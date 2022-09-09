package me.tapeline.hummingbird.expansions.themes;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;

import java.awt.*;

public class LightTheme extends AbstractTheme {
    @Override
    public String name() {
        return "Light";
    }

    @Override
    public AbstractColorScheme scheme() {
        return new AbstractColorScheme();
    }

    @Override
    public AbstractColorSet colors() {
        return new ColorSet();
    }

    @Override
    public void onApply() {
        FlatIntelliJLaf.setup();
    }

    public static class ColorSet extends AbstractColorSet {
        public ColorSet() {
            backgroundText = new Color(215, 215, 215);
            backgroundTextHighlight = new Color(199, 199, 199);
        }
    }
}
