package me.tapeline.carousellib.elements.simpletree.model;

import javax.swing.*;

public class CTreeBranch extends CTreeNode {

    public CTreeBranch(Icon icon, String title, CTreeNode... children) {
        super(icon, title);
        setAllowsChildren(true);
        setUserObject(title);
        for (CTreeNode child : children)
            add(child);
    }

    public CTreeBranch(String title, CTreeNode... children) {
        this(null, title, children);
    }

}
