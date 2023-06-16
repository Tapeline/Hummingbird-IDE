package me.tapeline.hummingbird.ide.expansion.files.standard;

import me.tapeline.carousellib.icons.items.CPlainFileIcon;
import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.project.Project;
import me.tapeline.hummingbird.ide.ui.menus.context.ItemDelete;

import javax.swing.*;
import java.io.File;

public class GenericFile extends AbstractFileType {

    @Override
    public String id() {
        return "generic_file";
    }

    @Override
    public String name() {
        return "File";
    }

    @Override
    public Icon icon() {
        return new CPlainFileIcon(16);
    }

    @Override
    public boolean doCustomOpen() {
        return false;
    }

    @Override
    public boolean applies(File file) {
        return file.isFile();
    }

    @Override
    public void setupContextMenu(EditorWindow editor, JPopupMenu menu,
                                 File contextFile, Project contextProject) {
        menu.add(new ItemDelete(editor.getFileTree(), contextFile));
    }

}
