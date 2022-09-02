package me.tapeline.hummingbird.menus.items;

import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.windows.dialog.wizards.common.Dialogs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ItemCreateNewText extends JMenuItem {

    public ItemCreateNewText(File parent) {
        super("New plain");
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = Dialogs.file(getRootPane(), parent);
                if (f != null) FS.writeFile(f, "");
            }
        });
    }

}
