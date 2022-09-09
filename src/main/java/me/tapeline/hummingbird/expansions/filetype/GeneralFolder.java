package me.tapeline.hummingbird.expansions.filetype;

import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.menus.items.ItemCreateNewFolder;
import me.tapeline.hummingbird.menus.items.ItemCreateNewPlain;
import me.tapeline.hummingbird.menus.items.ItemCreateNewText;
import me.tapeline.hummingbird.menus.items.ItemDelete;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.utils.Utils;

import javax.swing.*;
import java.io.File;

public class GeneralFolder extends AbstractFileType {

    public GeneralFolder() {
        icon = Icons.folder;
    }

    @Override
    public boolean appliesToFile(File file) {
        return file.isDirectory();
    }

    @Override
    public void setupContextActions(JMenu menu, File contextFile, Project contextProject) {
        JMenu menuNew = new JMenu("New...");
        menuNew.add(new ItemCreateNewPlain(contextFile));
        menuNew.add(new ItemCreateNewText(contextFile));
        menuNew.add(new ItemCreateNewFolder(contextFile));
        menu.add(menuNew);
        menu.add(new ItemDelete(contextFile));
    }

    @Override
    public void setupContextActions(JPopupMenu menu, File contextFile, Project contextProject) {
        JMenu menuNew = new JMenu("New...");
        menuNew.add(new ItemCreateNewPlain(contextFile));
        menuNew.add(new ItemCreateNewText(contextFile));
        menuNew.add(new ItemCreateNewFolder(contextFile));
        menu.add(menuNew);
        menu.add(new ItemDelete(contextFile));
    }

    public boolean canOpen(File file) {
        return false;
    }

    @Override
    public String id() {
        return "folder";
    }


    @Override
    public int weight() {
        return Integer.MAX_VALUE;
    }
}
