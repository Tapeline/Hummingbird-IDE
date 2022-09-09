package me.tapeline.hummingbird.windows.dialog.wizards.settings;

import com.formdev.flatlaf.FlatDarculaLaf;
import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.configuration.Config;
import me.tapeline.hummingbird.configuration.Configure;
import me.tapeline.hummingbird.utils.Utils;

import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SettingsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTabbedPane settingsSections;
    private List<SettingsTab> tabs = new ArrayList<>();

    public SettingsDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        for (String section : Configure.getSections(Main.cfg)) {
            SettingsTab t = new SettingsTab(section, Main.cfg);
            tabs.add(t);
            settingsSections.addTab(Utils.idToHumanReadableName(section), t);
        }
    }

    private void onOK() {
        // add your code here
        try {
            for (SettingsTab tab : tabs) {
                Field[] fields = Main.cfg.getClass().getFields();
                for (Field field : fields) {
                    Config configAnnotation = field.getAnnotation(Config.class);
                    if (configAnnotation != null) {
                        String prefix = configAnnotation.section();
                        if (!tab.section.equals(prefix)) continue;
                        if (tab.inputs.containsKey(field.getName())) {
                            Object val = configAnnotation.configurationField().equals("")?
                                    tab.getInput(field.getName()) :
                                    tab.getInput(configAnnotation.configurationField());
                            if (val != null)
                                field.set(Main.cfg, val);
                        }
                    }
                }
            }
        } catch (IllegalAccessException ignored) {}
        Main.save();
        Main.reloadConfig();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void dialog() {
        SettingsDialog dialog = new SettingsDialog();
        dialog.pack();
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        dialog();
        System.exit(0);
    }
}
