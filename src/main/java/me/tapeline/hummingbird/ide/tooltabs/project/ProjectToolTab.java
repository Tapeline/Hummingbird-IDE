package me.tapeline.hummingbird.ide.tooltabs.project;

import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.actionbar.CButtonAction;
import me.tapeline.carousellib.elements.actionbar.CHSpacerAction;
import me.tapeline.carousellib.elements.actionbar.CLabelAction;
import me.tapeline.carousellib.icons.items.CPlainFolderIcon;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.ui.filetree.FileTreeNode;
import me.tapeline.hummingbird.ide.ui.filetree.HFileTree;
import me.tapeline.hummingbird.ide.ui.tooltabs.AbstractToolTab;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProjectToolTab extends JPanel implements AbstractToolTab {

    private HFileTree fileTree;
    private CActionBar actionBar;
    private JScrollPane treeScroll;
    private EditorWindow editor;

    public ProjectToolTab(EditorWindow editor) {
        super();
        this.editor = editor;
        setLayout(new BorderLayout());
        actionBar = new CActionBar();
        actionBar.setBorder(new EmptyBorder(0, 8, 0, 0));
        add(actionBar, BorderLayout.PAGE_START);
        fileTree = new HFileTree(editor);
        treeScroll = new JScrollPane(fileTree);
        fileTree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = fileTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = fileTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1 && e.getClickCount() == 2) {
                    if (selPath.getLastPathComponent() instanceof FileTreeNode fileTreeNode)
                        editor.openFile(fileTreeNode.getFile());
                }
            }
        });
        add(fileTree, BorderLayout.CENTER);
    }

    @Override
    public String name() {
        return "Project";
    }

    @Override
    public Icon icon() {
        return new CPlainFolderIcon(14);
    }

    @Override
    public JComponent buildToolTab(EditorWindow editor, JButton hideToolTabButton) {
        CLabelAction label = new CLabelAction("Project", new CPlainFolderIcon(16));
        actionBar.addAction(label);
        actionBar.addAction(new CHSpacerAction());
        actionBar.addAction(new CButtonAction(hideToolTabButton));
        return this;
    }

    public HFileTree getFileTree() {
        return fileTree;
    }

    public CActionBar getActionBar() {
        return actionBar;
    }

    public JScrollPane getTreeScroll() {
        return treeScroll;
    }

}
