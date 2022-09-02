package me.tapeline.hummingbird.ui;

import me.tapeline.quailstudio.utils.Utils;

import java.io.File;

public class FileTreeNode extends IconTreeNode {

    public File file;

    public FileTreeNode(File f) {
        super(Utils.getIconForFile(f), f.getName());
        file = f;
    }

}
