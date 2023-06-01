package me.tapeline.hummingbird.ide.expansion.themes;

import me.tapeline.hummingbird.ide.expansion.RegistryEntry;

public abstract class AbstractTheme implements RegistryEntry {

    public abstract boolean isDark();

    public abstract String id();

    public abstract void onApply();

}
