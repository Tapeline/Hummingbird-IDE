package me.tapeline.hummingbird.expansions.themes;

import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;

public abstract class AbstractTheme {

    public abstract String name();

    public abstract AbstractColorScheme scheme();
    public abstract AbstractColorSet colors();

    public abstract void onApply();

}
