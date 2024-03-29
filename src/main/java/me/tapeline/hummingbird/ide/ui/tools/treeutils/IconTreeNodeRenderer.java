package me.tapeline.hummingbird.ide.ui.tools.treeutils;

import me.tapeline.carousellib.icons.navigation.CDownIcon;
import me.tapeline.carousellib.icons.navigation.CRightIcon;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class IconTreeNodeRenderer extends DefaultTreeCellRenderer {

    public IconTreeNodeRenderer() {
        setClosedIcon(new CDownIcon(16));
        setOpenIcon(new CRightIcon(16));
    }

    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        if (value instanceof IconTreeNode) {
            IconTreeNode.TreeNodeData data = (IconTreeNode.TreeNodeData) ((IconTreeNode) value).getUserObject();
            setIcon(data.getIcon());
            setText(data.getText());
        }
        return this;
    }

}