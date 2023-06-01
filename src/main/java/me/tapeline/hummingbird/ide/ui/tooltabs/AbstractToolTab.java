package me.tapeline.hummingbird.ide.ui.tooltabs;

import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;

import javax.swing.*;

public interface AbstractToolTab {

    String name();
    Icon icon();
    JComponent buildToolTab(EditorWindow editor, JButton hideToolTabButton);

}
