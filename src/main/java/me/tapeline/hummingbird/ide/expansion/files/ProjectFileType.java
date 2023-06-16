package me.tapeline.hummingbird.ide.expansion.files;

import me.tapeline.carousellib.icons.items.CColorFolderIcon;
import me.tapeline.carousellib.utils.AdaptableColor;
import me.tapeline.hummingbird.ide.expansion.files.standard.GenericFolder;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.project.Project;
import me.tapeline.hummingbird.ide.ui.menus.context.ItemDelete;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ProjectFileType extends GenericFolder {

    @Override
    public String id() {
        return "project";
    }

    @Override
    public String name() {
        return "Project";
    }

    @Override
    public Icon icon() {
        return new CColorFolderIcon(
                new AdaptableColor(
                        new Color(62, 133, 160),
                        new Color(63, 181, 223)
                ),
                16
        );
    }

    @Override
    public boolean doCustomOpen() {
        return false;
    }

    @Override
    public boolean applies(File file) {
        if (!file.isDirectory())
            return false;
        File[] contents = file.listFiles();
        if (contents == null)
            return false;
        for (File content : contents)
            if (content.getName().equals(".ide") && content.isDirectory())
                return true;
        return false;
    }

    @Override
    public void setupContextMenu(EditorWindow editor, JPopupMenu menu,
                                 File contextFile, Project contextProject) {
        super.setupContextMenu(editor, menu, contextFile, contextProject);
        for (Component c : menu.getComponents())
            if (c instanceof ItemDelete)
                menu.remove(c);
    }

}
