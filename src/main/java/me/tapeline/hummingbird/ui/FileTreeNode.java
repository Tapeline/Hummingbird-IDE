package me.tapeline.hummingbird.ui;

import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.resources.Icons;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

public class FileTreeNode extends IconTreeNode {

    public File file;

    public FileTreeNode(File f) {
        super(new ImageIcon(
                f.isDirectory()? Icons.folder :
                Objects.requireNonNull(FS.getTypeForFile(f)).icon
        ), f.getName());
        file = f;
    }

}
