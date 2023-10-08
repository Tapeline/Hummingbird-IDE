package me.tapeline.hummingbird.ide.ui.terminal;

import com.jediterm.terminal.ui.JediTermWidget;
import com.jediterm.terminal.ui.settings.SettingsProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class JediTermWidgetImpl extends JediTermWidget {

    public JediTermWidgetImpl(@NotNull SettingsProvider settingsProvider) {
        super(settingsProvider);
    }

    public JScrollBar getScrollBar() {
        return myScrollBar;
    }

}
