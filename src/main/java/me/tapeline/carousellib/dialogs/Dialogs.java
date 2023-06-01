package me.tapeline.carousellib.dialogs;

import me.tapeline.carousellib.utils.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.List;

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
        fileChooser.setDialogTitle("Choose file...");
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(form);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static File saveFile(Component form, File parent) {
        JFileChooser fileChooser = new JFileChooser(parent);
        fileChooser.setDialogTitle("Choose file...");
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(form);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static File file(Component form, File parent, List<String> ext) {
        JFileChooser fileChooser = new JFileChooser(parent);
        fileChooser.setDialogTitle("Choose file...");
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                for (String s : ext)
                    if (Utils.getExtension(f).equals(s))
                        return true;
                return false;
            }

            @Override
            public String getDescription() {
                return "One of " + ext.toString();
            }
        };
        fileChooser.setFileFilter(fileFilter);
        int result = fileChooser.showOpenDialog(form);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static File saveFile(Component form, File parent, List<String> ext) {
        JFileChooser fileChooser = new JFileChooser(parent);
        fileChooser.setDialogTitle("Choose file...");
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                for (String s : ext)
                    if (Utils.getExtension(f).equals(s))
                        return true;
                return false;
            }

            @Override
            public String getDescription() {
                return "One of " + ext.toString();
            }
        };
        fileChooser.setFileFilter(fileFilter);
        int result = fileChooser.showSaveDialog(form);
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

    public static String string(Component form, String message) {
        return JOptionPane.showInputDialog(form, message);
    }

    public static void exception(String title, String message, Exception exception) {
        ExceptionDialog dialog = new ExceptionDialog(title, message, exception);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

}