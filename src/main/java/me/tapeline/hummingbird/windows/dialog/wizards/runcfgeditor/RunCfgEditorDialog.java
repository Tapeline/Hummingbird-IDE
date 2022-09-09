package me.tapeline.hummingbird.windows.dialog.wizards.runcfgeditor;

import com.formdev.flatlaf.FlatDarculaLaf;
import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.configuration.Configuration;
import me.tapeline.hummingbird.configuration.Configure;
import me.tapeline.hummingbird.expansions.runcfg.RunConfiguration;

import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

public class RunCfgEditorDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList list1;
    private JButton addButton;
    private JButton deleteButton;
    private JTextArea cmd;
    private JTextField cfgname;
    private JButton applyToCurrentConfigurationButton;

    public RunCfgEditorDialog() {
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

        renewList();

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        addButton.addActionListener(e -> {
            Main.cfg.runConfigurations.add(new RunConfiguration(
                    "new" + System.currentTimeMillis() + "cfg",
                    ""
            ));
            renewList();
        });
        deleteButton.addActionListener(e -> {
            Object obj = list1.getSelectedValue();
            if (obj instanceof String) {
                for (RunConfiguration cfg : Main.cfg.runConfigurations) {
                    if ((cfg.name + " (" + cfg.command + ")").equals(obj)) {
                        Main.cfg.runConfigurations.remove(cfg);
                        break;
                    }
                }
                renewList();
            }
        });
        applyToCurrentConfigurationButton.addActionListener(e -> {
            Object obj = list1.getSelectedValue();
            if (obj instanceof String) {
                for (RunConfiguration cfg : Main.cfg.runConfigurations) {
                    if ((cfg.name + " (" + cfg.command + ")").equals((String) obj)) {
                        cfg.command = cmd.getText();
                        cfg.name = cfgname.getText();
                        break;
                    }
                }
                renewList();
            }
        });

    }

    private void renewList() {
        list1.removeAll();
        Vector<String> c = new Vector<>();
        for (RunConfiguration cfg : Main.cfg.runConfigurations) {
            c.add(cfg.name + " (" + cfg.command + ")");
        }
        list1.setListData(c);
    }

    private void onOK() {
        Main.save();
        Main.reloadConfig();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void dialog() {
        RunCfgEditorDialog dialog = new RunCfgEditorDialog();
        dialog.pack();
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        dialog();
        System.exit(0);
    }
}
