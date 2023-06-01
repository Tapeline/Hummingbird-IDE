package me.tapeline.carousellib.elements.simpletree.model;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public abstract class CTreeNode extends DefaultMutableTreeNode {

    protected Icon icon;
    protected String title;

    public CTreeNode(String title) {
        super(title);
        this.title = title;
    }

    public CTreeNode(Icon icon, String title) {
        super(title);
        this.icon = icon;
        this.title = title;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

}
