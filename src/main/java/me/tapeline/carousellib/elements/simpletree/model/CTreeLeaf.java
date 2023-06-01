package me.tapeline.carousellib.elements.simpletree.model;

import javax.swing.*;

public class CTreeLeaf extends CTreeNode {

    public CTreeLeaf(Icon icon, String title) {
        super(icon, title);
        setAllowsChildren(false);
        setUserObject(title);
    }

    public CTreeLeaf(String title) {
        this(null, title);
    }

}
