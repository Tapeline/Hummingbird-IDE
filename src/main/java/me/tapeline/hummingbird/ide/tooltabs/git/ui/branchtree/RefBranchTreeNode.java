package me.tapeline.hummingbird.ide.tooltabs.git.ui.branchtree;

import org.eclipse.jgit.lib.Ref;

public class RefBranchTreeNode extends BranchTreeNode {

    private final Ref ref;

    public RefBranchTreeNode(Ref ref) {
        super(ref, false);
        this.ref = ref;
    }

    public Ref getRef() {
        return ref;
    }

}
