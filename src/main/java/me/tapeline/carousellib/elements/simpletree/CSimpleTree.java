package me.tapeline.carousellib.elements.simpletree;

import me.tapeline.carousellib.elements.simpletree.model.CTreeRoot;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class CSimpleTree extends JTree {

    private CTreeRoot root;

    public CSimpleTree() {
        super();
        setCellRenderer(new CSimpleTreeRenderer());
    }

    public void setRootItem(CTreeRoot root) {
        this.root = root;
        updateRoot();
    }

    public CTreeRoot getRootItem() {
        return root;
    }

    private void updateRoot() {
        if (root == null) return;
        setModel(new DefaultTreeModel(root));
    }

}
