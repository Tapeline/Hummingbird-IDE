package me.tapeline.carousellib.dialogs;

import me.tapeline.hummingbird.ide.Application;

import javax.swing.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

public class ExceptionDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel message;
    private JTextPane text;

    public ExceptionDialog(String title, String message, Exception exception) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Application.getStaticLogger().log(Level.SEVERE, message, exception);

        setTitle(title);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        pw.close();

        this.message.setText(message);
        text.setText(
                "Exception " + exception + "\n" +
                exception.getLocalizedMessage() + "\n" +
                "\n" +
                "Stack Traceback (most recent call last):\n" +
                sw
        );

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    public static void main(String[] args) {
        ExceptionDialog dialog = new ExceptionDialog("Exception Occurred",
                "An exception has occurred", new IllegalArgumentException());
        dialog.pack();
        dialog.setSize(600, 400);
        dialog.setVisible(true);
        System.exit(0);
    }

}
