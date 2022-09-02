package me.tapeline.hummingbird.expansions.themes;

import com.formdev.flatlaf.FlatDarculaLaf;
import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;

public class LightTheme extends AbstractTheme {
    @Override
    public String name() {
        return "Darcula";
    }

    @Override
    public AbstractColorScheme scheme() {
        return new AbstractColorScheme();
    }

    @Override
    public void onApply() {
        FlatDarculaLaf.setup();
    }
}
