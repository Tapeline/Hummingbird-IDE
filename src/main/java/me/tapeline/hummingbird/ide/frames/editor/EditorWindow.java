package me.tapeline.hummingbird.ide.frames.editor;

import me.tapeline.carousellib.elements.closabletabs.CClosableTabbedPane;
import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.crumbdisplay.CCrumbDisplay;
import me.tapeline.carousellib.elements.slotpanel.CSimpleTab;
import me.tapeline.carousellib.icons.navigation.CDownIcon;
import me.tapeline.carousellib.icons.navigation.CMenuIcon;
import me.tapeline.carousellib.icons.navigation.CPackageIcon;
import me.tapeline.carousellib.icons.state.CWarningIcon;
import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.FS;
import me.tapeline.hummingbird.ide.exceptions.ProjectDirectoryException;
import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;
import me.tapeline.hummingbird.ide.frames.AppWindow;
import me.tapeline.hummingbird.ide.frames.editor.tooltabs.EventsToolTab;
import me.tapeline.hummingbird.ide.frames.editor.tooltabs.ProjectToolTab;
import me.tapeline.hummingbird.ide.frames.editor.tooltabs.TodoToolTab;
import me.tapeline.hummingbird.ide.project.Project;
import me.tapeline.hummingbird.ide.ui.filetree.FileTreeNode;
import me.tapeline.hummingbird.ide.ui.filetree.HFileTree;
import me.tapeline.hummingbird.ide.ui.studiopanel.StudioPanel;
import me.tapeline.hummingbird.ide.ui.tabs.AbstractWorkspaceTab;
import me.tapeline.hummingbird.ide.ui.tabs.DefaultCodeEditorTab;
import me.tapeline.hummingbird.ide.ui.tooltabs.AbstractToolTab;
import me.tapeline.hummingbird.ide.ui.tooltabs.HideTabButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private CActionBar upperActionBar;
    private StudioPanel studioPanel;
    private CCrumbDisplay pathCrumbs;
    private CClosableTabbedPane workspaceTabs;

    private ProjectToolTab projectToolTab;
    private TodoToolTab todoToolTab;
    private EventsToolTab eventsToolTab;

    private List<AbstractToolTab> bottomToolTabs = new ArrayList<>();
    private List<AbstractToolTab> leftToolTabs = new ArrayList<>();
    private List<AbstractToolTab> rightToolTabs = new ArrayList<>();
    private Logger logger;

    public EditorWindow(Project project) throws ProjectDirectoryException {
        //super("Hummingbird - " + project.getRoot().getName());
        super("Hummingbird - ");
        logger = Application.instance.getLogger();

        setupUI();

        projectToolTab.getFileTree().setProject(
                new Project(new File("src/test/resources/testProj")));

        addLeftToolTab(projectToolTab);
        addBottomToolTab(todoToolTab);
        addRightToolTab(eventsToolTab);

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
        logger.severe("WASSUP BEIJING");
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

    public HFileTree getFileTree() {
        return projectToolTab.getFileTree();
    }

    public StudioPanel getStudioPanel() {
        return studioPanel;
    }

}
