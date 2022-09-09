package me.tapeline.hummingbird.filesystem;

import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.filetype.GeneralFile;
import me.tapeline.hummingbird.resources.Icons;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FS {

    public static String CHARSET = "UTF-8";

    public static AbstractFileType getTypeForFile(File file) {
        for (AbstractFileType aft : Registry.fileTypes)
            if (aft.appliesToFile(file))
                return aft;
        return null;
    }

    public static String readFile(String path) {
        try {
            if (!Files.exists(new File(path).toPath())) return null;
            return FileUtils.readFileToString(new File(path), CHARSET);
        } catch (IOException e) {
            return null;
        }
    }

    public static String readFile(String path, String defaultText) {
        try {
            if (!Files.exists(new File(path).toPath())) return defaultText;
            return FileUtils.readFileToString(new File(path), CHARSET);
        } catch (IOException e) {
            return null;
        }
    }

    public static String readFile(File file) {
        return readFile(file.getAbsolutePath());
    }

    public static String readFile(File file, String defaultText) {
        return readFile(file.getAbsolutePath(), defaultText);
    }


    public static boolean writeFile(String path, String text) {
        try {
            FileUtils.writeStringToFile(new File(path), text, CHARSET);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean writeFile(File file, String text) {
        return writeFile(file.getAbsolutePath(), text);
    }


    public static boolean exists(String path) {
        return Files.exists(new File(path).toPath());
    }

    public static boolean exists(File path) {
        return Files.exists(path.toPath());
    }

    public static void delete(File path) {
        if (path.isDirectory()) {
            try {
                FileUtils.deleteDirectory(path);
            } catch (IOException ignored) { }
        } else {
            path.delete();
        }
    }
}
