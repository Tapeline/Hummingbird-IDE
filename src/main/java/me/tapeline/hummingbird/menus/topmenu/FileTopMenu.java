package me.tapeline.hummingbird.menus.topmenu;

import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.menus.items.*;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.windows.dialog.wizards.common.Dialogs;
import me.tapeline.hummingbird.windows.dialog.wizards.newproject.NewProjectWizard;
import me.tapeline.hummingbird.windows.dialog.wizards.settings.SettingsDialog;
import me.tapeline.hummingbird.windows.forms.editor.EditorFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileTopMenu extends JMenu {

    public File f;

    public FileTopMenu(EditorFrame frame, File file) {
        super("File");
        f = file;
        add(new ItemCreateNewPlain(file));
        add(new ItemCreateNewText(file));
        add(new ItemCreateNewFolder(file));
        addSeparator();
        JMenuItem newProject = new JMenuItem("New project");
        newProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File projectRoot = NewProjectWizard.dialog();
                if (projectRoot == null) return;
                projectRoot.mkdirs();
                if (Dialogs.confirmYesNo(frame, "Open", "Open project in new window?"))
                    frame.quit();
                Main.openedFrames.add(new EditorFrame(projectRoot));
            }
        });
        add(newProject);

        JMenuItem openProject = new JMenuItem("Open project");
        openProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File projectRoot = null;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose Project");
                fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    projectRoot = fileChooser.getSelectedFile();
                }
                if (projectRoot == null) return;
                if (Dialogs.confirmYesNo(frame, "Open", "Open project in new window?"))
                    frame.quit();
                Main.openedFrames.add(new EditorFrame(projectRoot));
            }
        });
        add(openProject);
        addSeparator();

        JMenuItem settings = new JMenuItem("Settings");
        settings.setIcon(new ImageIcon(Icons.settings));
        settings.addActionListener(e -> SettingsDialog.dialog());
        add(settings);
        addSeparator();

        JMenuItem quit = new JMenuItem("Quit window");
        quit.setIcon(new ImageIcon(Icons.iconCross));
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Dialogs.confirmYesNo(frame, "Quit", "Are you sure you want to quit?"))
                    frame.quit();
            }
        });
        add(quit);
    }

}
