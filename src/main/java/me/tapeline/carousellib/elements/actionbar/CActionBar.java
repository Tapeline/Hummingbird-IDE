package me.tapeline.carousellib.elements.actionbar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class CActionBar extends JToolBar {

    private List<CAbstractAction> actions = new ArrayList<>();
    private HashMap<CAbstractAction, Component> components = new HashMap<>();

    public CActionBar(int axis) {
        super();
        BoxLayout layout = new BoxLayout(this, axis);
        setLayout(layout);
    }

    public CActionBar() {
        this(BoxLayout.LINE_AXIS);
    }

    public CActionBar(int axis, List<CAbstractAction> actions) {
        this(axis);
        this.actions = actions;
        rebuildActionComponents();
    }

    public void addAction(CAbstractAction action) {
        actions.add(action);
        rebuildActionComponents();
    }

    public void addButtonAction(String text, Icon icon, BiConsumer<CAbstractAction, JComponent> onAction) {
        CButtonAction action = new CButtonAction(text, icon);
        action.setOnAction(onAction);
        addAction(action);
    }

    public void insertAction(int index, CAbstractAction action) {
        actions.add(index, action);
        rebuildActionComponents();
    }

    public void removeAction(CAbstractAction action) {
        actions.remove(action);
        rebuildActionComponents();
    }

    public CAbstractAction getAction(int index) {
        return actions.get(index);
    }

    public void clearActions() {
        actions.clear();
        rebuildActionComponents();
    }

    public int getActionCount() {
        return actions.size();
    }

    public Component getComponentForAction(CAbstractAction action) {
        if (!components.containsKey(action))
            return null;
        return components.get(action);
    }

    public void rebuildActionComponents() {
        for (Component component : components.values())
            remove(component);
        for (CAbstractAction action : actions) {
            Component comp = action.buildComponent();
            action.customize(comp);
            components.put(action, comp);
            add(comp);
        }
    }

}

/*package me.tapeline.carousellib.elements.actionbar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class CActionBar extends JToolBar {

    private List<CAbstractAction> actions = new ArrayList<>();
    private HashMap<CAbstractAction, JComponent> components = new HashMap<>();

    public CActionBar(int orient) {
        super(orient);
    }

    public CActionBar() {
        this(HORIZONTAL);
    }

    public CActionBar(int orient, List<CAbstractAction> actions) {
        this(orient);
        this.actions = actions;
        rebuildActionComponents();
    }

    public void addAction(CAbstractAction action) {
        actions.add(action);
        rebuildActionComponents();
    }

    public void addButtonAction(String text, Icon icon, BiConsumer<CAbstractAction, JComponent> onAction) {
        CButtonAction action = new CButtonAction(text, icon);
        action.setOnAction(onAction);
        addAction(action);
    }

    public void insertAction(int index, CAbstractAction action) {
        actions.add(index, action);
        rebuildActionComponents();
    }

    public void removeAction(CAbstractAction action) {
        actions.remove(action);
        rebuildActionComponents();
    }

    public CAbstractAction getAction(int index) {
        return actions.get(index);
    }

    public void clearActions() {
        actions.clear();
        rebuildActionComponents();
    }

    public int getActionCount() {
        return actions.size();
    }

    public JComponent getComponentForAction(CAbstractAction action) {
        if (!components.containsKey(action))
            return null;
        return components.get(action);
    }

    public void rebuildActionComponents() {
        for (JComponent component : components.values())
            remove(component);
        for (CAbstractAction action : actions) {
            JComponent comp = action.buildComponent();
            components.put(action, comp);
            add(comp);
        }
    }

}
*/