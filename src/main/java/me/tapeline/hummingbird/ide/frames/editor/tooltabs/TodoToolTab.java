package me.tapeline.hummingbird.ide.frames.editor.tooltabs;

import me.tapeline.carousellib.elements.dynamiclist.CDynamicList;
import me.tapeline.carousellib.icons.navigation.CMenuIcon;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.ui.tooltabs.AbstractToolTab;

import javax.swing.*;
import java.awt.*;

public class TodoToolTab extends JTabbedPane implements AbstractToolTab {

    private CDynamicList<String> projectTodos;
    private JList<String> scopeTodos;
    private EditorWindow editor;

    public TodoToolTab(EditorWindow editor) {
        super();
        this.editor = editor;
        projectTodos = new CDynamicList<>();
        scopeTodos = new JList<>();
    }

    @Override
    public String name() {
        return "TODO";
    }

    @Override
    public Icon icon() {
        return new CMenuIcon(14);
    }

    @Override
    public JComponent buildToolTab(EditorWindow editor, JButton hideToolTabButton) {
        addTab("Notes", projectTodos);
        addTab("In-Project TODOs", scopeTodos);
        return this;
    }

    public CDynamicList<String> getProjectTodos() {
        return projectTodos;
    }

    public JList<String> getScopeTodos() {
        return scopeTodos;
    }

    public EditorWindow getEditor() {
        return editor;
    }

}
