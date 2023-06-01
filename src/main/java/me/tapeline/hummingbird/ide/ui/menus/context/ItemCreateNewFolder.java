package me.tapeline.hummingbird.ide.ui.menus.context;

import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.hummingbird.ide.FS;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.ui.filetree.HFileTree;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;

public class ItemCreateNewFolder extends JMenuItem {

    public ItemCreateNewFolder(EditorWindow editor, HFileTree tree, File parent) {
        super("New directory");
        addActionListener(e -> {
            String name = Dialogs.string(editor, "New directory");
            if (name == null) return;
            Paths.get(
                    parent.getAbsolutePath(),
                    name
            ).toFile().mkdirs();
            tree.refresh();
        });
    }

}
