package me.tapeline.hummingbird.ide.frames.editor.tooltabs;

import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.actionbar.CButtonAction;
import me.tapeline.carousellib.elements.actionbar.CHSpacerAction;
import me.tapeline.carousellib.elements.closabletabs.CClosableTabbedPane;
import me.tapeline.carousellib.icons.commonactions.CPlayIcon;
import me.tapeline.hummingbird.ide.expansion.runconfigs.AbstractConfigurationRunner;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;
import me.tapeline.hummingbird.ide.expansion.runconfigs.TerminalConfiguration;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.ui.tooltabs.AbstractToolTab;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class RunToolTab extends JPanel implements AbstractToolTab {

    private EditorWindow editor;
    private HashMap<String, RunTaskTab> tasks = new HashMap<>();
    private CActionBar actionBar;
    private CClosableTabbedPane taskTabs;

    public RunToolTab(EditorWindow editor) {
        super();
        this.editor = editor;

        actionBar = new CActionBar();
        actionBar.addAction(new CHSpacerAction());

        taskTabs = new CClosableTabbedPane();
        taskTabs.addTabCloseListener(event -> {
            if (event.getTab().getBoundComponent() instanceof RunTaskTab tab) {
                tasks.remove(tab.getRunningTask().getName());
                tab.stopFully();
            }
        });

        setLayout(new BorderLayout());
        add(actionBar, BorderLayout.PAGE_START);
        add(taskTabs, BorderLayout.CENTER);
    }

    public void createTask(TerminalConfiguration configuration, RunConfiguration runningTask,
                           AbstractConfigurationRunner runner) {
        if (tasks.containsKey(runningTask.getName())) {
            tasks.get(runningTask.getName()).resetAndRun();
            return;
        }
        RunTaskTab tab = new RunTaskTab(this, configuration.getCommand(),
                configuration.getEnv(), runningTask, runner);
        tasks.put(runningTask.getName(), tab);
        taskTabs.addTab(runningTask.getName(), runner.icon(), tab);
        tab.resetAndRun();
    }

    public void stopAll() {
        for (RunTaskTab tab : tasks.values())
            tab.stopFully();
    }

    @Override
    public String name() {
        return "Run";
    }

    @Override
    public Icon icon() {
        return new CPlayIcon(16);
    }

    @Override
    public JComponent buildToolTab(EditorWindow editor, JButton hideToolTabButton) {
        actionBar.addAction(new CButtonAction(hideToolTabButton));
        return this;
    }

    public EditorWindow getEditor() {
        return editor;
    }

}
