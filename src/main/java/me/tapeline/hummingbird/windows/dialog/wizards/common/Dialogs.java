package me.tapeline.hummingbird.windows.dialog.wizards.directorychooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Dialogs {

    public static File directory(Component form, File parent) {
        JFileChooser fileChooser = new JFileChooser(parent);
        fileChooser.setDialogTitle("Choose directory...");
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(form);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static File file(Component form, File parent) {
        JFileChooser fileChooser = new JFileChooser(parent);
        fileChooser.setDialogTitle("Choose dile...");
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(form);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static void error(Component form, String title, String text) {
        JOptionPane.showMessageDialog(
                form,
                text,
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void warn(Component form, String title, String text) {
        JOptionPane.showMessageDialog(
                form,
                text,
                title,
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static void info(Component form, String title, String text) {
        JOptionPane.showMessageDialog(
                form,
                text,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static boolean confirmYesNo(Component form, String title, String text) {
        return JOptionPane.showConfirmDialog(
                form,
                text,
                title,
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
    }

    public static int confirmYesNoCancel(Component form, String title, String text) {
        return JOptionPane.showConfirmDialog(
                form,
                text,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION
        );
    }

}
