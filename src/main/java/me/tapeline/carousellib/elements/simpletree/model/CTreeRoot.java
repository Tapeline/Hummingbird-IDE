package me.tapeline.carousellib.elements.simpletree.model;

import javax.swing.*;

public class CTreeRoot extends CTreeBranch {

    public CTreeRoot(Icon icon, String title, CTreeNode... children) {
        super(icon, title, children);
    }

    public CTreeRoot(String title, CTreeNode... children) {
        super(title, children);
    }

}
