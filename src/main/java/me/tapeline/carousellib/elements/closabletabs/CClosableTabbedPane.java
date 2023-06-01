package me.tapeline.carousellib.elements.closabletabs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CClosableTabbedPane extends JTabbedPane {

    protected List<TabCloseListener> tabCloseListeners = new ArrayList<>();

    public void addTabCloseListener(TabCloseListener listener) {
        tabCloseListeners.add(listener);
    }

    public void removeTabCloseListener(TabCloseListener listener) {
        tabCloseListeners.remove(listener);
    }

    public List<TabCloseListener> getTabCloseListeners() {
        return tabCloseListeners;
    }

    @Override
    public void addTab(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
        int count = this.getTabCount() - 1;
        setTabComponentAt(count, new ClosableTab(this, component));
    }

    @Override
    public void addTab(String title, Icon icon, Component component) {
        addTab(title, icon, component, null);
    }

    @Override
    public void addTab(String title, Component component) {
        addTab(title, null, component);
    }

    public void addTabNoExit(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
    }

    public void addTabNoExit(String title, Icon icon, Component component) {
        addTabNoExit(title, icon, component, null);
    }

    public void addTabNoExit(String title, Component component) {
        addTabNoExit(title, null, component);
    }

}
