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
import me.tapeline.hummingbird.ide.exceptions.ProjectDirectoryException;
import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;
import me.tapeline.hummingbird.ide.frames.AppWindow;
import me.tapeline.hummingbird.ide.frames.editor.tooltabs.EventsToolTab;
import me.tapeline.hummingbird.ide.frames.editor.tooltabs.ProjectToolTab;
import me.tapeline.hummingbird.ide.frames.editor.tooltabs.TerminalToolTab;
import me.tapeline.hummingbird.ide.frames.editor.tooltabs.TodoToolTab;
import me.tapeline.hummingbird.ide.frames.runconfigs.RunConfigurationsDialog;
import me.tapeline.hummingbird.ide.project.Project;
import me.tapeline.hummingbird.ide.ui.filetree.HFileTree;
import me.tapeline.hummingbird.ide.ui.studiopanel.StudioPanel;
import me.tapeline.hummingbird.ide.ui.tabs.AbstractWorkspaceTab;
import me.tapeline.hummingbird.ide.ui.tabs.DefaultCodeEditorTab;
import me.tapeline.hummingbird.ide.ui.tools.RunConfigurationListRenderer;
import me.tapeline.hummingbird.ide.ui.tooltabs.AbstractToolTab;
import me.tapeline.hummingbird.ide.ui.tooltabs.HideTabButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
    private JComboBox<Object> runConfigurationBox;
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
                    project.save();
                } catch (ProjectDirectoryException e) {
                    logger.log(Level.SEVERE, "Exception while exiting", e);
                }
                Application.instance.saveConfig();
                dispose();
            }
        });

        projectToolTab.getFileTree().setProject(project);

        addLeftToolTab(projectToolTab);
        addBottomToolTab(todoToolTab);
        addRightToolTab(eventsToolTab);
        addBottomToolTab(terminalToolTab);

        leftStatus.setText("Ready");
        rightStatus.setText("|/ master");

        pathCrumbs.addCrumb("Test");
        pathCrumbs.addCrumb("Test");
        pathCrumbs.addCrumb("Test");
        pathCrumbs.addCrumb("Test");

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
        });

        projectToolTab = new ProjectToolTab(this);
        todoToolTab = new TodoToolTab(this);
        eventsToolTab = new EventsToolTab(this);
        terminalToolTab = new TerminalToolTab(this);

        runConfigurationStartButton.setIcon(new CPlayIcon(16));
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

    public HFileTree getFileTree() {
        return projectToolTab.getFileTree();
    }

    public StudioPanel getStudioPanel() {
        return studioPanel;
    }

}
