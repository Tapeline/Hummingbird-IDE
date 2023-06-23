package me.tapeline.hummingbird.ide.ui.terminal;

import com.jediterm.terminal.ui.JediTermWidget;
import com.jediterm.terminal.ui.settings.SettingsProvider;
import com.pty4j.PtyProcess;
import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.actionbar.CButtonAction;
import me.tapeline.carousellib.icons.items.CDisketteIcon;
import me.tapeline.carousellib.icons.navigation.CDownArrowIcon;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class HTerminal extends JPanel {

    private JediTermWidgetImpl termWidget;
    private CActionBar leftActions;

    public HTerminal(@NotNull SettingsProvider settingsProvider) {
        super();
        setLayout(new BorderLayout());
        termWidget = new JediTermWidgetImpl(settingsProvider);
        leftActions = new CActionBar(BoxLayout.PAGE_AXIS);
        add(termWidget, BorderLayout.CENTER);
        add(leftActions, BorderLayout.LINE_START);
        leftActions.addAction(new CButtonAction(
                null,
                new CDownArrowIcon(16),
                (action, component) -> scrollToBottom()

        ));
        leftActions.addAction(new CButtonAction(
                null,
                new CDisketteIcon(16),
                (action, component) -> System.out.println("SAVED")
        ));
    }

    public JediTermWidgetImpl getTermWidget() {
        return termWidget;
    }

    public CActionBar getLeftActions() {
        return leftActions;
    }

    public void scrollToBottom() {
        termWidget.getScrollBar().setValue(termWidget.getScrollBar().getMaximum());
    }

    public void stopAndClose(PtyProcess process) {
        if (process != null)
            process.destroy();
        termWidget.getTtyConnector().close();
        termWidget.stop();
        termWidget.getTerminalPanel().dispose();
    }

}
