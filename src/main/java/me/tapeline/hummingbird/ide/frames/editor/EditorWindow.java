package me.tapeline.hummingbird.ide.frames.editor;

import me.tapeline.carousellib.elements.closabletabs.CClosableTabbedPane;
import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.crumbdisplay.CCrumbDisplay;
import me.tapeline.carousellib.elements.slotpanel.CSimpleTab;
import me.tapeline.carousellib.icons.navigation.CDownIcon;
import me.tapeline.carousellib.icons.navigation.CMenuIcon;
import me.tapeline.carousellib.icons.navigation.CPackageIcon;
import me.tapeline.carousellib.icons.state.CWarningIcon;
import me.tapeline.hummingbird.ide.FS;
import me.tapeline.hummingbird.ide.exceptions.ProjectDirectoryException;
import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;
import me.tapeline.hummingbird.ide.frames.AppWindow;
import me.tapeline.hummingbird.ide.project.Project;
import me.tapeline.hummingbird.ide.ui.filetree.FileTreeNode;
import me.tapeline.hummingbird.ide.ui.filetree.HFileTree;
import me.tapeline.hummingbird.ide.ui.studiopanel.StudioPanel;
import me.tapeline.hummingbird.ide.ui.tabs.AbstractWorkspaceTab;
import me.tapeline.hummingbird.ide.ui.tabs.DefaultCodeEditorTab;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;

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
    private HFileTree fileTree;
    private JScrollPane treeScroll;

    public EditorWindow(Project project) throws ProjectDirectoryException {
        //super("Hummingbird - " + project.getRoot().getName());
        super("Hummingbird - ");

        setupUI();

        fileTree.setProject(new Project(new File("src/test/resources/testProj")));

        studioPanel.getBottomTabs().addTab(
                new CWarningIcon(16, 16),
                "Problems",
                new CSimpleTab(new JLabel("No problems"))
        );

        leftStatus.setText("Ready");
        rightStatus.setText("|/ master");

        studioPanel.getLeftTabs().addTab(new CPackageIcon(12), "Project", new CSimpleTab(
                treeScroll
        ));
        studioPanel.getLeftTabs().addTab(new CMenuIcon(12), "Tasks", new CSimpleTab(
                new JLabel("No tasks")
        ));

        studioPanel.getRightTabs().addTab(new CDownIcon(12), "Logs", new CSimpleTab(
                new JLabel("No logs")
        ));

        pathCrumbs.addCrumb("Test");
        pathCrumbs.addCrumb("Test");
        pathCrumbs.addCrumb("Test");
        pathCrumbs.addCrumb("Test");

        setContentPane(root);

        setSize(900, 600);
        centerOnScreen();
        setVisible(true);
    }

    private void setupUI() {
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

        fileTree = new HFileTree(this);
        treeScroll = new JScrollPane(fileTree);
        fileTree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = fileTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = fileTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1 && e.getClickCount() == 2) {
                    if (selPath.getLastPathComponent() instanceof FileTreeNode fileTreeNode)
                        openFile(fileTreeNode.getFile());
                }
            }
        });
    }

    public void openFile(File file) {
        AbstractFileType fileType = FS.getFileType(file);
        if (fileType.doCustomOpen()) {
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
    }

    public HFileTree getFileTree() {
        return fileTree;
    }

}
