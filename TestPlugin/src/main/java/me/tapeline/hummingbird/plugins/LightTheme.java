package me.tapeline.hummingbird.plugins;

import com.formdev.flatlaf.FlatIntelliJLaf;
import me.tapeline.carousellib.icons.CBundledIcon;
import me.tapeline.hummingbird.ide.expansion.themes.AbstractTheme;

public class LightTheme extends AbstractTheme {

    @Override
    public boolean isDark() {
        return false;
    }

    @Override
    public String id() {
        return "light";
    }

    @Override
    public void onApply() {
        FlatIntelliJLaf.setup();
        CBundledIcon.darkMode = false;
    }

}
