package me.tapeline.hummingbird.ide.expansion.files;

import me.tapeline.hummingbird.ide.Registry;
import me.tapeline.hummingbird.ide.expansion.RegistryEntry;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.project.Project;

import javax.swing.*;
import java.io.File;

public abstract class AbstractFileType implements RegistryEntry {

    public abstract String name();

    public abstract Icon icon();

    public abstract boolean doCustomOpen();

    public abstract boolean applies(File file);

    public void customOpen(EditorWindow editor, File file) {}

    public void setupContextMenu(EditorWindow editor, JPopupMenu menu,
                                 File contextFile, Project contextProject) {
        setupFileContextMenu(editor, menu, contextFile, contextProject);
        for (AbstractContextMenuExpansion expansion :
                Registry.getApplicableExpansions(this))
            expansion.setupContextMenu(this, editor, menu,
                    contextFile, contextProject);
    }

    public abstract void setupFileContextMenu(EditorWindow editor,
                                              JPopupMenu menu,
                                              File contextFile,
                                              Project contextProject);

}
