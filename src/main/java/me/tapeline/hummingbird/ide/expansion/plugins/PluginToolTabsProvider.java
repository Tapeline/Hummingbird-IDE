package me.tapeline.hummingbird.ide.expansion.plugins;

import me.tapeline.hummingbird.ide.ui.tooltabs.AbstractToolTab;
import org.pf4j.ExtensionPoint;

import java.util.List;

public interface PluginToolTabsProvider {

    List<AbstractToolTab> providedLeftToolTabs();
    List<AbstractToolTab> providedRightToolTabs();
    List<AbstractToolTab> providedBottomToolTabs();

}
