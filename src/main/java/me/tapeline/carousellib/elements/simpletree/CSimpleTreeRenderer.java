package me.tapeline.carousellib.elements.simpletree;

import me.tapeline.carousellib.elements.simpletree.model.CTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class CSimpleTreeRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        if (!(value instanceof CTreeNode)) return this;
        CTreeNode treeNode = (CTreeNode) value;
        setIcon(treeNode.getIcon());
        return this;
    }

}
