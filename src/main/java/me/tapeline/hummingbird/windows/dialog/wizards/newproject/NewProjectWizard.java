package me.tapeline.hummingbird.windows.dialog.wizards.newproject;

import com.formdev.flatlaf.FlatDarculaLaf;
import me.tapeline.hummingbird.windows.dialog.wizards.common.Dialogs;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class NewProjectWizard extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField projectName;
    private JTextField projectLocationParent;
    private JButton openFolder;
    private JLabel pathTrail;
    private boolean cancelled = false;

    public NewProjectWizard() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setMinimumSize(new Dimension(640, 280));

        JDialog that = this;

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

        openFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File parent = Dialogs.directory(that, new File(System.getProperty("user.home")));
                if (parent == null) return;
                projectLocationParent.setText(parent.getAbsolutePath());
            }
        });

        projectName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                pathTrail.setText(projectName.getText());
            }
            public void keyPressed(KeyEvent e) {
                pathTrail.setText(projectName.getText());
            }
            public void keyReleased(KeyEvent e) {
                pathTrail.setText(projectName.getText());
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        cancelled = true;
        dispose();
    }

    public static File dialog() {
        NewProjectWizard dialog = new NewProjectWizard();
        dialog.pack();
        dialog.setVisible(true);
        if (dialog.cancelled)
            return null;
        return new File(dialog.projectLocationParent.getText() + "/" + dialog.projectName.getText());
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        System.out.println(dialog());
        System.exit(0);
    }
}
