package me.tapeline.hummingbird.ide.ui.filetree;

import me.tapeline.hummingbird.ide.FS;
import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;
import me.tapeline.hummingbird.ide.ui.tools.treeutils.IconTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FileTreeModel extends DefaultTreeModel {

    protected File modelRootFile;
    protected FileTreeNode rootNode;
    protected HFileTree fileTree;

    public FileTreeModel(HFileTree fileTree, File root) {
        super(new FileTreeNode(root, true));
        this.rootNode = ((FileTreeNode) getRoot());
        this.modelRootFile = root;
        this.fileTree = fileTree;
    }

    public void updateNode(FileTreeNode node) {
        if (!node.getFile().exists()) {
            removeNodeFromParent(node);
            return;
        }

        String name = node.getFile().getName();
        if (node.isShowFullPath())
            name += " [" + node.getFile().getAbsolutePath() + "]";
        Icon icon = null;
        AbstractFileType fileType = FS.getFileType(node.getFile());
        if (fileType != null)
            icon = fileType.icon();
        node.setUserObject(new IconTreeNode.TreeNodeData(icon, name));

        if (node.getFile().isDirectory()) {
            node.setAllowsChildren(true);

            for (TreeNode currentChild : Collections.list(node.children())) {
                if (currentChild instanceof FileTreeNode childFile)
                    updateNode(childFile);
            }

            File[] childFilesArray = node.getFile().listFiles();
            if (childFilesArray == null)
                return;
            List<File> childFiles = new ArrayList<>(Arrays.asList(childFilesArray))
                    .stream().sorted(Comparator.comparing(File::getName)).toList();
            for (File childFile : childFiles)
                if (!node.containsChildFile(childFile)) {
                    FileTreeNode childNode = new FileTreeNode(childFile, false);
                    insertNodeInto(childNode, node, childFiles.indexOf(childFile));
                    updateNode(childNode);
                    reload(node);
                }
        }
    }

    public File getModelRootFile() {
        return modelRootFile;
    }

    public FileTreeNode getRootNode() {
        return rootNode;
    }

}
