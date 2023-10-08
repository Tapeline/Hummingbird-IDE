package me.tapeline.hummingbird.ide.expansion.plugins;

import me.tapeline.hummingbird.ide.expansion.themes.AbstractTheme;
import org.pf4j.ExtensionPoint;

import java.util.List;

public interface PluginThemeProvider {

    List<AbstractTheme> providedThemes();

}
