package me.tapeline.hummingbird.menus.topmenu;

import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.menus.items.ItemCreateNewFolder;
import me.tapeline.hummingbird.menus.items.ItemCreateNewPlain;
import me.tapeline.hummingbird.menus.items.ItemCreateNewText;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.windows.dialog.wizards.common.Dialogs;
import me.tapeline.hummingbird.windows.dialog.wizards.newproject.NewProjectWizard;
import me.tapeline.hummingbird.windows.dialog.wizards.settings.SettingsDialog;
import me.tapeline.hummingbird.windows.forms.editor.EditorFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class HelpTopMenu extends JMenu {

    public File f;

    public HelpTopMenu(EditorFrame frame, File file) {
        super("Help");
        f = file;
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(about);
    }

}
