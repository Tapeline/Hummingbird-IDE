package me.tapeline.hummingbird.expansions.filetype;

import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.menus.items.ItemDelete;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.utils.Utils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GeneralFile extends AbstractFileType {

    public GeneralFile() {
        icon = Icons.file;
    }

    @Override
    public boolean appliesToFile(File file) {
        if (file.isDirectory()) return false;
        if (extensions.size() == 0) return true;
        String ext = Utils.getExtension(file);
        for (String e : extensions)
            if (e.equals(ext))
                return true;
        return false;
    }

    @Override
    public void setupContextActions(JMenu menu, File contextFile, Project contextProject) {
        menu.add(new ItemDelete(contextFile));
    }

    @Override
    public void setupContextActions(JPopupMenu menu, File contextFile, Project contextProject) {
        menu.add(new ItemDelete(contextFile));
    }

    @Override
    public String id() {
        return "plain";
    }

    @Override
    public int weight() {
        return 0;
    }
}
