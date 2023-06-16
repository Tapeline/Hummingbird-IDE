package me.tapeline.hummingbird.ide.expansion.files.standard;

import me.tapeline.carousellib.icons.items.CPlainFolderIcon;
import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.project.Project;
import me.tapeline.hummingbird.ide.ui.menus.context.ItemCreateNewFolder;
import me.tapeline.hummingbird.ide.ui.menus.context.ItemCreateNewText;
import me.tapeline.hummingbird.ide.ui.menus.context.ItemDelete;

import javax.swing.*;
import java.io.File;

public class GenericFolder extends AbstractFileType {

    @Override
    public String id() {
        return "generic_folder";
    }

    @Override
    public String name() {
        return "Folder";
    }

    @Override
    public Icon icon() {
        return new CPlainFolderIcon(16);
    }

    @Override
    public boolean doCustomOpen() {
        return true;
    }

    @Override
    public boolean applies(File file) {
        return file.isDirectory();
    }

    @Override
    public void setupContextMenu(EditorWindow editor, JPopupMenu menu,
                                 File contextFile, Project contextProject) {
        menu.add(new ItemCreateNewText(editor, editor.getFileTree(), contextFile));
        menu.add(new ItemCreateNewFolder(editor, editor.getFileTree(), contextFile));
        menu.add(new ItemDelete(editor.getFileTree(), contextFile));
    }

}
