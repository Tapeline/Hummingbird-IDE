package me.tapeline.hummingbird.ide.frames.editor;

import me.tapeline.carousellib.configuration.exceptions.FieldNotFoundException;
import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.carousellib.elements.closabletabs.CClosableTabbedPane;
import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.crumbdisplay.CCrumbDisplay;
import me.tapeline.carousellib.elements.slotpanel.CSimpleTab;
import me.tapeline.carousellib.icons.commonactions.CPlayIcon;
import me.tapeline.carousellib.icons.commonactions.CStopIcon;
import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.FS;
import me.tapeline.hummingbird.ide.Registry;
import me.tapeline.hummingbird.ide.exceptions.ProjectDirectoryException;
import me.tapeline.hummingbird.ide.exceptions.runconfigs.ConfigurationRunException;
import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;
import me.tapeline.hummingbird.ide.expansion.runconfigs.AbstractConfigurationRunner;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;
import me.tapeline.hummingbird.ide.expansion.runconfigs.TerminalConfiguration;
import me.tapeline.hummingbird.ide.frames.AppWindow;
import me.tapeline.hummingbird.ide.tooltabs.events.EventsToolTab;
import me.tapeline.hummingbird.ide.tooltabs.git.GitToolTab;
import me.tapeline.hummingbird.ide.tooltabs.project.ProjectToolTab;
import me.tapeline.hummingbird.ide.tooltabs.run.RunToolTab;
import me.tapeline.hummingbird.ide.tooltabs.terminal.TerminalToolTab;
import me.tapeline.hummingbird.ide.tooltabs.todo.TodoToolTab;
import me.tapeline.hummingbird.ide.frames.runconfigs.RunConfigurationsDialog;
import me.tapeline.hummingbird.ide.project.Project;
import me.tapeline.hummingbird.ide.ui.filetree.HFileTree;
import me.tapeline.hummingbird.ide.ui.studiopanel.StudioPanel;
import me.tapeline.hummingbird.ide.ui.tabs.AbstractWorkspaceTab;
import me.tapeline.hummingbird.ide.ui.tabs.DefaultCodeEditorTab;
import me.tapeline.hummingbird.ide.ui.tabs.FileReferencingTab;
import me.tapeline.hummingbird.ide.ui.tools.RunConfigurationListRenderer;
import me.tapeline.hummingbird.ide.ui.tooltabs.AbstractToolTab;
import me.tapeline.hummingbird.ide.ui.tooltabs.HideTabButton;
import me.tapeline.hummingbird.ide.utils.PathUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class EditorWindow extends AppWindow {

    private JPanel root;
    private JPanel statusBar;
    private JLabel rightStatus;
    private JLabel leftStatus;
    private JPanel upperToolbar;
    private JPanel upperActionBarContainer;
    private JPanel centralContainer;
    private JPanel pathCrumbContainer;
    private JComboBox<RunConfiguration> runConfigurationBox;
    private JButton runConfigurationStartButton;
    private JButton runConfigurationStopButton;
    private JButton runConfigurationEditButton;
    private CActionBar upperActionBar;
    private StudioPanel studioPanel;
    private CCrumbDisplay pathCrumbs;
    private CClosableTabbedPane workspaceTabs;

    private ProjectToolTab projectToolTab;
    private TodoToolTab todoToolTab;
    private EventsToolTab eventsToolTab;
    private TerminalToolTab terminalToolTab;
    private RunToolTab runToolTab;
    private GitToolTab gitToolTab;
    private List<AbstractToolTab> bottomToolTabs = new ArrayList<>();
    private List<AbstractToolTab> leftToolTabs = new ArrayList<>();
    private List<AbstractToolTab> rightToolTabs = new ArrayList<>();

    private Logger logger;
    private Project project;

    public EditorWindow(Project project) throws ProjectDirectoryException {
        super("Hummingbird - " + project.getRoot().getName());
        this.project = project;
        logger = Application.instance.getLogger();

        try {
            List<String> lastProjects = (List<String>) Application.instance.getConfiguration()
                    .latestRun().getList("projectHistory");
            boolean contains = false;
            for (String projectPathString : lastProjects)
                if (new File(projectPathString).getAbsoluteFile().equals(project.getRoot().getAbsoluteFile())) {
                    contains = true;
                    break;
                }
            if (!contains) {
                lastProjects.add(project.getRoot().getAbsolutePath());
                Application.instance.getConfiguration().latestRun().set("projectHistory", lastProjects);
            }
        } catch (FieldNotFoundException e) {
            Dialogs.exception("Error", "Error logging opened project", e);
        }

        setupUI();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                boolean exit = Dialogs.confirmYesNo(null, "Exiting",
                        "Do you really want to exit?");
                if (!exit) return;
                try {
                    terminalToolTab.stop();
                    runToolTab.stopAll();
                    project.save();
                } catch (ProjectDirectoryException e) {
                    logger.log(Level.SEVERE, "Exception while exiting", e);
                }
                Application.instance.saveConfig();
                dispose();
                Thread.getAllStackTraces().keySet().forEach((t) -> System.out.println(t.getName() + "\nIs Daemon " + t.isDaemon() + "\nIs Alive " + t.isAlive() + "\n"));
            }
        });

        projectToolTab.getFileTree().setProject(project);
        setPathCrumbFile(null);

        addLeftToolTab(projectToolTab);
        addRightToolTab(eventsToolTab);
        addBottomToolTab(todoToolTab);
        addBottomToolTab(terminalToolTab);
        addBottomToolTab(runToolTab);
        addBottomToolTab(gitToolTab);

        leftStatus.setText("Ready");
        logger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                leftStatus.setText(record.getMessage());
            }
            public void flush() { }
            public void close() throws SecurityException { }
        });
        rightStatus.setText("|/ master");

        setContentPane(root);

        setSize(900, 600);
        centerOnScreen();
        setVisible(true);

        logger.info("Editor created");
    }

    private void setupUI() {
        logger.info("Creating UI for editor");
        centralContainer.setLayout(new BorderLayout());
        studioPanel = new StudioPanel();
        studioPanel.setBorder(new EmptyBorder(0, 4, 0, 4));
        centralContainer.add(studioPanel, BorderLayout.CENTER);

        upperActionBar = new CActionBar();
        upperActionBarContainer.setLayout(new BoxLayout(upperActionBarContainer, BoxLayout.LINE_AXIS));
        upperActionBarContainer.add(upperActionBar);

        pathCrumbContainer.setLayout(new BorderLayout());
        pathCrumbs = new CCrumbDisplay();
        pathCrumbContainer.add(pathCrumbs, BorderLayout.CENTER);

        workspaceTabs = new CClosableTabbedPane();
        studioPanel.setCentralWidget(workspaceTabs);
        workspaceTabs.addTabCloseListener((e) -> {
            if (e.getTab().getBoundComponent() instanceof AbstractWorkspaceTab tab)
                tab.save();
            updatePathCrumbs();
        });
        workspaceTabs.addChangeListener(e -> updatePathCrumbs());

        projectToolTab = new ProjectToolTab(this);
        todoToolTab = new TodoToolTab(this);
        eventsToolTab = new EventsToolTab(this);
        terminalToolTab = new TerminalToolTab(this);
        runToolTab = new RunToolTab(this);
        gitToolTab = new GitToolTab(this);

        runConfigurationStartButton.setIcon(new CPlayIcon(16));
        runConfigurationStartButton.addActionListener(e -> {
            if (runConfigurationBox.getSelectedItem() == null) return;
            RunConfiguration configuration = (RunConfiguration) runConfigurationBox.getSelectedItem();
            AbstractConfigurationRunner runner =
                    Registry.getConfigurationRunner(configuration.getRunner());
            if (runner == null) {
                Dialogs.error(EditorWindow.this, "Error",
                        "Unknown configuration runner " + configuration.getRunner());
                return;
            }
            TerminalConfiguration terminalConfiguration;
            try {
                terminalConfiguration = runner.run(configuration);
            } catch (ConfigurationRunException exception) {
                Dialogs.exception(
                        "Error",
                        "Error while running configuration " + configuration.getName(),
                        exception
                );
                return;
            }
            runToolTab.createTask(terminalConfiguration, configuration, runner);
            if (studioPanel.getDividerPositions().isBottomHidden()) {
                studioPanel.getBottomTabs().manuallyChange(2);
                studioPanel.showBottom();
            }
        });
        runConfigurationStopButton.setIcon(new CStopIcon(16));
        runConfigurationBox.setRenderer(new RunConfigurationListRenderer());
        runConfigurationEditButton.addActionListener(e -> {
            RunConfigurationsDialog dialog = null;
            try {
                dialog = new RunConfigurationsDialog(
                        EditorWindow.this,
                        new ArrayList<>((List<RunConfiguration>) project.getConfiguration().running()
                                .getList("configurations"))
                );
                dialog.dialog();
                List<RunConfiguration> result = dialog.getResult();
                if (result == null) return;
                project.getConfiguration().running().set("configurations", result);
                refreshConfigurations();
            } catch (FieldNotFoundException ex) {
                Dialogs.error(EditorWindow.this, "Error", ex.toString());
            }
        });
        refreshConfigurations();
    }

    public void openFile(File file) {
        logger.info("Trying to open file " + file);
        AbstractFileType fileType = FS.getFileType(file);
        if (fileType.doCustomOpen()) {
            logger.info("Delegated opening to " + fileType.getClass().getName());
            fileType.customOpen(this, file);
            return;
        }
        DefaultCodeEditorTab tab = new DefaultCodeEditorTab(this, file);
        addTabRaw(file.getName(), fileType.icon(), tab);
    }

    public void addTabRaw(String name, Icon icon, AbstractWorkspaceTab tab) {
        for (Component component : workspaceTabs.getComponents())
            if (component instanceof AbstractWorkspaceTab workspaceTab)
                if (workspaceTab.getIdentifier().equals(tab.getIdentifier())) {
                    workspaceTabs.setSelectedComponent(workspaceTab);
                    return;
                }
        workspaceTabs.addTab(name, icon, tab);
        logger.info("Added tab '" + name + "'");
    }

    public void addBottomToolTab(AbstractToolTab toolTab) {
        bottomToolTabs.add(toolTab);
        JComponent tabComponent = toolTab.buildToolTab(this,
                new HideTabButton(this, HideTabButton.BOTTOM));
        studioPanel.getBottomTabs().addTab(toolTab.icon(), toolTab.name(), new CSimpleTab(tabComponent));
        logger.info("Added bottom tool tab '" + toolTab.name() + "'");
    }

    public void addLeftToolTab(AbstractToolTab toolTab) {
        leftToolTabs.add(toolTab);
        JComponent tabComponent = toolTab.buildToolTab(this,
                new HideTabButton(this, HideTabButton.LEFT));
        studioPanel.getLeftTabs().addTab(toolTab.icon(), toolTab.name(), new CSimpleTab(tabComponent));
        logger.info("Added left tool tab '" + toolTab.name() + "'");
    }

    public void addRightToolTab(AbstractToolTab toolTab) {
        rightToolTabs.add(toolTab);
        JComponent tabComponent = toolTab.buildToolTab(this,
                new HideTabButton(this, HideTabButton.RIGHT));
        studioPanel.getRightTabs().addTab(toolTab.icon(), toolTab.name(), new CSimpleTab(tabComponent));
        logger.info("Added right tool tab '" + toolTab.name() + "'");
    }

    public void setPathCrumbFile(File file) {
        if (file == null) {
            setPathCrumbData(Collections.singletonList(project.getRoot().getName()));
            return;
        }
        List<File> roots = PathUtils.split(file, project.getRoot());
        List<String> names = new ArrayList<>();
        for (File root : roots)
            names.add(root.getName());
        setPathCrumbData(names);
    }

    public void setPathCrumbData(List<String> data) {
        pathCrumbs.getCrumbs().clear();
        pathCrumbs.reconstruct();
        for (String crumb : data)
            pathCrumbs.addCrumb(crumb);
    }

    public void refreshConfigurations() {
        runConfigurationBox.removeAllItems();
        List<?> configurations = new ArrayList<>();
        try {
            configurations = project.getConfiguration().running().getList("configurations");
        } catch (FieldNotFoundException ignored) {}
        for (Object object : configurations)
            if (object instanceof RunConfiguration runConfiguration)
                runConfigurationBox.addItem(runConfiguration);
    }

    private void updatePathCrumbs() {
        if (workspaceTabs.getSelectedComponent() instanceof FileReferencingTab tab)
            setPathCrumbFile(tab.getFile());
        else
            setPathCrumbFile(null);
    }

    public HFileTree getFileTree() {
        return projectToolTab.getFileTree();
    }

    public StudioPanel getStudioPanel() {
        return studioPanel;
    }

    public Project getProject() {
        return project;
    }

}
