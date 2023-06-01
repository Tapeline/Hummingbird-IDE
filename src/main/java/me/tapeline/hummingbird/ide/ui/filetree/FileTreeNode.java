package me.tapeline.hummingbird.ide.ui.filetree;

import me.tapeline.hummingbird.ide.FS;
import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;
import me.tapeline.hummingbird.ide.ui.tools.treeutils.IconTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class FileTreeNode extends IconTreeNode {

    protected File file;
    protected boolean showFullPath;

    public FileTreeNode(File file, boolean showFullPath) {
        super(null, "");
        this.file = file;
        this.showFullPath = showFullPath;
        setAllowsChildren(false);
    }

    public boolean containsChildFile(File file) {
        if (children == null) return false;
        for (TreeNode node : children)
            if (node instanceof FileTreeNode childFile)
                if (childFile.getFile().getAbsolutePath().equals(file.getAbsolutePath()))
                    return true;
        return false;
    }

    public File getFile() {
        return file;
    }

    public boolean isShowFullPath() {
        return showFullPath;
    }

}
