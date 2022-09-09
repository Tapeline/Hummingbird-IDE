package me.tapeline.hummingbird.utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static String getExtension(File file) {
        String[] parts = file.getAbsolutePath().split("\\.");
        return parts[parts.length - 1];
    }

    public static void adaptButton(JButton btn) {
        btn.setBorderPainted(false);
        btn.setBackground(new Color(0, 0, 0, 0));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0x8E404040, true));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 0, 0, 0));
            }
        });
    }

    public static String idToHumanReadableName(String id) {
        String name = "";
        boolean capitalized = false;
        for (int i = 0; i < id.length(); i++) {
            if (!capitalized) {
                name += ("" + id.charAt(i)).toUpperCase(Locale.ROOT);
                capitalized = true;
                continue;
            }
            if (id.charAt(i) >= 'A' &&
                    id.charAt(i) <= 'Z') {
                name += " " + id.charAt(i);
                continue;
            }
            name += id.charAt(i);
        }
        return name;
    }

    public static <T> boolean listContains(List<T> list, T t) {
        for (T o : list)
            if (o.equals(t))
                return true;
        return false;
    }

    public static List<String> splitPath(File f) {
        List<String> pathElements = new ArrayList<>();
        Paths.get(f.getPath()).forEach(p -> pathElements.add(p.toString()));
        return pathElements;
    }

    public static List<File> filePyramid(List<String> components) {
        List<File> fp = new ArrayList<>();
        for (int i = 0; i < components.size(); i++) {
            String path = "";
            for (int j = 0; j <= i; j++) path += components.get(j) + "/";
            fp.add(new File(path));
        }
        return fp;
    }

    public static float getHueByText(String text) {
        return ((float) (int) text.toLowerCase(Locale.ROOT).trim().charAt(0)) * 0.03F;
    }

    public static String safeToString(Object obj) {
        return obj == null? "null" : obj.toString();
    }

}
