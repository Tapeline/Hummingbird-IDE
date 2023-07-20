package me.tapeline.hummingbird.ide.tooltabs.run.ui;

import com.formdev.flatlaf.ui.FlatScrollBarUI;
import com.jediterm.pty.PtyProcessTtyConnector;
import com.jediterm.terminal.TtyConnector;
import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.actionbar.CButtonAction;
import me.tapeline.carousellib.icons.commonactions.CPlayIcon;
import me.tapeline.carousellib.icons.commonactions.CStopIcon;
import me.tapeline.hummingbird.ide.expansion.runconfigs.AbstractConfigurationRunner;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;
import me.tapeline.hummingbird.ide.tooltabs.terminal.data.TerminalSettingsProvider;
import me.tapeline.hummingbird.ide.tooltabs.run.RunToolTab;
import me.tapeline.hummingbird.ide.ui.terminal.HTerminal;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RunTaskTab extends JPanel {

    private HTerminal terminal;
    private CActionBar leftActions;
    private String[] command;
    private Map<String, String> environmentVariables;
    private RunConfiguration runningTask;
    private AbstractConfigurationRunner runner;
    private RunToolTab toolTab;
    private TtyConnector connector;
    private PtyProcess process;
    private Thread exitCodePrinter;

    public RunTaskTab(RunToolTab toolTab, String[] command, Map<String, String> environmentVariables,
                      RunConfiguration runningTask, AbstractConfigurationRunner runner) {
        super();
        this.toolTab = toolTab;
        this.command = command;
        this.environmentVariables = environmentVariables;
        this.runningTask = runningTask;
        this.runner = runner;

        terminal = new HTerminal(new TerminalSettingsProvider());
        terminal.getTermWidget().getScrollBar().setUI(new FlatScrollBarUI());
        setLayout(new BorderLayout());
        leftActions = new CActionBar(BoxLayout.PAGE_AXIS);
        add(terminal, BorderLayout.CENTER);
        add(leftActions, BorderLayout.LINE_START);
        leftActions.addAction(new CButtonAction(
                null,
                new CPlayIcon(16),
                (action, component) -> resetAndRun()
        ));
        leftActions.addAction(new CButtonAction(
                null,
                new CStopIcon(16),
                (action, component) -> stop()
        ));
    }

    public void resetAndRun() {
        terminal.getTermWidget().getTerminalPanel().clearBuffer();
        try {
            Map<String, String> envs = new HashMap<>(System.getenv());
            envs.putAll(environmentVariables);
            process = new PtyProcessBuilder().setCommand(command).setEnvironment(envs).start();
            connector = new PtyProcessTtyConnector(process, StandardCharsets.UTF_8);
            terminal.getTermWidget().setTtyConnector(connector);
            exitCodePrinter = new Thread() {
                private String status = "Process state unknown.";

                public void run() {
                    try {
                        int exitCode = process.waitFor();
                        status = "Process finished. Exit code " + exitCode;
                    } catch (InterruptedException e) {
                        status = "Process interrupted";
                    }
                    terminal.getTermWidget().getTerminal().newLine();
                    terminal.getTermWidget().getTerminal().writeCharacters(status);
                    terminal.scrollToBottom();
                }
            };
            //exitCodePrinter.start();
        } catch (Exception e) {
            Dialogs.exception("Error", "Error while creating process", e);
        }
        //terminal.getTermWidget().start();
    }

    public void stop() {
        terminal.getTermWidget().stop();
        exitCodePrinter.interrupt();
    }

    public void stopFully() {
        terminal.stopAndClose(process);
        exitCodePrinter.interrupt();
    }

    public HTerminal getTerminal() {
        return terminal;
    }

    public CActionBar getLeftActions() {
        return leftActions;
    }

    public String[] getCommand() {
        return command;
    }

    public Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }

    public RunConfiguration getRunningTask() {
        return runningTask;
    }

    public AbstractConfigurationRunner getRunner() {
        return runner;
    }

    public RunToolTab getToolTab() {
        return toolTab;
    }

    public TtyConnector getConnector() {
        return connector;
    }

}
