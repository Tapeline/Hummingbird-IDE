package me.tapeline.hummingbird.ide.ui.tabs;

import javax.swing.*;

public abstract class AbstractWorkspaceTab extends JPanel {

    protected String identifier;

    public AbstractWorkspaceTab(String identifier) {
        this.identifier = identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public abstract void save();
    public abstract void reload();

}
