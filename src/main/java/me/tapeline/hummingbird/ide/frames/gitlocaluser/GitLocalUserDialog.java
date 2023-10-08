package me.tapeline.hummingbird.ide.frames.gitlocaluser;

import me.tapeline.carousellib.data.Pair;
import me.tapeline.hummingbird.ide.frames.DialogResult;

import javax.swing.*;
import java.awt.event.*;

public class GitLocalUserDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField usernameField;
    private JTextField emailField;
    private DialogResult<Pair<String, String>> result;

    public GitLocalUserDialog(String username, String email) {
        setTitle("Configure user");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        result = new DialogResult<>(DialogResult.Type.CANCEL, null);
        usernameField.setText(username);
        emailField.setText(email);

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

        setSize(600, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onOK() {
        result = new DialogResult<>(DialogResult.Type.OK, new Pair<>(
                usernameField.getText(),
                emailField.getText()
        ));
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public DialogResult<Pair<String, String>> getResult() {
        return result;
    }

}
