package me.tapeline.hummingbird.menus.items;

import me.tapeline.hummingbird.windows.dialog.wizards.common.Dialogs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ItemCreateNewFolder extends JMenuItem {

    public ItemCreateNewFolder(File parent) {
        super("New directory");
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = Dialogs.directory(getRootPane(), parent);
                if (f != null) f.mkdirs();
            }
        });
    }

}
