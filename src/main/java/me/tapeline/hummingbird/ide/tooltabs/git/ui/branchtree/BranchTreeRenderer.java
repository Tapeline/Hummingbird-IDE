package me.tapeline.hummingbird.ide.tooltabs.git.ui.branchtree;

import me.tapeline.carousellib.icons.items.git.CBranchIcon;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.IOException;

public class BranchTreeRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value,
                sel, expanded, leaf, row, hasFocus);
        if (value instanceof CategoryBranchTreeNode cat) {
            setIcon(null);
            setText(cat.getName());
        } else if (value instanceof RefBranchTreeNode ref) {
            boolean isCurrent = false;
            try {
                String head = ((BranchTree) tree).getRepository().getFullBranch();
                isCurrent = head.equals(ref.getRef().getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            setIcon(new CBranchIcon(16, isCurrent));
            setText(ref.getRef().getName());
        }
        return this;
    }
}
