package me.tapeline.hummingbird.ui;

import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.filesystem.project.fsmodel.FileSystemModel;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;
import java.io.File;

public class JFileTree extends JTree {

    public Project project;
    public FileSystemModel fileSystemModel;
    public JPopupMenu popup;

    public JFileTree() {
        setCellRenderer(new IconTreeNodeRenderer());
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setShowsRootHandles(true);
        setEditable(false);

        popup = new JPopupMenu();
        add(popup);
        addMouseListener(new PopupTrigger(this));
    }

    public JFileTree(DefaultTreeModel model) {
        setCellRenderer(new IconTreeNodeRenderer());
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setShowsRootHandles(true);
        setEditable(false);

        popup = new JPopupMenu();
        add(popup);
        addMouseListener(new PopupTrigger(this));
    }

    public JFileTree(Project project) {

        this.project = project;
        fileSystemModel = project.fileSystem;
        setCellRenderer(new IconTreeNodeRenderer());
        setModel(new DefaultTreeModel(fileSystemModel.toTree()));
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setShowsRootHandles(true);
        setEditable(false);

        popup = new JPopupMenu();
        add(popup);
        addMouseListener(new PopupTrigger(this));
    }


    public void setProject(Project project) {
        this.project = project;
        fileSystemModel = project.fileSystem;
        setModel(new DefaultTreeModel(fileSystemModel.toTree()));
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("F");
        JLabel l = new JLabel();
        JFileTree ft = new JFileTree(new Project(new File("fff")));
        f.add(l);
        f.add(ft);
        f.pack();
        f.setVisible(true);
    }

    static class PopupTrigger extends MouseAdapter {

        private final JFileTree tree;

        public PopupTrigger(JFileTree tree) {
            this.tree = tree;
        }

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
                int x = e.getX();
                int y = e.getY();
                TreePath path = tree.getPathForLocation(x, y);
                if (path != null) {
                    FileTreeNode ftn = (FileTreeNode) path.getLastPathComponent();
                    File file = ftn.file;
                    AbstractFileType aft = FS.getTypeForFile(file);
                    System.out.println(aft);
                    if (aft == null) return;
                    tree.popup.removeAll();
                    aft.setupContextActions(tree.popup, file, tree.project);
                    tree.popup.show(tree, x, y);
                }
            }
        }
    }

}
