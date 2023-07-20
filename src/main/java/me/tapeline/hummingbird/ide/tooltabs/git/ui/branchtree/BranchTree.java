package me.tapeline.hummingbird.ide.tooltabs.git.ui.branchtree;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchTree extends JTree {

    private final HashMap<String, List<Ref>> categories;
    private final Repository repository;
    private final Git git;
    private DefaultMutableTreeNode root;

    public BranchTree(Repository repository, Git git) {
        super();
        this.repository = repository;
        this.git = git;
        categories = new HashMap<>();
        root = new DefaultMutableTreeNode("Root");
        setShowsRootHandles(true);
        setCellRenderer(new BranchTreeRenderer());
    }

    public void gatherCategories() {
        categories.clear();
        try {
            List<Ref> localBranches = git.branchList().call();
            List<Ref> remoteBranches = git.branchList()
                    .setListMode(ListBranchCommand.ListMode.REMOTE).call();
            categories.put("Local", localBranches);
            categories.put("Remote", remoteBranches);
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void updateItemsToMatchCategories() {
        removeAll();
        for (Map.Entry<String, List<Ref>> entry : categories.entrySet()) {
            CategoryBranchTreeNode category
                    = new CategoryBranchTreeNode(entry.getKey());
            for (Ref ref : entry.getValue())
                category.add(new RefBranchTreeNode(ref));
            root.add(category);
        }
        setRootVisible(false);
        setModel(new DefaultTreeModel(root));
        setExpandedState(getPathForRow(0), true);
    }

    public HashMap<String, List<Ref>> getCategories() {
        return categories;
    }

    public Repository getRepository() {
        return repository;
    }

    public Git getGit() {
        return git;
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }
}
