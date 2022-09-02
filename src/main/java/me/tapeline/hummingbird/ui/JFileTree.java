package me.tapeline.quailstudio.customui;

import me.tapeline.quailstudio.project.fsmodel.FileSystemModel;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class JFileTree extends JTree {

    public FileSystemModel model;

    public JFileTree(FileSystemModel model) {
        super();
        this.model = model;
        setCellRenderer(new IconTreeNodeRenderer());
        setModel(new DefaultTreeModel(model.toTree()));
    }

}
