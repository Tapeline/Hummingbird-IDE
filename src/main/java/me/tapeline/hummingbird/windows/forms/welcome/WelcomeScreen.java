package me.tapeline.hummingbird.windows.forms.welcome;

import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.windows.HFrame;
import me.tapeline.hummingbird.windows.dialog.wizards.newproject.NewProjectWizard;
import me.tapeline.hummingbird.windows.dialog.wizards.settings.SettingsDialog;
import me.tapeline.hummingbird.windows.forms.editor.EditorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class WelcomeScreen extends HFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton exitButton;
    private JButton newProjectButton;
    private JButton openProjectButton;
    private JButton exitButton1;
    private JPanel lastProjects;
    private JLabel fullVersion;
    private JButton settingsButton;

    public WelcomeScreen(List<String> lastProjectsList) {
        super("Hummingbird - Welcome");
        setContentPane(panel1);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(640, 480));
        pack();

        fullVersion.setText(Main.fullVersion);

        WelcomeScreen that = this;

        newProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File projectRoot = NewProjectWizard.dialog();
                if (projectRoot == null) return;
                projectRoot.mkdirs();
                Main.openedFrames.add(new EditorFrame(projectRoot));
                that.dispose();
            }
        });

        openProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File projectRoot = null;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose Project");
                fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(that);
                if (result == JFileChooser.APPROVE_OPTION) {
                    projectRoot = fileChooser.getSelectedFile();
                }
                if (projectRoot == null) return;
                Main.openedFrames.add(new EditorFrame(projectRoot));
                that.dispose();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsDialog.dialog();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        exitButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        lastProjects.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.weightx = 1;
        constraints.gridx = 0;

        for (String path : lastProjectsList) {
            lastProjects.add(new ProjectEntry(this, path), constraints);
        }

        setVisible(true);
    }

    public void reloadFromConfig() { }
}
