package me.tapeline.hummingbird.ide.tooltabs.git;

import me.tapeline.carousellib.data.Pair;
import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.carousellib.elements.actionbar.*;
import me.tapeline.carousellib.icons.commonactions.CAddIcon;
import me.tapeline.carousellib.icons.commonactions.CRemoveIcon;
import me.tapeline.carousellib.icons.items.git.CBranchIcon;
import me.tapeline.carousellib.icons.navigation.CDownArrowIcon;
import me.tapeline.carousellib.icons.navigation.CRightArrowIcon;
import me.tapeline.carousellib.icons.navigation.CUpArrowIcon;
import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.frames.DialogResult;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.frames.gitlocaluser.GitLocalUserDialog;
import me.tapeline.hummingbird.ide.tooltabs.git.ui.branchtree.BranchTree;
import me.tapeline.hummingbird.ide.ui.tooltabs.AbstractToolTab;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.awtui.CommitGraphPane;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GitToolTab extends JPanel implements AbstractToolTab {

    private EditorWindow editor;
    private CActionBar actionBar;
    private BranchTree branchTree;
    private CommitGraphPane commitGraph;
    private JButton hideButton;
    private boolean isRemoteAvailable;
    private boolean isRepositoryAvailable;
    private Repository repo;
    private Git git;
    private JSplitPane splitPane;

    public GitToolTab(EditorWindow editor) {
        super();
        this.editor = editor;
        actionBar = new CActionBar();
        setLayout(new BorderLayout());
        branchTree = null;
        commitGraph = null;
        isRepositoryAvailable = false;
        isRemoteAvailable = false;
        repo = null;
        git = null;
        tryToLoadRepo();
        rebuildGUI();
    }

    private void rebuildGUI() {
        actionBar.clearActions();
        actionBar.addAction(new CLabelAction("Git", null));
        actionBar.addAction(new CHSpacerAction());
        if (!isRepositoryAvailable) {
            actionBar.addAction(new CButtonAction(
                    "Create repo", new CAddIcon(16, 16),
                    this::createRepo
            ));
        } else {
            actionBar.addAction(new CButtonAction(
                    null, new CAddIcon(16, 16), this::addNewBranch
            ));
            actionBar.addAction(new CButtonAction(
                    null, new CRemoveIcon(16, 16), this::removeBranch
            ));
            actionBar.addAction(new CButtonAction(
                    null, new CRightArrowIcon(16), this::checkoutBranch
            ));
            actionBar.addAction(new CButtonAction(
                    "Commit", null, this::makeCommit
            ));
        }
        if (isRepositoryAvailable && isRemoteAvailable) {
            actionBar.addAction(new CSeparatorAction());
            actionBar.addAction(new CLabelAction("Remote", null));
            actionBar.addAction(new CButtonAction(
                    null, new CDownArrowIcon(16), this::pullRemote
            ));
            actionBar.addAction(new CButtonAction(
                    null, new CUpArrowIcon(16), this::pushRemote
            ));
        }
        actionBar.addAction(new CHSpacerAction());
        if (isRepositoryAvailable && !isRemoteAvailable)
            actionBar.addAction(new CButtonAction(
                    "Setup remote", null, this::setupRemote
            ));
        if (isRepositoryAvailable)
            actionBar.addAction(new CButtonAction(
                    "Configure user", null, this::configureUser
            ));
        if (hideButton != null) actionBar.addAction(new CButtonAction(hideButton));

        if (isRepositoryAvailable) {
            branchTree = new BranchTree(repo, git);
            commitGraph = new CommitGraphPane();
            splitPane = new JSplitPane();
            splitPane.setLeftComponent(branchTree);
            splitPane.setRightComponent(commitGraph);
        } else {
            branchTree = null;
            commitGraph = null;
            splitPane = null;
        }

        removeAll();
        add(actionBar, BorderLayout.PAGE_START);
        if (splitPane != null) add(splitPane, BorderLayout.CENTER);

        updateGUI();
    }

    private void updateGUI() {
        if (isRepositoryAvailable && commitGraph != null) {
            PlotWalk walk = new PlotWalk(repo);
            walk.sort(RevSort.BOUNDARY, true);
            List<RevCommit> commits = new ArrayList<>();
            try {
                for (Ref a : repo.getRefDatabase().getRefs()) {
                    ObjectId oid = a.getPeeledObjectId();
                    if (oid == null) oid = a.getObjectId();
                    try {
                        commits.add(walk.parseCommit(oid));
                    } catch (IncorrectObjectTypeException ignored) {}
                }
                for (RevCommit c : commits) walk.markStart(c);
                commitGraph.getCommitList().source(walk);
                commitGraph.getCommitList().fillTo(Integer.MAX_VALUE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (isRepositoryAvailable && branchTree != null) {
            branchTree.gatherCategories();
            branchTree.updateItemsToMatchCategories();
        }
    }

    private void tryToLoadRepo() {
        File gitRoot = Paths.get(
                editor.getProject().getRoot().getAbsolutePath(), ".git"
        ).toFile();
        if (!gitRoot.exists()) return;
        try {
            repo = new FileRepositoryBuilder().setGitDir(gitRoot).build();
            git = new Git(repo);
            isRepositoryAvailable = true;
        } catch (IOException e) {
            isRepositoryAvailable = false;
            repo = null;
            Dialogs.exception("Error", "Error while loading Git", e);
        }
    }

    private void addNewBranch(CAbstractAction action, JComponent component) {

    }

    private void removeBranch(CAbstractAction action, JComponent component) {

    }

    private void checkoutBranch(CAbstractAction action, JComponent component) {

    }

    private void pullRemote(CAbstractAction action, JComponent component) {

    }

    private void pushRemote(CAbstractAction action, JComponent component) {

    }

    private void createRepo(CAbstractAction action, JComponent component) {
        try {
            Git git = Git.init()
                    .setDirectory(editor.getProject().getRoot())
                    .call();
            Application.getStaticLogger().info("Created a new repository at "
                    + git.getRepository().getDirectory());
        } catch (GitAPIException e) {
            Dialogs.exception("Error", "Error creating repo", e);
        }
        tryToLoadRepo();
        rebuildGUI();
    }

    private void setupRemote(CAbstractAction action, JComponent component) {

    }

    private void makeCommit(CAbstractAction action, JComponent component) {

    }

    private void configureUser(CAbstractAction action, JComponent component) {
        if (repo == null) return;
        StoredConfig storedConfig = repo.getConfig();
        String name = storedConfig.getString("user", null,
                "name");
        String email = storedConfig.getString("user", null,
                "email");
        GitLocalUserDialog dialog = new GitLocalUserDialog(name, email);
        DialogResult<Pair<String, String>> result = dialog.getResult();
        if (result.getType() == DialogResult.Type.CANCEL) return;
        storedConfig.setString("user", null, "name",
                result.getValue().getA());
        storedConfig.setString("user", null, "email",
                result.getValue().getB());
        try {
            storedConfig.save();
        } catch (IOException e) {
            Dialogs.exception("Error", "Error saving config", e);
        }
    }

    @Override
    public String name() {
        return "Git";
    }

    @Override
    public Icon icon() {
        return new CBranchIcon(16, false);
    }

    @Override
    public JComponent buildToolTab(EditorWindow editor, JButton hideToolTabButton) {
        hideButton = hideToolTabButton;
        rebuildGUI();
        return this;
    }

    public EditorWindow getEditor() {
        return editor;
    }

}
