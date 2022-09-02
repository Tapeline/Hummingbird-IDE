package me.tapeline.hummingbird.menus.items;

import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.windows.dialog.wizards.common.Dialogs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

public class ItemDelete extends JMenuItem {

    public ItemDelete(File parent) {
        super("New text (.txt)");
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = Dialogs.saveFile(getRootPane(), parent, Arrays.asList("txt"));
                if (f != null) FS.writeFile(f, "");
            }
        });
    }

}
