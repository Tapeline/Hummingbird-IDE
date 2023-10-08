package me.tapeline.hummingbird.ide.ui.filetree;

import me.tapeline.hummingbird.ide.FS;
import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.project.Project;
import me.tapeline.hummingbird.ide.ui.tools.treeutils.IconTreeNodeRenderer;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class HFileTree extends JTree {

    protected Project project;
    protected JPopupMenu popup;
    protected EditorWindow editor;
    protected FileTreeNode rootNode;
    protected FileTreeModel model;

    public HFileTree(EditorWindow editor) {
        this.editor = editor;

        setCellRenderer(new IconTreeNodeRenderer());
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setShowsRootHandles(true);
        setEditable(false);

        popup = new JPopupMenu();
        add(popup);
        addMouseListener(new PopupTrigger(this));
        addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                Object lastComponent = event.getPath().getLastPathComponent();
                if (lastComponent instanceof FileTreeNode fileTreeNode)
                    model.updateNode(fileTreeNode);
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event) { }
        });
    }

    public HFileTree(EditorWindow editor, Project project) {
        this(editor);
        setProject(project);
    }


    public void setProject(Project project) {
        this.project = project;
        model = new FileTreeModel(this, project.getRoot());
        setModel(model);
        refresh();
    }

    public void refresh() {
        model.updateNode(model.getRootNode());
    }

    static class PopupTrigger extends MouseAdapter {
        private final HFileTree tree;

        public PopupTrigger(HFileTree tree) {
            this.tree = tree;
        }

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
                int x = e.getX();
                int y = e.getY();
                TreePath path = tree.getPathForLocation(x, y);
                if (path != null) {
                    tree.setSelectionPath(path);
                    FileTreeNode node = (FileTreeNode) path.getLastPathComponent();
                    File file = node.file;
                    AbstractFileType fileType = FS.getFileType(file);
                    if (fileType == null) return;
                    tree.popup.removeAll();
                    fileType.setupContextMenu(tree.editor, tree.popup, file, tree.project);
                    tree.popup.show(tree, x, y);
                }
            }
        }
    }

}