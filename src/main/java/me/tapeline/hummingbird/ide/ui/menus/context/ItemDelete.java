package me.tapeline.hummingbird.ide.ui.menus.context;

import me.tapeline.hummingbird.ide.FS;
import me.tapeline.hummingbird.ide.ui.filetree.HFileTree;

import javax.swing.*;
import java.io.File;

public class ItemDelete extends JMenuItem {

    public ItemDelete(HFileTree tree, File parent) {
        super("Delete");
        addActionListener(e -> {
            FS.delete(parent);
            tree.refresh();
        });
    }

}
