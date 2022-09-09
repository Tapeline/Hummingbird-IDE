package me.tapeline.hummingbird.filesystem.project.fsmodel;

import me.tapeline.hummingbird.filesystem.project.fsmodel.Element;
import me.tapeline.hummingbird.ui.FileTreeNode;

import java.io.File;

public class FileSystemModel {

    public Element root;

    public FileSystemModel(File root) {
        this.root = new Element(root);
    }

    public FileTreeNode toTree() {
        return root.toTree();
    }

    public void reload() {
        root.reload();
    }

}
