package me.tapeline.hummingbird.ide.tooltabs.terminal;

import com.formdev.flatlaf.ui.FlatScrollBarUI;
import com.jediterm.pty.PtyProcessTtyConnector;
import com.jediterm.terminal.TtyConnector;
import com.jediterm.terminal.ui.JediTermWidget;
import com.jediterm.terminal.ui.UIUtil;
import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.actionbar.CButtonAction;
import me.tapeline.carousellib.elements.actionbar.CHSpacerAction;
import me.tapeline.carousellib.icons.items.CShellIcon;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.tooltabs.terminal.data.TerminalSettingsProvider;
import me.tapeline.hummingbird.ide.ui.terminal.HTerminal;
import me.tapeline.hummingbird.ide.ui.tooltabs.AbstractToolTab;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TerminalToolTab extends JPanel implements AbstractToolTab {

    private EditorWindow editor;
    private HTerminal terminal;
    private TtyConnector connector;
    private CActionBar actionBar;
    private PtyProcess process;

    public TerminalToolTab(EditorWindow editor) {
        super();
        this.editor = editor;
        this.connector = createTtyConnector();

        terminal = new HTerminal(new TerminalSettingsProvider());
        terminal.getTermWidget().setTtyConnector(connector);
        terminal.getTermWidget().start();

        terminal.getTermWidget().getScrollBar().setUI(new FlatScrollBarUI());

        actionBar = new CActionBar();
        actionBar.addAction(new CHSpacerAction());
        setLayout(new BorderLayout());
        add(actionBar, BorderLayout.PAGE_START);
        add(terminal, BorderLayout.CENTER);

    }

    public TtyConnector createTtyConnector() {
        try {
            Map<String, String> envs = System.getenv();
            String[] command;
            if (UIUtil.isWindows) {
                command = new String[] {"cmd.exe"};
            } else {
                command = new String[]{"/bin/bash", "--login"};
                envs = new HashMap<>(System.getenv());
                envs.put("TERM", "xterm-256color");
            }
            process = new PtyProcessBuilder().setCommand(command).setEnvironment(envs).start();
            return new PtyProcessTtyConnector(process, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void stop() {
        terminal.stopAndClose(process);
    }

    @Override
    public String name() {
        return "Terminal";
    }

    @Override
    public Icon icon() {
        return new CShellIcon(16);
    }

    @Override
    public JComponent buildToolTab(EditorWindow editor, JButton hideToolTabButton) {
        actionBar.addAction(new CButtonAction(hideToolTabButton));
        return this;
    }

    public EditorWindow getEditor() {
        return editor;
    }

    public JediTermWidget getTerminal() {
        return terminal.getTermWidget();
    }

    public TtyConnector getConnector() {
        return connector;
    }

}
