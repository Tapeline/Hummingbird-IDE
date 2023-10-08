package me.tapeline.hummingbird.ide.tooltabs.git.ui.branchtree;

public class CategoryBranchTreeNode extends BranchTreeNode {

    private final String name;

    public CategoryBranchTreeNode(String name) {
        super(name, true);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
